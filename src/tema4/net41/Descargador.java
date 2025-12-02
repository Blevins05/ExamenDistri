package tema4.net41;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class Descargador implements Runnable{

	private URL url;
	private String directorio;
	private long byteInicio, byteFin;
	private CountDownLatch cdl;
	
	
	public Descargador(URL u, String d, long bi, long bf, CountDownLatch cdl) {
		this.url = u;
		this.directorio = d;
		this.byteInicio = bi;
		this.byteFin = bf;
		this.cdl = cdl;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 HttpURLConnection con = null;
		 try  {
			 String[] partes = url.getPath().split("/");
			 String nombreFichero = partes[partes.length-1];
			 
			 con = (HttpURLConnection) url.openConnection();
			 con.setRequestProperty("Range", "bytes=" + byteInicio + "-" + byteFin);
			 DataInputStream dis = new DataInputStream(con.getInputStream()); // duda, se pone asi o se le pasa el openStream ??
			 RandomAccessFile raf = new RandomAccessFile(nombreFichero, "rw");
			 raf.seek(byteInicio);
			 
			 int leidos;
			 byte[] buffer = new byte[2048];
			 while ((leidos = dis.read(buffer)) != -1) {
				 raf.write(buffer, 0, leidos);
			 }
			 cdl.countDown(); // el hilo termino de hacer su tarea.
			 
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
