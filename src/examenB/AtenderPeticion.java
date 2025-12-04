package examenB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class AtenderPeticion implements Runnable {

    private Socket cliente;

    public AtenderPeticion(Socket s) {
        this.cliente = s;
    }

    @Override
    public void run() {
        try (
            BufferedReader bfr = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter pw = new PrintWriter(cliente.getOutputStream())
        ) {

            // Leer la línea de petición
            String lineaPeticion = bfr.readLine();

            // Leer cabeceras hasta línea vacía
            String header;
            while ((header = bfr.readLine()) != null && !header.isEmpty()) {}

            if (lineaPeticion == null) return;

            if (lineaPeticion.startsWith("GET /primos")) {

                String recurso = lineaPeticion.split(" ")[1];
                int N = -1, M = -1;

                // /primos?N=12&M=3
                String[] partes = recurso.split("[?&]");
                for (String p : partes) {
                    if (p.startsWith("N=")) N = Integer.parseInt(p.substring(2));
                    if (p.startsWith("M=")) M = Integer.parseInt(p.substring(2));
                }

                GestorPrimos gp = new GestorPrimos(N, M);
                ArrayList<Integer> primos = gp.call();
                int[] encontrados = gp.getEncontradosPorHilo();

                enviarRespuestaHTTP(pw, primos, N, M, encontrados);
                pw.flush();

            } else if (lineaPeticion.startsWith("GET /rsa")) {

                String recurso = lineaPeticion.split(" ")[1];
                int numero = Integer.parseInt(recurso.split("=")[1]);

                Factorizador f = new Factorizador(numero);
                ArrayList<Integer> factores = f.factorizar();

                enviarRespuestaRSA(pw, numero, factores);
                pw.flush();

            } else {
                pw.println("HTTP/1.1 400 Bad Request");
                pw.println("");
                pw.println("Petición inválida");
                pw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { cliente.close(); } catch (Exception ex) {}
        }
    }

    private void enviarRespuestaHTTP(PrintWriter pw, ArrayList<Integer> primos, int N, int M, int[] encontrados) {
        StringBuilder xml = new StringBuilder();

        xml.append("<resultados>\n");
        xml.append("<N>").append(N).append("</N>\n");
        xml.append("<M>").append(M).append("</M>\n");

        for (int i = 0; i < encontrados.length; i++) {
            xml.append("<hilo numero='").append(i).append("'>")
               .append(encontrados[i])
               .append("</hilo>\n");
        }

        xml.append("<primos>");
        for (int p : primos) xml.append(p).append(" ");
        xml.append("</primos>\n");

        xml.append("</resultados>");

        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-Type: text/xml");
        pw.println("Content-Length: " + xml.length());
        pw.println(""); 
        pw.println(xml.toString());
    }

    private void enviarRespuestaRSA(PrintWriter pw, int numero, ArrayList<Integer> factores) {
        StringBuilder xml = new StringBuilder();
        xml.append("<rsa>\n");

        xml.append("<numero>").append(numero).append("</numero>\n");
        xml.append("<factores>");
        for (int f : factores) xml.append(f).append(" ");
        xml.append("</factores>\n");

        xml.append("</rsa>");

        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-Type: text/xml");
        pw.println("Content-Length: " + xml.length());
        pw.println("");
        pw.println(xml.toString());
    }
}
