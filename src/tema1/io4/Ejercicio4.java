package tema1.io4;
import java.io.*;
import java.util.Scanner;

public class Ejercicio4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el modo (1 = DataInput/Output, 2 = Buffered): ");
        int modo = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Introduce el archivo de entrada: ");
        String input = sc.nextLine();

        System.out.print("Introduce el archivo de salida: ");
        String output = sc.nextLine();

        copiarArchivo(modo, input, output);

        sc.close();
    }


    public static void copiarArchivo(int modo, String input, String output) {

        if (modo == 1) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(input));
                 DataOutputStream dos = new DataOutputStream(new FileOutputStream(output))) {

                String linea;
                while ((linea = dis.readLine()) != null) {  
                    dos.writeBytes(linea + "\n");
                }
                dos.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if (modo == 2) {
            try (BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
                 BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)))) {

                String linea;
                while ((linea = bfr.readLine()) != null) {
                    bfw.write(linea + "\n");
                }
                bfw.flush();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("Modo inválido. Debe ser 1 o 2.");
        }
    }
}
