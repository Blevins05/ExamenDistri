package tema1.io10;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Deserializar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            Contactos c = new Contactos();
    

            ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream("contactos.ser"));

            c = (Contactos) ois.readObject();
            ois.close();
            
            System.out.println("Tfno de Juan = " + c.getTfno("Juan"));
            System.out.println("Email de Ana = " + c.getEmail("Ana"));
            System.out.println("Tamño de la agenda: " + c.tamano()); // la gracia es que funciona porque el serialVersionUID es el mismo.
            System.out.println("Tamaño maximo agenda: " + c.TamañoMaximoAgenda);
            System.out.println("Objeto deserializado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
