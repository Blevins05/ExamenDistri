package tema3.th2;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Buscador b1 = new Buscador("LICENSE");
		Buscador b2 = new Buscador("LICENSE");
		Buscador b3 = new Buscador("LICENSE");
		int suma = 0;
		long init = System.currentTimeMillis();
		b1.start();
		b2.start();
		b3.start();
		
		try {
			b1.join();
			b2.join();;
			b3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long fin = System.currentTimeMillis();
		
		System.out.println((fin-init) + "ms");
		System.out.println(b1.getLineas());
		System.out.println(b2.getLineas());
		System.out.println(b3.getLineas());
		
		System.out.println("Tiempo con secuencial: 1352ms");
		System.out.println("Tiempo con concurrencia: 602ms");
	} 
		
}


