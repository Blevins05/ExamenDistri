package examenMatrices;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CalculadoraMatrices implements Callable<int[]>{
	 private Matriz matriz;
	 private int fila; // qué fila calcular
	 private int escalar; // o la otra matriz
	public CalculadoraMatrices(Matriz matriz, int fila, int escalar) {
		this.matriz = matriz;
		this.fila = fila;
		this.escalar = escalar;
	}

	@Override
	public int[] call() throws Exception {
		 System.out.println("Hilo " + Thread.currentThread().getName() + 
                 " calculando fila " + fila);
		// TODO Auto-generated method stub
		int[] nuevaFila = new int[matriz.getColumnas()];
		
		for (int i = 0; i < matriz.getColumnas(); i++) {
			nuevaFila[i] = matriz.getElemento(fila, i) * escalar;
		}

		return nuevaFila;
	}
	
}
