package tema1.io6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int res = contarRepeticiones("HMR");
		System.out.println(res);
	}

	private static int contarRepeticiones(String patron) {
		// TODO Auto-generated method stub
		int cont = 0;
		try (BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream("fich_t1_ej6"))))
		{
			String linea;
			
			while((linea = bfr.readLine()) != null) {
				int pos = 0;
				while(linea.indexOf(patron, pos) != -1) {
					pos = linea.indexOf(patron, pos)+patron.length();
					cont++;
				}
			}
			System.out.println("Se han obtenido " + cont + " repeticiones");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return cont;
	}
	
}
