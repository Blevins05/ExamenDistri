package tema2.net5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (ServerSocket ss = new ServerSocket(6666)) {
			while (true) {
				try (Socket cliente = ss.accept();
						DataInputStream dis = new DataInputStream(cliente.getInputStream());) 
				{
					
					String nombre = dis.readLine();
					long tamano = dis.readLong();
					try (FileOutputStream fos = new FileOutputStream("recibido_" + nombre)) {
						
						// primera mitad 
						long mitad = tamano / 2;
						
						int leidos_linea, leidos_total = 0;
						byte[] buff = new byte[1024];
						while(leidos_total < mitad) {
							leidos_linea = dis.read(buff);
							fos.write(buff, 0, leidos_linea);
							leidos_total += leidos_linea;
						}
						
						fos.flush();
						
						String mitadCompletada = dis.readLine();
						
						System.out.println(mitadCompletada);
						
						// segunda mitad:
						while ((leidos_linea = dis.read(buff)) != -1) {
							fos.write(buff, 0, leidos_linea);
						}

					}
					
					
				} catch (IOException ex) 
					{ 
						ex.printStackTrace(); 
					}
			}
		} catch (IOException ex) { ex.printStackTrace(); }
	}

}
