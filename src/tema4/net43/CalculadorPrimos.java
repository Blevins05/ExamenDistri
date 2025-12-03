package tema4.net43;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CalculadorPrimos {
	public static boolean esPrimoParalelo(int n) {
	    if (n <= 1) return false;
	    if (n == 2) return true;
	    if (n % 2 == 0) return false; // pares > 2 no son primos

	    int cores = Runtime.getRuntime().availableProcessors();
	    ExecutorService pool = Executors.newFixedThreadPool(cores);
	    int maximo = (int) Math.sqrt(n);

	    int rango = (maximo - 3 + 1) / cores; // rango exacto entre 3 y max
	    ArrayList<Future<Boolean>> resultados = new ArrayList<>();

	    for (int i = 0; i < cores; i++) {
	        int inicio = 3 + i * rango;
	        int fin = (i == cores - 1) ? maximo : inicio + rango - 1; // -1 para no solapar
	        int iniFinal = inicio;
	        int finFinal = fin;

	        resultados.add(pool.submit(() -> {
	            for (int d = iniFinal; d <= finFinal; d += 2) {
	                if (n % d == 0) return true; // encontrado divisor
	            }
	            return false;
	        }));
	    }

	    pool.shutdown();

	    for (Future<Boolean> f : resultados) {
	        try {
	            if (f.get()) return false; // si algún hilo encontró divisor -> no primo
	        } catch (InterruptedException | ExecutionException e) {
	            e.printStackTrace();
	        }
	    }

	    return true; // ningún hilo encontró divisor -> es primo
	}
	
}

