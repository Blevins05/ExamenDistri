package examenB;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "localhost";
		int puerto = 8080;
		Scanner sc = new Scanner(System.in);
		try (Socket s = new Socket(host, puerto);
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				BufferedReader bfr = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
			
			int opcion = -1;
			
			do {
				System.out.println("Menú:");
				System.out.println("1. Calcular los N primeros números primos");
				System.out.println("2. Romper RSA");
				System.out.println("3. Desconectar");
				System.out.println("Elige una opcion:");
				opcion = Integer.parseInt(sc.nextLine());
				
				switch (opcion) {
					case 1:
						System.out.println("Introduce N (mayor que 0), numero de primos que quieres calcular");
						int N = Integer.parseInt(sc.nextLine());
						System.out.println("Introduce el numero de hilos con los que quieres trabajar");
						int M = Integer.parseInt(sc.nextLine());
						 pw.println("GET /primos?N=" + N + "&M=" + M + " HTTP/1.1");
	                        pw.println("Host: localhost");
	                        pw.println("Connection: close");
	                        pw.println(""); // MUY IMPORTANTE
	                        obtenerRespuesta(bfr);
						break;
					case 2:
						System.out.println("Introduce el número que quieres factorizar en primos");
						int num = Integer.parseInt(sc.nextLine());
						  pw.println("GET /rsa?num=" + num + " HTTP/1.1");
	                      pw.println("Host: localhost");
	                      pw.println("Connection: keep-alive");
	                      pw.println(""); // mandamos linea en blanco
	                      pw.flush();
	                      obtenerRespuesta(bfr);
	                       
						break;
					case 3:
						System.out.println("Desconectando...");
						s.close();
						break;
				}
			} while (opcion != 3);
	
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void obtenerRespuesta(BufferedReader bfr) {
	    try {
	        // 1) Leer línea de estado
	        String linea = bfr.readLine();
	        System.out.println(linea);

	        int contentLength = 0;

	        // 2) Leer cabeceras hasta línea vacía
	        while (!(linea = bfr.readLine()).equals("")) {
	            System.out.println(linea);

	            if (linea.startsWith("Content-Length:"))
	                contentLength = Integer.parseInt(linea.split(":")[1].trim());
	        }

	        // 3) Leer exactamente contentLength caracteres del cuerpo
	        char[] buffer = new char[contentLength];
	        bfr.read(buffer, 0, contentLength);

	        System.out.println(new String(buffer));

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}



}
