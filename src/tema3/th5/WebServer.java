package tema3.th5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newCachedThreadPool();
		String rutaFichero = "LICENSE";
		try (ServerSocket ss = new ServerSocket(8080)) {
			while (true) {
				try {
					Socket cliente = ss.accept();
					pool.execute(new AtenderPeticion(cliente, rutaFichero));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			pool.shutdown();
		}
	}

}
