package flight;

import airport.Airport;
import airplane.Airplane;

public class Flight {
	
	private Airport departure;
	private Airport arrival;
	private Airplane aplane;
	private double firstClassPrice;
	private double coachClassPrice;
	
	public Flight(Airport departure, Airport arrival, Airplane aplane, double firstClassPrice, double coachClassPrice) {
		this.departure = departure;
		this.arrival = arrival;
		this.aplane = aplane;
		this.firstClassPrice = firstClassPrice;
		this.coachClassPrice = coachClassPrice;
	}
	
	public boolean isValid() {
		return true;
	}
}
