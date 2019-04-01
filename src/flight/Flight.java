package flight;

import airport.Airport;
import airplane.Airplane;

public class Flight {
	
	private String number;
	private String flightDuration;
	private Airport departure;
	private Airport arrival;
	private Airplane aplane;
	private String departureTime;
	private String arrivalTime;
	private double firstClassPrice;
	private double coachClassPrice;
	private int firstClassCapacity;
	private int coachClassCapacity;
	/**
	 * Default constructor
	 * 
	 * Constructor with params. Requires object fields to be explicitly
	 * set using setter methods
	 * 
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */	
	public Flight(String number, String flightDuration, Airport departure, Airport arrival, Airplane aplane, double firstClassPrice,
			      double coachClassPrice, String departureTime, String arrivalTime, int coachClassCapacity,
			      int firstClassCapacity) {
		this.departure = departure;
		this.arrival = arrival;
		this.aplane = aplane;
		this.firstClassPrice = firstClassPrice;
		this.coachClassPrice = coachClassPrice;
		this.number = number;
		this.flightDuration = flightDuration;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.firstClassCapacity = firstClassCapacity;
		this.coachClassCapacity = coachClassCapacity;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public void print() {
		String print_str = number+" :: "+departure.code() +" "+"flight duration"+" :: "+flightDuration +" "+ departureTime+" ===> "+arrival.code()+" "+arrivalTime
			+" :: First Class reserved:"+ firstClassCapacity+" First Class Price: $"+ firstClassPrice+" "+" :: Coach Class reserved:"+coachClassCapacity+" Coach Class Price: $"+ coachClassPrice;
		System.out.println(print_str);
	}
	
	public Airport arrivalAirport() {
		return this.arrival;
	}
}

