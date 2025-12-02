package tema4.net41;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorDescargas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(3);
		CountDownLatch cdl = new CountDownLatch(3);
		try {
			String ur="https://www.josepdeulofeu.com/wp-content/uploads/2022/06/test-seo-1.gif";
			URL url = new URL(ur);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("HEAD");
			long tamano = con.getContentLengthLong();
			String directorio = "ExamenDistri";
			
			long partes = tamano / 3;
			long inicio = 0;
			long fin = partes;
			
			for (int i = 0; i < 2; i++) {
				pool.execute(new Descargador(url, directorio, inicio, fin, cdl));
				inicio = fin+1;
				fin += partes;
			}
			cdl.await(); // Se queda esperando el hilo principal (cuando los 3 descargadores acaben se puede seguir)
			
			System.out.println("Recurso correctamente descargado en: " + directorio); // gif de homer
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 
		 
		 
	}

}
