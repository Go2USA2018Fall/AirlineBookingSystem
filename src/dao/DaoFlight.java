package dao;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import airplane.Airplane;
import airport.Airport;
import airport.Airports;
import flight.Flight;
import flight.Flights;

public class DaoFlight {

	public static Flights addAll (String xmlFlights) throws NullPointerException {
		Flights flights = new Flights();
		
		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each airport to our collection
		Document docFlights = buildDomDoc (xmlFlights);
		NodeList nodesFlights = docFlights.getElementsByTagName("Flight");
		
		for (int i = 0; i < nodesFlights.getLength(); i++) {
			Element elementFlight = (Element) nodesFlights.item(i);
			Flight flight = buildFlight (elementFlight);
			
			if (flight.isValid()) {
				flights.add(flight);
			}
		}
		
		return flights;
	}
	
	static private Flight buildFlight (Node nodeFlight) {
		String airplane_id;
		String flight_duration;
		String number;
		
		// The airport element has attributes of Name and 3 character airport code
		Element elementFlight = (Element) nodeFlight;
		airplane_id = elementFlight.getAttributeNode("Airplane").getValue();
		flight_duration = elementFlight.getAttributeNode("FlightTime").getValue();
		number = elementFlight.getAttributeNode("Number").getValue();
		
		// The latitude and longitude are child elements
		Element arrival_element = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
		Element arrival_code = (Element)arrival_element.getElementsByTagName("Code").item(0);
		String code_value = getCharacterDataFromElement(arrival_code);
		Airport arrival = new Airport();
		arrival.code(code_value);
		
		Element departure_element = (Element)elementFlight.getElementsByTagName("Departure").item(0);
		Element departure_code = (Element)departure_element.getElementsByTagName("Code").item(0);
		String code_value1 = getCharacterDataFromElement(departure_code);
		Airport departure = new Airport();
		departure.code(code_value1);
		
		Element arrival_time = (Element)arrival_element.getElementsByTagName("Time").item(0);
		String arrival_value = getCharacterDataFromElement(arrival_time);
		
		Element departure_time = (Element)departure_element.getElementsByTagName("Time").item(0);
		String departure_value = getCharacterDataFromElement(departure_time);
		
		Element seating = (Element)elementFlight.getElementsByTagName("Seating").item(0);
		
		Element firstClass = (Element)seating.getElementsByTagName("FirstClass").item(0);
		String fcPrice = firstClass.getAttributeNode("Price").getValue();
		fcPrice = fcPrice.replace("$", "");
		fcPrice = fcPrice.replace(",", "");
		double firstClassPrice = Double.parseDouble(fcPrice);
		int firstClassCapacity = Integer.parseInt(getCharacterDataFromElement(firstClass));
		
		Element coachClass = (Element)seating.getElementsByTagName("Coach").item(0);
		int coachClassCapacity = Integer.parseInt(getCharacterDataFromElement(coachClass));
		String ccPrice = coachClass.getAttributeNode("Price").getValue();
		ccPrice = ccPrice.replace("$", "");
		double coachClassPrice = Double.parseDouble(ccPrice);
		
		Airplane airplane = new Airplane(firstClassCapacity, coachClassCapacity);
		
		Flight flight = new Flight(departure, arrival, airplane, firstClassPrice, coachClassPrice);
		
		System.out.println(airplane_id+" :: "+code_value1 +" => "+ code_value);
		return flight;
		
	}	
	
	static private Document buildDomDoc (String xmlString) {
		/**
		 * load the xml string into a DOM document and return the Document
		 */
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlString));
			
			return docBuilder.parse(inputSource);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getCharacterDataFromElement (Element e) {
		Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	        CharacterData cd = (CharacterData) child;
	        return cd.getData();
	      }
	      return "";
	}
}
