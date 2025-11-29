package examenA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        String host = "localhost"; 
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             Scanner sc = new Scanner(System.in)) {

        	// Enviamos el nombre al servidor 
            System.out.print("Introduce tu nombre: ");
            String nombre = sc.nextLine();
            dos.writeBytes(nombre + "\n");
            dos.flush();

            // Jugamos 3 rondas
            for (int ronda = 0; ronda < 3; ronda++) {
                // Recibimos la operacion del servidor
                String operacion = dis.readLine();
                System.out.println("Ronda " + (ronda + 1) + ": " + operacion);

                String intentoJugador;
                String mensaje;
                do {
                    System.out.print("Introduce tu respuesta: ");
                    intentoJugador = sc.nextLine();
                    dos.writeBytes(intentoJugador + "\n");
                    dos.flush();

                    mensaje = dis.readLine(); // Mensaje del servidor
                    System.out.println("Servidor: " + mensaje);

                } while (!mensaje.contains("Correct"));
            }

            // Recibir XML
 
            long size = dis.readLong();
            System.out.println("Tamaño recibido: " + size);

            byte[] buffer = new byte[(int) size];
            dis.readFully(buffer);
            System.out.println("XML recibido");

            try (FileOutputStream fos = new FileOutputStream("Partida_Recibida.xml")) {
                fos.write(buffer);
            }
            System.out.println("XML guardado");

            // Recibir nombre del ganador
            System.out.println("Esperando nombre del ganador...");
            String ganador = dis.readLine();
            System.out.println("El ganador es: " + ganador);

            System.out.println("Partida descargada correctamente como Partida_Recibida.xml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
