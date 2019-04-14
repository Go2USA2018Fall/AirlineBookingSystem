package flight;

import airport.Airport;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.TimeZone;
import java.time.*;
//import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;

import airplane.Airplane;

public class Flight {
	
	private String number;
	private String flightDuration;
	private Airport departure;
	private Airport arrival;
	private Airplane aplane;
	private String departureTime;
	private String arrivalTime;
	private ZonedDateTime departureDate;
	private ZonedDateTime arrivalDate;
	private float firstClassPrice;
	private float coachClassPrice;
	private int firstClassReserved;
	private int coachClassReserved;
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
	public Flight(String number, String flightDuration, Airport departure, Airport arrival, Airplane aplane, float firstClassPrice,
			      float coachClassPrice, String departureTime, String arrivalTime, int coachClassReserved,
			      int firstClassReserved) throws Exception {

		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm z");
		DateTimeFormatter timeParser = DateTimeFormatter.ofPattern("HH:mm");
		
		LocalDateTime depTime = LocalDateTime.parse(departureTime, dateParser);
		this.departureDate = depTime.atZone(ZoneId.of("UTC"));
		LocalDateTime arrTime = LocalDateTime.parse(arrivalTime, dateParser);
		this.arrivalDate = arrTime.atZone(ZoneId.of("UTC"));
		LocalTime tmpDepTime = this.departureDate.toLocalTime();
		LocalTime tmpArrTime = this.arrivalDate.toLocalTime();
		this.departureTime = tmpDepTime.format(timeParser);
		this.arrivalTime = tmpArrTime.format(timeParser);
		this.departure = departure;
		this.arrival = arrival;
		this.aplane = aplane;
		this.firstClassPrice = firstClassPrice;
		this.coachClassPrice = coachClassPrice;
		this.number = number;
		this.flightDuration = flightDuration;
		this.firstClassReserved = firstClassReserved;
		this.coachClassReserved = coachClassReserved;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public String toString(boolean debug) {
		String printStr;
		if (debug) {
			printStr = number+" :: "+departure.code() +" "+"flight duration"+" :: "+flightDuration +" "+ departureTime+" ===> "+arrival.code()+" "+arrivalTime
			+" :: First Class reserved:"+ firstClassReserved+" First Class Price: $"+ firstClassPrice+" "+" :: Coach Class reserved:"+coachClassReserved+" Coach Class Price: $"+ coachClassPrice;
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
	
	public Airport departureAirport() {
		return this.departure;
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
	
	public ZonedDateTime departureDate() {
		return this.departureDate;
	}
		
	public ZonedDateTime arrivalDate() {
		return this.arrivalDate;
	}
	
	public float getFirstClassPrice() {
		return this.firstClassPrice;
	}
	
	public float getCoachClassPrice() {
		return this.coachClassPrice;
	}
	
	public boolean isFirstClassAvailable() {
		return this.firstClassReserved < this.aplane.getFirstClassCapacity();
	}
	
	public boolean isCoachClassAvailable() {
		return this.coachClassReserved < this.aplane.getCoachClassCapacity();
	}
}

