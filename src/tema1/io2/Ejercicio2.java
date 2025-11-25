package tema1.io2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (FileInputStream fis = new FileInputStream("fich2_t1_ej2");
				FileOutputStream fos = new FileOutputStream("fich2_t1_ej2")) {
			
			int bs = 1024;
			byte[] buff = new byte[bs];
			
			int leidos;
			
			// Lectura y escritura por bloques: Ojo muy importante hacer esto (fallo de hacha)
			// porque 
			while((leidos = fis.read(buff)) != -1) {
				fos.write(buff, 0, leidos);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
