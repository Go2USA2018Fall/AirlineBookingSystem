package trip;

import flight.Flight;
import flight.Flights;

public class Trip {

	private Flights oneWayFlights;
	private Flights roundTripFlights;
	private String seatClass;
	private float price = 0;
	
	public Trip(Flights flights, String seatClass) {
		this.oneWayFlights = flights;
		this.seatClass = seatClass;
	}
	
	public void calculatePrice() {
		for (Flight flight: this.oneWayFlights) {
			if (this.seatClass == "economy")
				this.price += flight.getCoachClassPrice();
			else if(this.seatClass == "firstclass")
				this.price += flight.getFirstClassPrice();
		}
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	public String toString() {
		return this.oneWayFlights.toString() + " Price: $" + String.valueOf(this.price);
	}
	
	public Flights tripFlights() {
		return this.oneWayFlights;
	}
}
