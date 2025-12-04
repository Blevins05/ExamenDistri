package examenB;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GestorPrimos implements Callable<ArrayList<Integer>> {
	private int num;
	private int hilos;
	
	public GestorPrimos(int n, int h) {
		this.num = n;
		this.hilos = h;
	}
	
	public GestorPrimos(int numeroRsa) {
		this.num = numeroRsa;
	}
	
	public boolean esPrimo(int n) {
		if (n == 1) return false;
		if (n == 2) return true;
		
		for (int div = 3; div < Math.sqrt(n); div++) {
			if (n % div == 0) return false;
		}
		return true;
	}

	@Override
	public ArrayList<Integer> call() throws Exception {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(hilos);
		 ArrayList<Future<ArrayList<Integer>>> resultadosFuture = new ArrayList<>();
		int parte = num / hilos; // el num de primos que le toca calcular a cada uno
		int inicio = 1;
		int rango = (num + hilos - 1) / hilos;
		
		for(int i = 0; i < hilos; i++) {
			int ini = inicio;
			int fin = Math.min(ini+rango-1, num);
			
			
		 resultadosFuture.add(pool.submit(() -> {
			 ArrayList<Integer> primosParciales = new ArrayList<>();
	            for (int j = inicio; j <= fin; j++) {
	            	if (esPrimo(j)) {
	            		primosParciales.add(j);
	            	}
	            }
	            return primosParciales;
	       }));
		}
		ArrayList<Integer> listaPrimos = new ArrayList<>();
		for (Future<ArrayList<Integer>> futureListaPrimos : resultadosFuture) {
			ArrayList<Integer> ls = futureListaPrimos.get();
			listaPrimos.addAll(ls);
		}
		
		return listaPrimos;
	
	}
		
}
