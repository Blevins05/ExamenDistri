package examenMatrices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (Socket s = new Socket("localhost", 8080);
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream())) 
		{
			Scanner sc = new Scanner(System.in);
			String opcion = "-1";
			do {
				opcion = mostrarMenu();
				
				switch (opcion) {
					case "1":
						System.out.println("Introduce el número de filas de la matriz a enviar");
						int filas = Integer.parseInt(sc.nextLine());
						System.out.println("Introduce el número de columnas de la matriz a enviar");
						int columnas = Integer.parseInt(sc.nextLine());
						System.out.println("Se ha creado tu matriz de " + filas + " filas y " + columnas + " columnas");
						Matriz matriz = new Matriz(filas, columnas);
						System.out.println("Introduce los valores de tu matriz en cada posicion");
						matriz.agregarValores();
						System.out.println("Ahora, introduce un escalar por el que multiplicar tu matriz");
						int k = Integer.parseInt(sc.nextLine());
						
						System.out.println("Enviando matriz y escalar...");
						oos.writeInt(1);
						oos.writeObject(matriz);
						oos.flush();
						
						oos.writeInt(k);
						oos.flush();
						
						Matriz resultado = (Matriz) ois.readObject();
						System.out.println("Matriz multiplicada: ");
						resultado.mostrar();
						
						break;
						
					case "2":
						
						System.out.println("Introduce el número de filas de la matriz 1 a enviar");
						int filas1 = Integer.parseInt(sc.nextLine());
						System.out.println("Introduce el número de columnas de la matriz 1 a enviar");
						int columnas1 = Integer.parseInt(sc.nextLine());
						System.out.println("Se ha creado tu matriz 1 de " + filas1 + " filas y " + columnas1 + " columnas");
						Matriz matriz1 = new Matriz(filas1, columnas1);
						System.out.println("Introduce los valores de tu matriz en cada posicion");
						matriz1.agregarValores();
						
						System.out.println("Introduce el número de filas de la matriz 2 a enviar");
						int filas2 = Integer.parseInt(sc.nextLine());
						System.out.println("Introduce el número de columnas de la matriz 2 a enviar");
						int columnas2 = Integer.parseInt(sc.nextLine());
						System.out.println("Se ha creado tu matriz 2 de " + filas2 + " filas y " + columnas2 + " columnas");
						Matriz matriz2 = new Matriz(filas2, columnas2);
						System.out.println("Introduce los valores de tu matriz en cada posicion");
						matriz2.agregarValores();
						
						System.out.println("Enviando ambas matrices");	
						oos.writeInt(2);
						oos.writeObject(matriz1);
						oos.writeObject(matriz2);
						oos.flush();
						
						Matriz multiplicada = (Matriz) ois.readObject();
						System.out.println("Matrices mutliplicadas: ");
						multiplicada.mostrar();
						
						break;
				
					case "3":
						System.out.println("Desconectando");
						break;
				}
				
				
			} while (!opcion.equals("3"));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String mostrarMenu() {
		System.out.println("Menu de la aplicación: ");
		System.out.println("1. Multiplicar por un escalar");
		System.out.println("2. Multiplicar 2 matrices");
		System.out.println("3. Desconectarse");
		System.out.println("Elige una opcion");
		return new Scanner(System.in).nextLine();
	}

}
