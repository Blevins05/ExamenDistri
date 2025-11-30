package tema3.th4;

import java.io.File;
import java.util.Scanner;
import java.util.Timer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		ComprobadorDirectorio cd = new ComprobadorDirectorio(sc.nextLine());
		
		Timer t = new Timer(); // el timer "ejecuta" tareas de tipo timerTask
		
		t.scheduleAtFixedRate(cd, 0, 10000); // en ms: 10 segundos, entonces cada 10000. 
		
		// diferencia entre fixedRate y no fixedRate:
		
		/* 
		 programar la ejecucion de una tarea con un metodo fixedRate, 
		 hace que no tenga en cuenta los retrasos de ejecuciones de tareas previas, es decir
		 si la tarea dura 3 minutos, y se programa para su ejecucion cada 5, entonces
		 realmente se ejecuta cada 2 minutos (hay 2 minutos entre que termina la ejecucion de una tarea
		 y el comienzo de la siguiente)
		 
		 si no es fixedRate, tiene en cuenta los retrasos de anteriores tareas.
		 
		 NOTA: scheduleAtFixedRate programa según el reloj, 
		 por lo que si la ejecución tarda más que el periodo, 
		 se solapan tareas. schedule no: ejecuta secuencialmente sin solapamientos.
		 
		 */
	}

}
