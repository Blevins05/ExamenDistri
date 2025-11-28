package tema2.net5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Nombre del fichero a mandar (o su ruta)");
		String nombre = sc.nextLine();
		long tamano = new File(nombre).length();
		enviaFichero(nombre, tamano);

	}
	
	public static void enviaFichero(String nombre, long tamano) {
		try (Socket s = new Socket("localhost", 6666);
				FileInputStream fis = new FileInputStream(nombre);
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()))
		{
			
			dos.writeBytes(nombre + "\n");
			dos.writeLong(tamano);
			
			
			
			
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
