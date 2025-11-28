package tema2.net3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteMenu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean seguir = true;

        while (seguir) {

            System.out.println("---- MENU ----");
            System.out.println("1. Añadir teléfono (PUT)");
            System.out.println("2. Obtener teléfono (GET)");
            System.out.println("3. Salir");
            System.out.print("Elige opción: ");

            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {

                case 1:
                    System.out.print("Nombre: ");
                    String nombrePut = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String tfno = sc.nextLine();

                    enviarPeticion("PUT " + nombrePut + " " + tfno);
                    break;

                case 2:
                    System.out.print("Nombre: ");
                    String nombreGet = sc.nextLine();

                    enviarPeticion("GET " + nombreGet);
                    break;

                case 3:
                    seguir = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        System.out.println("Cliente desconectado.");
    }


    private static void enviarPeticion(String msg) {
        try (Socket s = new Socket("localhost", 6666);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {

            out.write(msg + "\n");
            out.flush();

            System.out.println("Servidor: " + in.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
