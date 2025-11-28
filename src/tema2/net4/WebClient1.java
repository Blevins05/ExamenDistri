package tema2.net4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WebClient1 {
	public static void main(String[] args) {
		recuperaRecurso("i1.wp.com", 80, "/hipertextual.com/wp-content/uploads/2015/09/googles-new-logo-.gif" );
	}
	
	public static void recuperaRecurso(String hostname, int port, String path) {
		try (Socket s = new Socket(hostname, port);
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {
			
			String peticion = "GET " + path + " HTTP/1.1";
			dos.writeBytes(peticion + "\r\n");
			dos.writeBytes("Host: " + hostname + "\r\n");
			dos.writeBytes("\r\n");
			dos.flush();
			
			int contentLength = 0;
			String lineaEstado = dis.readLine();
			System.out.println("Linea de estado: " + lineaEstado);
			
			String linea;
			linea = dis.readLine();
			while ((!linea.equals(""))) {
				// skipear cabeceras (literalmente hacer nada) y coger la longitud de la respuesta
				if (linea.startsWith("Content-Length: ")) {
					contentLength = Integer.parseInt(linea.split(" ")[1]);
				}
					linea = dis.readLine();
			}
			
			// leer el recurso devuelto.
			String[] partes = path.split("/");
			String nombreFichero = partes[partes.length - 1];
			try (FileOutputStream fos = new FileOutputStream(nombreFichero)) {
				int total_leidos = 0; int leidos_linea = 0;
				byte[] buff = new byte[1024];
				
				while ((total_leidos < contentLength) && (leidos_linea = dis.read(buff)) != -1) {
					fos.write(buff, 0, leidos_linea);
					total_leidos += leidos_linea;
				}
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
