package examenA;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		int gamePort = 8080;
		try (ServerSocket ss = new ServerSocket(gamePort)) {
			while (true) {
				try {
					Socket cliente = ss.accept();
					pool.execute(new AtenderPeticion(cliente));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException ex) {ex.printStackTrace(); pool.shutdown(); }
	}
}
