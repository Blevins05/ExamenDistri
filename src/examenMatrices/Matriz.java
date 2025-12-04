package examenMatrices;

import java.io.Serializable;
import java.util.Scanner;

public class Matriz implements Serializable {
	private final long serialVersionUID = 1L; // por si en un futuro se modificase la clase
	private int[][] matriz;
	private int filas;
	private int columnas;
	
	public Matriz(int i, int j) {
		this.filas = i;
		this.columnas = j;
		matriz = new int[filas][columnas];
	}
	
	public Matriz(int[][] datos) {
	    this.filas = datos.length;
	    this.columnas = datos[0].length;
	    this.matriz = datos;
	}
	
	public int getFilas() {
		return filas;
	}
	
	public int getColumnas() {
		return columnas;
	}
	
	public int getElemento(int fila, int columna) {
		return matriz[fila][columna];
	}
	
	public void agregarValores() {
		Scanner sc = new Scanner(System.in);
		
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				System.out.println("Introduce el elemento " + (i+1) + (j+1) + " de la matriz");
				int num = Integer.parseInt(sc.nextLine());
				matriz[i][j] = num;
			}
		}
	}
	
	public void mostrar() {		
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				System.out.println("Elemento " + (i+1) + (j+1) + " de la matriz = " + matriz[i][j]);	
			}
		}
	}
}
