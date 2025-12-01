package tema3.th5;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebClient {
	private String host;
	private int port;
	
	public WebClient(String h, int p) {
		this.host = h;
		this.port = p;
	}
	
	
	public void descargaSinGuardar() {
		try (Socket s = new Socket(host, port);
				DataInputStream dis = new DataInputStream(s.getInputStream())) {
					
		String linea;
		
		while ((linea = dis.readLine()) != null) {
			System.out.println(linea);
		}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
