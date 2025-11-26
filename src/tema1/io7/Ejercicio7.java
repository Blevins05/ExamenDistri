package tema1.io7;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe la ruta del directorio a listar");
		String path = sc.nextLine();
		
		File dir = new File(path);
		for (File f: dir.listFiles()) {
			if (f.isDirectory()) {
				System.out.println(f.getName() + "<DIR>");
			} else {
				System.out.println(f.getName() + ", " +  f.length() + ", " + new Date(f.lastModified()));
			}

		}

	}

	
}