package trip;

import java.util.ArrayList;

import flight.Flights;

public class Trips extends ArrayList<Flights> {
	private static final long serialVersionUID = 1L;
	
	public void print() {
		for (Flights trip: this) {
			trip.print();
		}
	}
	
}
