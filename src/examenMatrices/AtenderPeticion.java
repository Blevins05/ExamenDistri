package examenMatrices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AtenderPeticion implements Runnable {
	private Socket cliente;
	private CalculadoraMatrices calc;
	public AtenderPeticion(Socket cliente) {
		// TODO Auto-generated constructor stub
		this.cliente = cliente;
	}

	
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try (ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
						ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream())) {
			
			int peticion = ois.readInt();
			
			if (peticion == 1) {
				Matriz matriz = (Matriz) ois.readObject();
				int escalar = ois.readInt();
				ExecutorService pool = Executors.newFixedThreadPool(matriz.getFilas());
				
				List<Future<int[]>> futures = new ArrayList<>();
				
				for (int i = 0; i < matriz.getFilas(); i++) {
					futures.add(pool.submit(new CalculadoraMatrices(matriz, i, escalar)));
				}
				int[][] matrizResultado = new int[matriz.getFilas()][matriz.getColumnas()];

				for (int i = 0; i < futures.size(); i++) {
				    int[] filaResultado = futures.get(i).get();
				    matrizResultado[i] = filaResultado;
				}

				Matriz resultado = new Matriz(matrizResultado);
				
				oos.writeObject(resultado);
				oos.flush();
				pool.shutdown();
				
			} else if (peticion == 2) {
				Matriz m1 = (Matriz) ois.readObject();
				Matriz m2 = (Matriz) ois.readObject();
				
				if (m1.getColumnas() != m2.getFilas()) {
					oos.writeBytes("Las matrices enviadas no se pueden multiplicar. ERROR");
				} else {
					ExecutorService pool = Executors.newFixedThreadPool(m1.getColumnas());
					
					List<Future<int[]>> futures = new ArrayList<>();
					for (int i = 0; i < m2.getColumnas(); i++) {
						futures.add(pool.submit(new MultiplicadorMatrices(m1, m2, i)));
					}
					int[][] resultadoFinal = new int[m1.getFilas()][m2.getColumnas()];
					
					for (int i = 0; i < futures.size(); i++) {
						int[] filaMultiplicada = futures.get(i).get();
						resultadoFinal[i] = filaMultiplicada;
					}
					Matriz resultado = new Matriz(resultadoFinal);
					oos.writeObject(resultado);
					oos.flush();
					pool.shutdown();
				}
				
			} else {
				// Peticion invalida
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
