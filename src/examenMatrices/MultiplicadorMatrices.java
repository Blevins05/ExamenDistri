package examenMatrices;

import java.util.concurrent.Callable;

public class MultiplicadorMatrices implements Callable<int[]>{
	 private Matriz matriz1;
	 private Matriz matriz2;
	 private int fila;
	public MultiplicadorMatrices(Matriz matriz1, Matriz matriz2, int fila) {
		this.matriz1 = matriz1;
		this.matriz2 = matriz2;
		this.fila = fila;
	}

	@Override
	public int[] call() throws Exception {
		 System.out.println("Hilo " + Thread.currentThread().getName() + 
                 " calculando fila " + fila);
		// TODO Auto-generated method stub
		int[] nuevaFila = new int[matriz2.getColumnas()];
		
		for (int j = 0; j < matriz2.getColumnas(); j++) {
			int suma = 0;
			for (int k = 0; k < matriz1.getColumnas(); k++) {
				suma += matriz1.getElemento(fila, k) * matriz2.getElemento(k, j);
			}
			nuevaFila[j] = suma;
		}
		return nuevaFila;
	}
	
}
