package dao;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

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
	
	/**
	 * Builds a collection of Flights from flights described in XML
	 * 
	 * Parses an XML string to read each of the flights and adds each valid flight 
	 * to the collection. The method uses Java DOM (Document Object Model) to convert
	 * from XML to Java primitives. 
	 * 
	 * Method iterates over the set of Flight nodes in the XML string and builds
	 * an Flight object from the XML node string and add the Flight object instance to
	 * the Flights collection.
	 * 
	 * @param xmlFlights XML string containing set of Flights 
	 * @return [possibly empty] collection of Flights in the xml string
	 * @throws Exception 
	 * 
	 * @pre the xmlFlights string adheres to the format specified by the server API
	 * @post the [possibly empty] set of Flights in the XML string are added to collection
	 */

	public static Flights addAll (String xmlFlights, Map<String, Airplane> airplaneData, Map<String, Airport> airportData ) throws Exception {
		Flights flights = new Flights();
		
		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each airport to our collection
		Document docFlights = buildDomDoc (xmlFlights);
		NodeList nodesFlights = docFlights.getElementsByTagName("Flight");
		
		for (int i = 0; i < nodesFlights.getLength(); i++) {
			Element elementFlight = (Element) nodesFlights.item(i);
			Flight flight = buildFlight (elementFlight, airplaneData, airportData);
			
			if (flight.isValid()) {
				flights.add(flight);
			}
		}
		
		return flights;
	}
	
	/**
	 * Creates an Flight object from a DOM node
	 * 
	 * Processes a DOM Node that describes an Airport and creates an Flight object from the information
	 * @param nodeFlight is a DOM Node describing an Flight
	 * @return Flight object created from the DOM Node representation of the Flight
	 * @throws Exception 
	 * 
	 * @pre nodeFlight is of format specified by CS509 server API
	 * @post flight object instantiated. Caller responsible for deallocating memory.
	 */
	static private Flight buildFlight (Node nodeFlight, Map<String, Airplane> airplaneData,  Map<String, Airport> airportData) throws Exception {
		String airplane_id;
		String flight_duration;
		String number;
		
		// The airport element has attributes of Name and 3 character airport code
		Element elementFlight = (Element) nodeFlight;
		airplane_id = elementFlight.getAttributeNode("Airplane").getValue();
		Airplane airplane = airplaneData.get(airplane_id);
		flight_duration = elementFlight.getAttributeNode("FlightTime").getValue();
		number = elementFlight.getAttributeNode("Number").getValue();
		
		// The latitude and longitude are child elements
		Element arrival_element = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
		Element arrival_code = (Element)arrival_element.getElementsByTagName("Code").item(0);
		String code_value = getCharacterDataFromElement(arrival_code);
		Airport arrival = airportData.get(code_value);
//		arrival.code(code_value);
		
		Element departure_element = (Element)elementFlight.getElementsByTagName("Departure").item(0);
		Element departure_code = (Element)departure_element.getElementsByTagName("Code").item(0);
		String code_value1 = getCharacterDataFromElement(departure_code);
		Airport departure = airportData.get(code_value1);
//		departure.code(code_value1);
		
		Element arrival_time = (Element)arrival_element.getElementsByTagName("Time").item(0);
		String arrival_value = getCharacterDataFromElement(arrival_time);
		//arrival_value = arrival_value.split("\\w+ \\w+ \\d+ ")[1];
		
		Element departure_time = (Element)departure_element.getElementsByTagName("Time").item(0);
		String departure_value = getCharacterDataFromElement(departure_time);
		//departure_value = departure_value.split("\\w+ \\w+ \\d+ ")[1];
		
		Element seating = (Element)elementFlight.getElementsByTagName("Seating").item(0);
		
		Element firstClass = (Element)seating.getElementsByTagName("FirstClass").item(0);
		String fcPrice = firstClass.getAttributeNode("Price").getValue();
		fcPrice = fcPrice.replace("$", "");
		fcPrice = fcPrice.replace(",", "");
		float firstClassPrice = Float.parseFloat(fcPrice);
		int firstClassCapacity = Integer.parseInt(getCharacterDataFromElement(firstClass));
		
		Element coachClass = (Element)seating.getElementsByTagName("Coach").item(0);
		int coachClassCapacity = Integer.parseInt(getCharacterDataFromElement(coachClass));
		String ccPrice = coachClass.getAttributeNode("Price").getValue();
		ccPrice = ccPrice.replace("$", "");
		ccPrice = ccPrice.replace(",", "");
		float coachClassPrice = Float.parseFloat(ccPrice);
		//TODO: need to call airplane dao here to create airplane object
		
		Flight flight = new Flight(number, flight_duration, departure, arrival, airplane, firstClassPrice, coachClassPrice,
				departure_value, arrival_value, coachClassCapacity, firstClassCapacity);
		
		
		return flight;
		
	}	
	
	/**
	 * Builds a DOM tree from an XML string
	 * 
	 * Parses the XML file and returns a DOM tree that can be processed
	 * 
	 * @param xmlString XML String containing set of objects
	 * @return DOM tree from parsed XML or null if exception is caught
	 * @pre successfully got xml file
	 * @post parsed xml file to document data type
	 */
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
