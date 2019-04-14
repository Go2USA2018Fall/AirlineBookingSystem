package trip;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import flight.Flight;
import flight.Flights;

public class Trip {

	private Flights oneWayFlights;
	private String seatClass;
	private float price = 0;
	
	public Trip(Flights flights, String seatClass) {
		this.oneWayFlights = flights;
		this.seatClass = seatClass;
	}
	
	public void calculatePrice() {
		for (Flight flight: this.oneWayFlights) {
			if (this.seatClass == "coach")
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
	
	public ZonedDateTime getDepartureDateTime() {
		return this.oneWayFlights.get(0).departureDate();
	}
	
	public ZonedDateTime getArrivalDateTime() {
		int size = this.oneWayFlights.size();
		return this.oneWayFlights.get(size-1).arrivalDate();
	}
	
	public long duration() {
		ZonedDateTime departure = this.getDepartureDateTime();
		ZonedDateTime arrival = this.getArrivalDateTime();
		return ChronoUnit.HOURS.between(departure,  arrival);
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public boolean confirm() {
		return true;
	}
}
