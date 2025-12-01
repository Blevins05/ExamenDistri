package tema3.th6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebStress {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el numero de hilos");
		int N = Integer.parseInt(sc.nextLine());
		System.out.println("Introduce el numero de repeticiones por hilo");
		int M = Integer.parseInt(sc.nextLine());
		System.out.println("Introduce la forma en la que quieres testear el programa (1-2)");
		int forma = Integer.parseInt(sc.nextLine());
		
		if (forma == 1) {
			pruebaRendimiento1(N, M);
		} else if (forma == 2) {
			pruebaRendimiento2(N, M);
		} else if (forma == 3) {
			pruebaRendimiento2PasandoParametros(N, M);
		}
	}
	
	
	public static void pruebaRendimiento1(int N, int M) {
		ExecutorService pool = Executors.newFixedThreadPool(N); // pool de N hilos
		
		ArrayList<Callable<ArrayList<Long>>> cl = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			cl.add(new Probador(M));
		}
		// todos los callables añadidos al ArrayList
		
		// obtenemos resultados con invokeAll
		try {
			List<Future<ArrayList<Long>>> tiemposFutures = pool.invokeAll(cl);
			ArrayList<Long> tiempos = new ArrayList<>();
			
			for (Future<ArrayList<Long>> listFutures : tiemposFutures) {
				ArrayList<Long> ls = listFutures.get();
				tiempos.addAll(ls);
			}
			
			long min = tiempos.stream().mapToLong(Long::longValue).min().orElse(0);
			long max = tiempos.stream().mapToLong(Long::longValue).max().orElse(0);
			double media = tiempos.stream().mapToLong(Long::longValue).average().orElse(0);
			
			System.out.println("Tiempo de descarga mas rapido de todas las repeticiones de los hilos : "  + min + "ms");
			System.out.println("Tiempo de descarga mas lento de todas las repeticiones de los hilos : "  + max + "ms");
			System.out.println("Tiempo de descarga promedio de todas las repeticiones de los hilos : " + media + "ms");
			
	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void pruebaRendimiento2(int N, int M) {
		ExecutorService pool = Executors.newFixedThreadPool(N); // pool de N hilos (empezaran todos a la vez)
		CyclicBarrier cb = new CyclicBarrier(N+1);
		CountDownLatch cdl = new CountDownLatch(N);
		List<Future<ArrayList<Long>>> resultadosFuture = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			resultadosFuture.add(pool.submit(() -> {
				cb.await(); // hacemos que los hilos trabajadores esperen
				ArrayList<Long> listaTiempos = new Probador(M).call();
				cdl.countDown(); // cuando un hilo trabajador acaba, lo indica reduciendo el contador en 1
				return listaTiempos;		
			}));
		}
		
		
		
		
		try {
			cb.await();  // el principal se bloquea (da la señal de salida) y empiezan todos a la vez
			cdl.await(); // espera a que todos terminen la tarea (el contador esta en 0 y se puede seguir)
			
			ArrayList<Long> tiemposFinales = new ArrayList<>();
			
			for (Future<ArrayList<Long>> fl : resultadosFuture) {
				ArrayList<Long> ls = fl.get();
				tiemposFinales.addAll(ls);
			}
			
			long min = tiemposFinales.stream().mapToLong(Long::longValue).min().orElse(0);
			long max = tiemposFinales.stream().mapToLong(Long::longValue).max().orElse(0);
			double media = tiemposFinales.stream().mapToLong(Long::longValue).average().orElse(0);
			
			System.out.println("Tiempo de descarga mas rapido de todas las repeticiones de los hilos : "  + min + "ms");
			System.out.println("Tiempo de descarga mas lento de todas las repeticiones de los hilos : "  + max + "ms");
			System.out.println("Tiempo de descarga promedio de todas las repeticiones de los hilos : " + media + "ms");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void pruebaRendimiento2PasandoParametros(int N, int M) {
		ExecutorService pool = Executors.newFixedThreadPool(N); // pool de N hilos (empezaran todos a la vez)
		CyclicBarrier cb = new CyclicBarrier(N+1);
		CountDownLatch cdl = new CountDownLatch(N);
		List<Future<ArrayList<Long>>> resultadosFuture = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			try {
				Future<ArrayList<Long>> tiempos = pool.submit(new ProbadorParametros(M, cb, cdl));
				resultadosFuture.add(tiempos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			cb.await();  // el principal se bloquea (da la señal de salida) y empiezan todos a la vez
			cdl.await(); // espera a que todos terminen la tarea (el contador esta en 0 y se puede seguir)
			
			ArrayList<Long> tiemposFinales = new ArrayList<>();
			
			for (Future<ArrayList<Long>> fl : resultadosFuture) {
				ArrayList<Long> ls = fl.get();
				tiemposFinales.addAll(ls);
			}
			
			long min = tiemposFinales.stream().mapToLong(Long::longValue).min().orElse(0);
			long max = tiemposFinales.stream().mapToLong(Long::longValue).max().orElse(0);
			double media = tiemposFinales.stream().mapToLong(Long::longValue).average().orElse(0);
			
			System.out.println("Tiempo de descarga mas rapido de todas las repeticiones de los hilos : "  + min + "ms");
			System.out.println("Tiempo de descarga mas lento de todas las repeticiones de los hilos : "  + max + "ms");
			System.out.println("Tiempo de descarga promedio de todas las repeticiones de los hilos : " + media + "ms");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}















