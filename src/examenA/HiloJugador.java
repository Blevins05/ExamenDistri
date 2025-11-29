package examenA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class HiloJugador implements Runnable {
	private Socket jugador;
	private CyclicBarrier barrera;
	private CountDownLatch starter;
	private List<Integer> ordenAciertos;
	private int id;
	private String[] nombres;
	private HiloPartida partida;
    private DataOutputStream dos; 
	public HiloJugador(Socket socket, CyclicBarrier barrera, CountDownLatch starter, List<Integer> ordenAciertos,
				int i, String[] nombres, HiloPartida partida, DataOutputStream dos) {
		this.jugador = socket; 
		this.barrera = barrera;
		this.starter = starter;
		this.ordenAciertos = ordenAciertos;
		this.id = i;
		this.nombres = nombres;
		this.partida = partida;
		this.dos = dos;
	}
	
	@Override
	public void run() {
		try {
			
			DataInputStream dis = new DataInputStream(jugador.getInputStream());
			String nombre = dis.readLine(); 
			nombres[id] = nombre;
			
			for (int ronda = 0; ronda < 3; ronda++) {
				// Espera a que el servidor le envie a todos la ronda
				barrera.await();

				int resultadoEsperado = partida.resultadoEsperado;

				 boolean acertado = false;

		            while (!acertado) {

		                int intento = Integer.parseInt(dis.readLine()); // recibe intento del jugador

		                if (intento == resultadoEsperado) {
		                    dos.writeBytes("Correcto, has acertado" + "\n");
		                    dos.flush();
		                    acertado = true;
		                } else {
		                	dos.writeBytes("Has fallado, intentalo de nuevo" + "\n");
		                    dos.flush();
		                }
		            }

				ordenAciertos.add(id);
				barrera.await(); 
				// aqui el jugador habria contestado correctamente, por lo que termino su ronda y 
				// le tocaria esperar a que los demas acabasen.
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (BrokenBarrierException e1) {
			e1.printStackTrace();
		}
	}
	
}
