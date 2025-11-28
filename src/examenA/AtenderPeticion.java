package examenA;

import java.net.Socket;

public class AtenderPeticion extends Thread{
	private Socket socket;
	public AtenderPeticion(Socket s) {
		this.socket = s;
	}
	
}
