package tema1.io9;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Ejercicio9 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Codificacion de tu fichero: " + analizaCodificacion("ruta de tu fichero aqui"));
	}
	
	 public static String analizaCodificacion(String fichero) {
	        ArrayList<Integer> bytes = new ArrayList<>();

	        try (FileInputStream fis = new FileInputStream(fichero)) {
	            int leido;
	            while ((leido = fis.read()) != -1) {
	                bytes.add(leido);
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }

	        boolean ascii = false;
	        boolean utf8 = false;
	        boolean otra = false;

	        for (int i = 0; i < bytes.size(); i++) {

	            int a = bytes.get(i);

	            // 7 bits
	            if (cumplePatron7Bits(a)) {
	                ascii = true;
	            }

	            // 2 bytes
	            if (i + 1 < bytes.size()) {
	                int b = bytes.get(i + 1);
	                if (cumplePatron2Bytes(a, b)) {
	                    utf8 = true;
	                }
	            }

	            // 3 bytes
	            if (i + 2 < bytes.size()) {
	                int b = bytes.get(i + 1);
	                int c = bytes.get(i + 2);
	                if (cumplePatron3Bytes(a, b, c)) {
	                    utf8 = true;
	                }
	            }

	            // 8 bits (no UTF-8)
	            if (cumplePatron8Bits(a)) {
	                otra = true;
	            }
	        }

	        if (ascii && !utf8 && !otra) return "ASCII";
	        if (utf8) return "UTF-8";
	        if (!utf8 && otra) return "otra codificacion no UTF-8";

	        return "indefinido";
	    }
	
	public static boolean cumplePatron7Bits(int a) {
		return a >= 0 && a <= 127; // entre 0 y 127
	}
	
	public static boolean cumplePatron2Bytes(int a, int b) {
		return (192 <= a && a <= 223) && (128 <= b && b <= 191);
	}
	
	public static boolean cumplePatron3Bytes(int a, int b, int c) {
		return (224 <= a && a <= 239) && (128 <= b && b <= 191) && (128 <= c && c <= 191);
	}
	
	public static boolean cumplePatron8Bits(int a) {
	    return a >= 128; // simplemente el primer bit es 1
	}

}
