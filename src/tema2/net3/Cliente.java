package tema2.net3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String hostname = "localhost";
		System.out.println("Escribe la operacion a realizar: PUT nombre numero : GET nombre");
		String mensaje = sc.nextLine();
		try (Socket s = new Socket(hostname, 6666);
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {
			
			dos.writeBytes(mensaje + "\n");
			dos.flush();
			
			String respuesta = dis.readLine();
			System.out.println(respuesta);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
