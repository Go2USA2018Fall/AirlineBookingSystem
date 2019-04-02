package trip;

import java.util.ArrayList;

import flight.Flights;

public class Trips extends ArrayList<Trip> {
	private static final long serialVersionUID = 1L;
	
	public void print() {
		for (Trip trip: this) {
			trip.print();
		}
	}
	
}
