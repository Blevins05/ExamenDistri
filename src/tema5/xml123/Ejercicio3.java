package tema5.xml123;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Ejercicio3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Pizza> pizzas = new ArrayList<>();
		
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File("C:\\URApps\\eclipse-workspace\\Practica5\\src\\xml1\\PizzasXML.xml"));
			Element root = document.getDocumentElement();
			
			NodeList pizzaNL = root.getElementsByTagName("pizza");
			
			for (int i = 0; i < pizzaNL.getLength(); i++ ) {
			    Element pizza = (Element) pizzaNL.item(i);
			    String nombre = pizza.getAttribute("nombre");
			    Float precio = Float.parseFloat(pizza.getAttribute("precio"));
			    Pizza p = new Pizza(nombre, precio);
			    
			    NodeList ingredientes = pizza.getElementsByTagName("ingrediente");
			    for (int j = 0; j < ingredientes.getLength(); j++) {
			        p.addIngrediente(ingredientes.item(j).getTextContent());
			    }

			    NodeList ofertaNL = pizza.getElementsByTagName("oferta");
			    if (ofertaNL.getLength() > 0) {
			        p.setOferta(true);
			    } else {
			        p.setOferta(false);
			    }
		

			    pizzas.add(p);
			}
			
			Element nuevaPizza = document.createElement("pizza");
			nuevaPizza.setAttribute("nombre", "Pecado carnal");
			nuevaPizza.setAttribute("precio", "10.99");
			Element carne = document.createElement("ingrediente");
			Element barbacoa = document.createElement("ingrediente");
			carne.appendChild(document.createTextNode("carne"));
			barbacoa.appendChild(document.createTextNode("salsa bbq"));
			nuevaPizza.appendChild(carne);
			nuevaPizza.appendChild(barbacoa);
			
			root.appendChild(nuevaPizza);
			
			for (Pizza pizza : pizzas) {
                System.out.println(pizza.toString());
            }
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("pizzasActualizado.xml"));
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
