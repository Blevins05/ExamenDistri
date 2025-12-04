package examenB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newCachedThreadPool();
		int puerto = 8080;
		try (ServerSocket ss = new ServerSocket(puerto)) {
			while (true) {
				try {
					Socket cliente = ss.accept();
					pool.execute(new AtenderPeticion(cliente));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pool.shutdown();
		} 
	}
}
