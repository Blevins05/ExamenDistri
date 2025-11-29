package examenA;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HiloPartida implements Runnable {
	private Socket[] jugadores;
	private Random generadorNumeros;
	private String[] operaciones = {"SUMAR", "RESTAR", "MULTIPLICAR"};
	private CyclicBarrier barrera = new CyclicBarrier(4);
	private CountDownLatch starter = new CountDownLatch(3);
	private List<Integer> ordenAciertos = Collections.synchronizedList(new ArrayList<>()); // Orden en el que van acertando los jugadores
	private int[] puntuaciones = new int[3]; // Puntuaciones de cada jugador
	private String[] nombres = new String[3];
	
	public volatile int resultadoEsperado;
	
	public HiloPartida(Socket[] clientes) {
		this.jugadores = clientes;
		this.generadorNumeros = new Random();
		
	}
	private int calcularResultado(String operacion, int operando1, int operando2) {
	    if (operacion.equals("SUMAR")) {
	        return operando1 + operando2;
	    } else if (operacion.equals("RESTAR")) {
	        return operando1 - operando2;
	    } else {
	        return operando1 * operando2;
	    }
	}

	@Override
	public void run() {
		 DataOutputStream[] dos = new DataOutputStream[3];
	        for (int i = 0; i < 3; i++) {
	            try {
					dos[i] = new DataOutputStream(jugadores[i].getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	
	        // Crear Hilo para ejecutar cada jugador
		for (int i = 0; i < 3; i++) {
			new Thread(new HiloJugador(jugadores[i], barrera, starter, ordenAciertos, i, nombres, this, dos[i])).start();
		}
		
		int rondas = 3;
		// Generar 1 operacion
		for (int ronda = 0; ronda < rondas; ronda++) {
			String operacion = operaciones[ronda];
			int operando1 = generadorNumeros.nextInt(501);
			int operando2 = generadorNumeros.nextInt(501);
			
			this.resultadoEsperado = calcularResultado(operacion, operando1, operando2);
			
			String ejercicio = operacion + " " + operando1 + " " + operando2;
			System.out.println(ejercicio);
			
			for (int i = 0; i < 3; i++) {
				try {
					dos[i].writeBytes(ejercicio + "\n");
					dos[i].flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				this.barrera.await(); // Todos empiezan a la vez:
				this.barrera.await(); // Y todos terminan a la vez;
				calcularPuntuaciones();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
		// Crear el XML y enviarselo a los jugadores
		terminarPartida();
		System.out.println("XML creado");

		enviarPartidaATodos(dos);
		System.out.println("XML enviado a todos");

		String ganador = obtenerNombreGanador();
		System.out.println("Ganador: " + ganador);

		for (int i = 0; i < 3; i++) {
		    try {
		        dos[i].writeBytes(ganador + "\n");
		        dos[i].flush();
		        System.out.println("Ganador enviado a jugador " + i);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	private String obtenerNombreGanador() {
	    int maxPuntuacion = getPuntuacionGanadora();
	    for (int i = 0; i < 3; i++) {
	        if (puntuaciones[i] == maxPuntuacion) {
	            return nombres[i];
	        }
	    }
	    return "";
	}
	public void calcularPuntuaciones() {
		if (ordenAciertos.size() == 3) {
		    puntuaciones[ordenAciertos.get(0)] += 3;
		    puntuaciones[ordenAciertos.get(1)] += 2;
		    puntuaciones[ordenAciertos.get(2)] += 1;
		    ordenAciertos.clear();
		}
	}
	
	public int getPuntuacionGanadora() {
		int mp = puntuaciones[0];
		for (int i = 0; i < 3; i++) {
			mp = Math.max(puntuaciones[i], mp);
		}
		return mp;
	}
	
	public void terminarPartida() {
		String ficheroXML = "Partida.xml";
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element root = doc.createElement("partida");
			
			for (int i = 0; i < 3; i++) {
				Element jugador = doc.createElement("jugador");
				jugador.setAttribute("nombre", nombres[i]);
				jugador.setTextContent(String.valueOf(puntuaciones[i]));
				
				if (puntuaciones[i] == getPuntuacionGanadora())  {
					jugador.setAttribute("posicion", "ganador");
				}
				root.appendChild(jugador);
			}
			doc.appendChild(root);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(ficheroXML));
			transformer.transform(source, result);
			
				
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
	
	public void enviarPartidaATodos(DataOutputStream[] dos) {
	    File archivo = new File("Partida.xml");
	    long size = archivo.length();

	    byte[] bufferArchivo = new byte[(int) size];
	    try (FileInputStream fis = new FileInputStream(archivo)) {
	        fis.read(bufferArchivo);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return;
	    }

	    for (int i = 0; i < dos.length; i++) {
	        try {
	            dos[i].writeLong(size);
	            dos[i].write(bufferArchivo);
	            dos[i].flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
}
