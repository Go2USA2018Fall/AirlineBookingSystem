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
	/**
	 * print trip in to console
	 * 
	 * @pre have a valid list of trips
	 * @post print list of trips to console with index
	 */
	public void print() {
		for (Trip trip: this) {
			String index = String.valueOf(this.indexOf(trip));
			System.out.println(index + ") " + trip.toString());
		}
	}
	
}
