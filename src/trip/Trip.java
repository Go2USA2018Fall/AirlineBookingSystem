package trip;

import flight.Flights;

public class Trip {

	private Flights flights;
	
	public Trip(Flights flights) {
		this.flights = flights;
	}
	
	public void print() {
		this.flights.print();
	}
}
