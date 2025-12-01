package tema3.th5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class AtenderPeticion implements Runnable{
	private Socket cliente;
	private String ruta;
	
	public AtenderPeticion(Socket cliente, String r) {
		// TODO Auto-generated constructor stub
		this.cliente = cliente;
		this.ruta = r;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try(FileInputStream fis = new FileInputStream(ruta);
				DataInputStream dis = new DataInputStream(fis);
						DataOutputStream dos = new DataOutputStream(cliente.getOutputStream())) {
			
			// Enviar fichero al cliente:
			
			String linea;
			
			while ((linea = dis.readLine()) != null) {
				dos.writeBytes(linea + "\n");
			}
			dos.flush();
			
		} catch (IOException ex) {ex.printStackTrace();}
		finally {
			try {
				cliente.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		
	}

}
