package tema3.th2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Buscador extends Thread{

	private String nomFichero;
	private int lineasTotales;
	
	public Buscador(String s) {
		// TODO Auto-generated constructor stub
		this.nomFichero = s;
		this.lineasTotales = 0;
	}

	public int getLineas() {
		return this.lineasTotales;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try(FileInputStream fis = new FileInputStream(nomFichero);
				DataInputStream dis = new DataInputStream(fis)) {
			
			while (dis.readLine() != null) {
				lineasTotales++;
			}
			System.out.println("Lineas totales de este fichero: " + lineasTotales);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
