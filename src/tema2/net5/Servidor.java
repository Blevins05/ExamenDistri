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
						DataInputStream dis = new DataInputStream(cliente.getInputStream());
								 DataOutputStream dos = new DataOutputStream(cliente.getOutputStream())) 
				{
					
					String nombre = dis.readLine();
					long tamano = dis.readLong();
					try (FileOutputStream fos = new FileOutputStream(nombre)) {
						
					}
					
					
					
					
					
					
					
					
					
				} catch (IOException ex) 
					{ 
						ex.printStackTrace(); 
					}
			}
		} catch (IOException ex) { ex.printStackTrace(); }
	}

}
