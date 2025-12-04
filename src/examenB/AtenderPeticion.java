package examenB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class AtenderPeticion implements Runnable {
	private Socket cliente;
	private GestorPrimos gestorPrimos;
	private Factorizador factorizador;
	public AtenderPeticion(Socket s) {
		this.cliente = s;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try (BufferedReader bfr = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				PrintWriter pw = new PrintWriter(cliente.getOutputStream())) {
			
			
			String lineaPeticion = bfr.readLine(); // leemos la linea de peticion
			
			// saltamos cabeceras
			while (!bfr.readLine().equals("")) {
				
			}
			
			// vamos a ver que tipo de peticion quiere el cliente
			
			if (lineaPeticion.startsWith("GET /primos")) {
				String recurso = lineaPeticion.split(" ")[1]; 
				// ejemplo: "/primos?N=12&M=3"
				String[] partes = recurso.split("[?&]");			
				int N = -1, M = -1;
				
				for (String p : partes) {
				    if (p.startsWith("N=")) {
				        N = Integer.parseInt(p.substring(2));
				    } else if (p.startsWith("M=")) {
				        M = Integer.parseInt(p.substring(2));
				    }
				}
						
				gestorPrimos = new GestorPrimos(N, M);
				ArrayList<Integer> primos = gestorPrimos.call();
				
			} else if (lineaPeticion.startsWith("GET /rsa")) {
				String recurso = lineaPeticion.split(" ")[1]; 
				// ejemplo: "/rsa?num="3"
				String[] partes = recurso.split("\\?");			
				int numero = Integer.parseInt(partes[1].split("=")[1]);
				factorizador = new Factorizador(numero);
				
				
			} else {
				pw.write("Peticion Invalida. No has mandado bien la petición");
				
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
