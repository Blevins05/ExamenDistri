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

			// primera mitad:
			long mitad = tamano / 2;
			
			int leidos_linea, leidos_total = 0;
			byte[] buff = new byte[1024];
			while(leidos_total < mitad) {
				leidos_linea = fis.read(buff);
				dos.write(buff, 0, leidos_linea);
				leidos_total += leidos_linea;
			}
			dos.flush();
			
			dos.writeBytes("He mandado la mitad\n");
			
		
			// segunda mitad:
			while ((leidos_linea = fis.read(buff)) != -1) {
				dos.write(buff, 0, leidos_linea);
			}
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
