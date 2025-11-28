package tema2.net6;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 9999;
        
        try (Socket s = new Socket(servidor, puerto);
             DataInputStream dis = new DataInputStream(s.getInputStream());
             DataOutputStream dos = new DataOutputStream(s.getOutputStream());
             Scanner sc = new Scanner(System.in)) {
            
            System.out.println("Conectado al servidor de eco");
            System.out.println("Escribe frases (termina con *):");
            
            // enviar mensajes hasta *
            String linea;
            while (!(linea = sc.nextLine()).equals("*")) {
                dos.writeBytes(linea + "\n");
            }
            dos.writeBytes("*\n"); // enviar el *
            dos.flush();
            
            System.out.println("\nRespuestas del servidor:");
            
            int mensajes = dis.readInt();
            int i = 0;
            // recibir y mostrar respuestas al cliente
            try {
                while (i < mensajes) {
                    String respuesta = dis.readLine();
                    System.out.println(respuesta);
                    i++;
                }
            } catch (EOFException e) {
            	e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}