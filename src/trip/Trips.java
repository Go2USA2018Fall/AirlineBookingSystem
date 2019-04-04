package trip;

import java.util.ArrayList;

import flight.Flights;

public class Trips extends ArrayList<Trip> {
	private static final long serialVersionUID = 1L;
	
	public void calculatePrice() {
		for (Trip trip : this) {
			trip.calculatePrice();
		}
	}
	
	public void print() {
		for (Trip trip: this) {
			trip.print();
		}
	}
	
}
