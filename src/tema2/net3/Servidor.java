package tema2.net3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) {
		AgendaTfno agenda = new AgendaTfno();
		try (ServerSocket ss = new ServerSocket(6666)) {
			
			while (true) {
				try (Socket client = ss.accept();
						BufferedReader bfr = new BufferedReader(new InputStreamReader(client.getInputStream()));
						BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
					
					String request = bfr.readLine();
					
					String[] partes = request.split(" ");
					String requestType = partes[0];
					
					if (requestType.equals("GET")) {
						String nombre = partes[1];
						String tfno = (agenda.getTfno(nombre) != null) ? agenda.getTfno(nombre): "Desconocido";
						bfw.write(tfno + "\n");
					} else if (requestType.equals("PUT")) {
						agenda.añadeTelefono(partes[1], partes[2]);
						bfw.write("OK\n");
					} else {
						 bfw.write("ERROR\n");
						   bfw.flush();
					}
					bfw.flush();
				}
			}
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}