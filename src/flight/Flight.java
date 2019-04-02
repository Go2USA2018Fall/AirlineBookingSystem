package flight;

import airport.Airport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import airplane.Airplane;

public class Flight {
	
	private String number;
	private String flightDuration;
	private Airport departure;
	private Airport arrival;
	private Airplane aplane;
	private String departureTime;
	private String arrivalTime;
	private Date departureDate;
	private Date arrivalDate;
	private double firstClassPrice;
	private double coachClassPrice;
	private int firstClassCapacity;
	private int coachClassCapacity;
	/**
	 * Default constructor
	 * 
	 * Constructor with params. Requires object fields to be explicitly
	 * set using setter methods
	 * @throws Exception 
	 * 
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */	
	public Flight(String number, String flightDuration, Airport departure, Airport arrival, Airplane aplane, double firstClassPrice,
			      double coachClassPrice, String departureTime, String arrivalTime, int coachClassCapacity,
			      int firstClassCapacity) throws Exception {
		
		DateFormat dateParser = new SimpleDateFormat("yyyy MMM dd HH:mm");
		dateParser.setTimeZone(TimeZone.getTimeZone("GMT"));
		SimpleDateFormat timeParser = new SimpleDateFormat("HH:mm");
		timeParser.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		this.departureDate = dateParser.parse(departureTime);
		this.arrivalDate = dateParser.parse(arrivalTime);
		this.departure = departure;
		this.arrival = arrival;
		this.aplane = aplane;
		this.firstClassPrice = firstClassPrice;
		this.coachClassPrice = coachClassPrice;
		this.number = number;
		this.flightDuration = flightDuration;
		this.departureTime = timeParser.format(this.departureDate);
		this.arrivalTime = timeParser.format(this.arrivalDate);
		this.firstClassCapacity = firstClassCapacity;
		this.coachClassCapacity = coachClassCapacity;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public String toString(boolean debug) {
		String printStr;
		if (debug) {
			printStr = number+" :: "+departure.code() +" "+"flight duration"+" :: "+flightDuration +" "+ departureTime+" ===> "+arrival.code()+" "+arrivalTime
			+" :: First Class reserved:"+ firstClassCapacity+" First Class Price: $"+ firstClassPrice+" "+" :: Coach Class reserved:"+coachClassCapacity+" Coach Class Price: $"+ coachClassPrice;
		} else {
			printStr = number+" :: "+departure.code() +" "+ departureTime+" ===> "+arrival.code()+" "+arrivalTime;
		}
		
		return printStr;
	}
	
	public void print() {
		System.out.println(toString(false));
	}

	public void printV() {
		System.out.println(toString(true));
	}
	
	public Airport arrivalAirport() {
		return this.arrival;
	}
	
	public String arrivalTime() {
		return this.arrivalTime;
	}
	
	public String departureTime() {
		return this.departureTime;
	}
	
	public int flightDuration() {
		return Integer.parseInt(this.flightDuration);
	}
	
	public Date departureDate() {
		return this.departureDate;
	}
	
	public Date arrivalDate() {
		return this.arrivalDate;
	}
}

