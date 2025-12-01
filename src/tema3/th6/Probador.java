package tema3.th6;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import tema3.th5.WebClient;

public class Probador implements Callable<ArrayList<Long>> {

	private int repeticiones;
	private final String ruta;
	
	public Probador(int r) {
		this.repeticiones = r;
		ruta = "LICENSE";
	}

	@Override
	public ArrayList<Long> call() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Long> tiempos = new ArrayList();
		WebClient wc = new WebClient("localhost", 8080);
		for (int i = 0; i < repeticiones; i++) {
			long inicio = System.currentTimeMillis();
			wc.descargaSinGuardar();
			long fin = System.currentTimeMillis();
			long res = fin - inicio;
			tiempos.add(res);
			
		}
		
		return tiempos;
	}

}
