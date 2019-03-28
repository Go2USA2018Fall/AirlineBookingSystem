package flight;

import java.util.ArrayList;

import airport.Airport;
/**
 * This class aggregates a number of Airport. The aggregate is implemented as an ArrayList.
 * Airports can be added to the aggregate using the ArrayList interface. Objects can 
 * be removed from the collection using the ArrayList interface.
 * 
 * @author Michiele
 *
 */
public class Flights extends ArrayList<Flight>{
	private static final long serialVersionUID = 1L;
	
	public void print() {
		for (Flight f: this) {
			f.print();
		}
	}
}
