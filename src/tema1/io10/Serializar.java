package tema1.io10;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Serializar {
    public static void main(String[] args) {
        try {
            Contactos c = new Contactos();
            c.addDatos("Juan", "111111111", "juan@email.com");
            c.addDatos("Ana", "222222222", "ana@email.com");

            ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream("contactos.ser"));

            oos.writeObject(c);
            oos.close();

            System.out.println("Objeto serializado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
