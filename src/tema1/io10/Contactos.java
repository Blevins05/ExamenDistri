package tema1.io10;

import java.io.Serializable;
// modificar la clase para que sea posible serializar objetos de la clase en almacenamiento o envio a traves de red
import java.util.*;
public class Contactos implements Serializable {
	private static final long serialVersionUID = 1L;
	public static class Datos implements Serializable {
		private static final long serialVersionUID = 1L;
		private String email;
		private String tfno;
		public Datos(String email, String telefono) {
			this.email = email;
			this.tfno = telefono;
		}
		public String getTfno() {
			return tfno;
		}
		public String getEmail() {
			return email;
		}
	}

	private HashMap<String, Datos> agenda = new HashMap<String, Datos>();
	public int TamañoMaximoAgenda = 1000;
	public void addDatos(String nombre, String tfno, String email) {
		agenda.put(nombre, new Datos(email, tfno));
	}
	public String getTfno(String nombre) {
		return agenda.get(nombre).getTfno();
	}
	public String getEmail(String nombre) {
		return agenda.get(nombre).getEmail();
	}
	
	public Integer tamano() { // metodo añadido punto d.
		return agenda.size();
	}
}