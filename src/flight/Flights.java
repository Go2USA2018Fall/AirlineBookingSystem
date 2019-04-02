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
	
	public Flights(Flights f) {
		super(f);
	}
	
	public Flights() {
		super();
	}
	
	public void print() {
		String flightString = "";
		int size = this.size();
		for (int i = 0; i < size; i++) {
			Flight flight = this.get(i);
			if (i == size-1)
				flightString += flight.toString(false);
			else 
				flightString += flight.toString(false) + " |||| ";
		}
		
		System.out.println(flightString);
	}
}
