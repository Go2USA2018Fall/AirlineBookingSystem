package dao;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import flight.Flight;
import flight.Flights;
import trip.Trip;
import trip.Trips;

public class DaoReservation {
	
	public static String buildXML(Trips selectedTrips){
		String result_XML = null;
		 try {
	         DocumentBuilderFactory dbFactory =
	         DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.newDocument();
	         
	         // root element
	         Element rootElement = doc.createElement("Flights");
	         doc.appendChild(rootElement);

	         // flight element
	         for (Trip trip : selectedTrips){
	        	 Flights flights =  trip.tripFlights();
	        	 for(Flight f: flights){
		        	 Element flight = doc.createElement("Flight");
			         flight.setAttribute("number",f.getNumber() );
			         flight.setAttribute("seating",trip.getSeatClass());
			         rootElement.appendChild(flight); 
	        	 }
	         }	         
	         DOMSource domSource = new DOMSource(doc);
	         Transformer transformer = TransformerFactory.newInstance().newTransformer();
	         StringWriter sw = new StringWriter();
	         StreamResult sr = new StreamResult(sw);
	         transformer.transform(domSource, sr);
	         result_XML = sw.toString();

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		
		return result_XML;
		
	}

}
