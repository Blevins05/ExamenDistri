package tema3.th3;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newCachedThreadPool();
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el nombre del directorio");
		String dir = sc.nextLine();
		ArrayList<Callable<Integer>> cc = new ArrayList<>();
		File directorio = new File(dir);
		File[] ficheros = directorio.listFiles();
		int total = 0;
		for(File f : ficheros) {
			cc.add(new Buscador(f.getName()));
		}
		
		// invokeAll VS submit()
		try {
			List<Future<Integer>> fc = pool.invokeAll(cc);
			
			for (Future<Integer> f: fc) {
				System.out.println(f.get() + " lineas tiene este fichero");
				total += f.get();
			}
			
			// si se hiciera con submit () seria asi:
			/* 
 			for (Callable<Integer> c : cc) {
 			Future<Integer> f = pool.submit(c);
 			total += f.get();
 			} 
 			
 			o tambien se podria hacer asi:
 			
 			Crear una lista de futures en vez de una de callables:
 			
 			for (File f : ficheros) {
 				listaFutures.add(pool.submit(new Buscador(f.getName));
 			}
 			
 			for (Future<Integer> f : listaFutures) {
 				total += f.get();
 			}
 			
 			*/
			
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
