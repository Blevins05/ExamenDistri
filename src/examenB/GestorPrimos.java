package examenB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class GestorPrimos implements Callable<ArrayList<Integer>> {
	private int num;
	private int hilos;
	private int[] encontradosPorHilo;
	public GestorPrimos(int n, int h) {
		this.num = n;
		this.hilos = h;
		encontradosPorHilo = new int[hilos];
	}
	
	public GestorPrimos(int numeroRsa) {
		this.num = numeroRsa;
	}
	
	public boolean esPrimo(int n) {
	    if (n < 2) return false;
	    if (n == 2) return true;
	    if (n % 2 == 0) return false;

	    for (int div = 3; div * div <= n; div += 2) {
	        if (n % div == 0) return false;
	    }

	    return true;
	}


	@Override
	public ArrayList<Integer> call() throws Exception {
	    ExecutorService pool = Executors.newFixedThreadPool(hilos);

	    List<Integer> primos = new ArrayList<>();
	    AtomicInteger candidato = new AtomicInteger(2);

	    ArrayList<Callable<Void>> tareas = new ArrayList<>();

	    for (int i = 0; i < hilos; i++) {
	    	
	    	final int hiloID = i;
	        tareas.add(() -> {
	            while (true) {
	                // Si ya tenemos N, este hilo termina
	                synchronized (primos) {
	                    if (primos.size() >= this.num)
	                        break;
	                }

	                int num = candidato.getAndIncrement();

	                if (esPrimo(num)) {
	                    synchronized (primos) {
	                        if (primos.size() < this.num) {
	                            primos.add(num);
	                            this.encontradosPorHilo[hiloID]++;
	                        }
	                    }
	                }
	            }
	            return null;
	        });
	    }

	    pool.invokeAll(tareas);
	    pool.shutdown();

	    // Convertimos a ArrayList para el return
	    return new ArrayList<>(primos);
	}

	public int[] getEncontradosPorHilo() {
		// TODO Auto-generated method stub
		return this.encontradosPorHilo;
	}

		
}
