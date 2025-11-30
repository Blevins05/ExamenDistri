package tema3.th4;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimerTask;

public class ComprobadorDirectorio extends TimerTask {
	private final File directorio;
	private long ultimaFecha;
	
	public ComprobadorDirectorio(String dir) {
		this.directorio = new File(dir);
		this.ultimaFecha = directorio.lastModified();
	}
	
	 private void listarContenido() {
			File[] contenido = directorio.listFiles();
			for(File f : contenido) {
				System.out.println(f.getName() + ", " + f.length() + " : " 
			+ new Date(f.lastModified()));
			}
	    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		  long nuevaFecha = directorio.lastModified();
		  
		  if (nuevaFecha != ultimaFecha) {
			  System.out.println("El directorio ha sido modificado");
			  listarContenido();
			  ultimaFecha = nuevaFecha;
		  } else {
			  System.out.println("Sin cambios en el directorio");
		  }

	}

}
