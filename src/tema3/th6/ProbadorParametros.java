package tema3.th6;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import tema3.th5.WebClient;

public class ProbadorParametros implements Callable<ArrayList<Long>> {

	private int repeticiones;
	private final String ruta;
	private CyclicBarrier cb;
	private CountDownLatch cdl;
	public ProbadorParametros(int r, CyclicBarrier cb, CountDownLatch cdl) {
		this.repeticiones = r;
		ruta = "LICENSE";
		this.cb = cb;
		this.cdl = cdl;
	}
	

	@Override
	public ArrayList<Long> call() throws Exception {
		// TODO Auto-generated method stub
		cb.await(); // espera (asi cuando todos hayan llamado al await, comenzaran a la vez)
		ArrayList<Long> tiempos = new ArrayList();
		WebClient wc = new WebClient("localhost", 8080);
		for (int i = 0; i < repeticiones; i++) {
			long inicio = System.currentTimeMillis();
			wc.descargaSinGuardar();
			long fin = System.currentTimeMillis();
			long res = fin - inicio;
			tiempos.add(res);	
		}
		cdl.countDown(); // termino su tarea el hilo trabajador
		return tiempos;
	}

}
