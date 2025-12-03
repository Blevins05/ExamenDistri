package tema5.xml123;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
	private String nombre;
	private float precio;
	private boolean oferta;
	private List<String> ingredientes;
	
	public Pizza(String nombre, float precio) {
	this.nombre = nombre;
	this.precio = precio;
	this.ingredientes = new ArrayList<String>();
	}
	
	public String getNombre() {return nombre;}
	
	public float getPrecio() {return precio;}
	
	public boolean isOferta() {return oferta;}
	
	public void setOferta(boolean oferta) {this.oferta = oferta;}
	public void addIngrediente(String ingrediente) {
		ingredientes.add(ingrediente);
	}
	public List<String> getIngredientes() {return ingredientes;}
	
	public String toString() {
	return "Pizza "+nombre+" "+ingredientes+" cuesta "+precio+"."+(oferta?" En oferta.":"");
	
	}

}