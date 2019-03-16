package flight;

import airport.Airport;
import airplane.Airplane;

public class Flight {
	
	private String number;
	private Airport departure;
	private Airport arrival;
	private Airplane aplane;
	private String departureTime;
	private String arrivalTime;
	private double firstClassPrice;
	private double coachClassPrice;
	private int firstClassCapacity;
	private int coachClassCapacity;
	
	public Flight(String number, Airport departure, Airport arrival, Airplane aplane, double firstClassPrice,
			      double coachClassPrice, String departureTime, String arrivalTime, int coachClassCapacity,
			      int firstClassCapacity) {
		this.departure = departure;
		this.arrival = arrival;
		this.aplane = aplane;
		this.firstClassPrice = firstClassPrice;
		this.coachClassPrice = coachClassPrice;
		this.number = number;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.firstClassCapacity = firstClassCapacity;
		this.coachClassCapacity = coachClassCapacity;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public void print() {
		String print_str = number+" :: "+departure.code() +" "+ departureTime+" ===> "+arrival.code()+" "+arrivalTime
			+" :: First Class reserved:"+ firstClassCapacity+" First Class Price: $"+ firstClassPrice+" "+" :: Coach Class reserved:"+coachClassCapacity+" Coach Class Price: $"+ coachClassPrice;
		System.out.println(print_str);
	}
}
