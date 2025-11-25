package tema1.io1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio1 {

	public static void main(String[] args) {
		try (FileInputStream fis = new FileInputStream("fich1_t1_ej1");
				FileOutputStream fos = new FileOutputStream("fich2_t1_ej1")) {
			
			
			int leido;
			// Leer y escribir byte a byte
			while((leido = fis.read()) != -1) {
				fos.write(leido);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
}
