package tema4.net42;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Date;

public class Hilo extends Thread {
	private Socket socket;
	public Hilo(Socket s) {
		socket = s;
	}
	private File buscaFichero(String m) {
		String fileName="";
			if (m.startsWith("GET ")){
			// A partir de una cadena de mensaje (m) correcta (comienza por GET)
			fileName = m.substring(4, m.indexOf(" ", 5));
			if (fileName.equals("/")) {
				fileName += "index.html";
			}}
			if (m.startsWith("HEAD ")){
			// A partir de una cadena de mensaje (m) correcta (comienza por HEAD)
			fileName = m.substring(6, m.indexOf(" ", 7));
			if (fileName.equals("/")) {
			fileName += "index.html";
				}
			}
		return new File("ruta donde tengas el fichero descargado", fileName);
		}
		
	private void sendMIMEHeading(OutputStream os, int code, String cType, long fSize) {
		PrintStream dos = new PrintStream(os);
		dos.print("HTTP/1.1 " + code + " ");
		if (code == 200) {
		dos.print("OK\r\n");
		dos.print("Date: " + new Date() + "\r\n");
		dos.print("Server: Cutre http Server ver. -6.0\r\n");
		dos.print("Connection: close\r\n");
		dos.print("Content-length: " + fSize + "\r\n");
		dos.print("Content-type: " + cType + "\r\n");
		dos.print("\r\n");
		} else if (code == 404) {
		dos.print("File Not Found\r\n");
		dos.print("Date: " + new Date() + "\r\n");
		dos.print("Server: Cutre http Server ver. -6.0\r\n");
		dos.print("Connection: close\r\n");
		dos.print("Content-length: " + fSize + "\r\n");
		dos.print("Content-type: " + "text/html" + "\r\n");
		dos.print("\r\n");
		} else if (code == 501) {
		dos.print("Not Implemented\r\n");
		dos.print("Date: " + new Date() + "\r\n");
		dos.print("Server: Cutre http Server ver. -6.0\r\n");
		dos.print("Connection: close\r\n");
		dos.print("Content-length: " + fSize + "\r\n");
		dos.print("Content-type: " + "text/html" + "\r\n");
		dos.print("\r\n");
		}
		dos.flush();
		}
	
		private String makeHTMLErrorText(int code, String txt) {
		StringBuffer msg = new StringBuffer("<HTML>\r\n");
		msg.append(" <HEAD>\r\n");
		msg.append(" <TITLE>" + txt + "</TITLE>\r\n");
		msg.append(" </HEAD>\r\n");
		msg.append(" <BODY>\r\n");
		msg.append(" <H1>HTTP Error " + code + ": " + txt + "</H1>\r\n");
		msg.append(" </BODY>\r\n");
		msg.append("</HTML>\r\n");
		return msg.toString();
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		/*
		 Pasos a seguir:
		 	1. Leer la linea de peticion (tú eres el servidor)
		 	2. Saltar las cabeceras (simplemente hacer un while hasta que encuentres la linea en blanco)
		 	3. Encontrar el fichero: Si no existe, llamar a sendMIME con codigo 404 (fichero no encontrado)
		 		Si existe, diferenciar si la peticion era GET o HEAD, si es GET mostrar en navegador, si es HEAD
		 		simplemente mandar codigo 200 OK
		 *  */
		
		try (DataInputStream dis = new DataInputStream(socket.getInputStream());
				OutputStream os = socket.getOutputStream();
				BufferedWriter bwr = new BufferedWriter(new OutputStreamWriter(os)))
		{
			
			System.out.println("Streams creados");
	        String requestLine = dis.readLine();
	        System.out.println("RequestLine leída: " + requestLine);
	        if (requestLine == null || requestLine.isEmpty()) {
	            socket.close();
	            return;
	        }
	        
	        while (!dis.readLine().equals("")) {
	        	// nada: aqui llegamos hasta la linea en blanco que separa las cabeceras del cuerpo
	        }
			
	        File fichero = buscaFichero(requestLine);
	        System.out.println("Request: " + requestLine);
	        System.out.println("Fichero buscado: " + fichero.getAbsolutePath());
	        System.out.println("Existe? " + fichero.exists());
	        
	        if (!fichero.exists()) {
	        	String htmlError = makeHTMLErrorText(404, "File not found");
	        	sendMIMEHeading(os, 404, "text/html", htmlError.length());
	        	bwr.write(htmlError);
	        	
	        } else {
	        	if (requestLine.startsWith("GET")) {
	        		String mime = java.net.URLConnection.guessContentTypeFromName(fichero.getName());
	        		sendMIMEHeading(os, 200, mime, fichero.length());
	        		os.flush();
	        		
	        		try(FileInputStream fis = new FileInputStream(fichero))     		
	        		{
	        			byte[] buff = new byte[2048];
        				int bytesRead;
	        			while((bytesRead = fis.read(buff)) != -1) {
	        				os.write(buff, 0, bytesRead);
	        			}
	        				
	        			os.flush();	
	        				
	        				
	        		} catch (IOException ex) {
	        			ex.printStackTrace();
	        		}
	
	        	} else if (requestLine.startsWith("HEAD")) {
	        		String mime = java.net.URLConnection.guessContentTypeFromName(fichero.getName());
	        		sendMIMEHeading(os, 200, mime, fichero.length());
	        		os.flush();
	        		
	        	}
	        }
	      	
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

}
