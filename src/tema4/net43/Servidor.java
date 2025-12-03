package tema4.net43;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Servidor {
	public static void main(String[] args) {
	    System.out.println("Servidor iniciando en puerto 8080...");
	    ExecutorService pool = Executors.newCachedThreadPool();
	    try (ServerSocket ss = new ServerSocket(8080)) 
	    {
	        System.out.println("Servidor escuchando en puerto 8080");
	        while(true) {
	            try {
	                System.out.println("Esperando conexión...");
	                Socket user = ss.accept();
	                System.out.println("Cliente conectado: " + user.getInetAddress());
	                pool.execute(new Hilo(user));
	                System.out.println("Hilo lanzado");
	            } catch(IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    } catch (IOException ex) {
	        System.out.println("Error al crear ServerSocket");
	        ex.printStackTrace(); 
	        pool.shutdown(); 
	    }
	}
}
