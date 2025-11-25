package tema1.io3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ejercicio3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (DataInputStream dis = new DataInputStream(new FileInputStream("wqdfqw"));
				DataOutputStream dos = new DataOutputStream(new FileOutputStream("njqwbdqw"))) {
			
			
			String linea;
			
			while ((linea = dis.readLine()) != null) {
				dos.writeBytes(linea + "\n");
			}
			dos.flush();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main2() {
		try (BufferedReader bfr= new BufferedReader(new InputStreamReader(new FileInputStream("wqdfqw")));
				BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("njqwbdqw")))) {
			
			
			String linea;
			
			while ((linea = bfr.readLine()) != null) {
				bfw.write(linea + "\n");
			}
			bfw.flush();

	} catch  (IOException ex) {
		ex.printStackTrace();
	}

}

	
}