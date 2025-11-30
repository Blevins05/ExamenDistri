package tema3.th3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class Buscador implements Callable<Integer>{
	private String nomFichero;
	private Integer lineasTotales;
	
	public Buscador(String s) {
		// TODO Auto-generated constructor stub
		this.nomFichero = s;
		this.lineasTotales = 0;
	}

	@Override
	public Integer call() throws Exception {
		try(FileInputStream fis = new FileInputStream(nomFichero);
				DataInputStream dis = new DataInputStream(fis)) { // para solo texto, es mejor usar bfr
			
			while (dis.readLine() != null) {
				lineasTotales++;
			}
			System.out.println("Lineas totales de este fichero: " + lineasTotales);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return lineasTotales;
	}
}
