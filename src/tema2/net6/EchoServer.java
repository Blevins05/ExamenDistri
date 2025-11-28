package tema2.net6;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class EchoServer {
    public static void main(String[] args) {
        int puerto = 9999;
        
        try (ServerSocket ss = new ServerSocket(puerto)) {
            System.out.println("Servidor de eco escuchando en puerto " + puerto);
            
            while (true) {
                try (Socket cliente = ss.accept();
                     DataInputStream dis = new DataInputStream(cliente.getInputStream());
                     DataOutputStream dos = new DataOutputStream(cliente.getOutputStream())) {
                    
                    System.out.println("Cliente conectado: " + cliente.getInetAddress());
                    
                    String ip = cliente.getLocalAddress().getHostAddress();
                    int port = cliente.getLocalPort();
                    
                    ArrayList<String> mensajes = new ArrayList<>();
                    ArrayList<String> timestamps = new ArrayList<>();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    
                    // recibir mensajes hasta encontrar * (los guardamos en una coleccion)
                    String linea;
                    while ((linea = dis.readLine()) != null) {
                        if (linea.equals("*")) {
                            break;
                        }
                        mensajes.add(linea);
                        timestamps.add(sdf.format(new Date()));
                    }
                    dos.writeInt(mensajes.size());
                    // enviar todas las respuestas al cliente
                    for (int i = 0; i < mensajes.size(); i++) {
                        String respuesta = "Echo server at " + ip + ":" + port + 
                                         " [" + timestamps.get(i) + "]: " + mensajes.get(i);
                        dos.writeBytes(respuesta + "\n");
                    }
                    dos.flush();
                    
                    System.out.println("Enviadas " + mensajes.size() + " respuestas al cliente");
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}