package TripRequest;

import airport.Airport;
import java.time.*;
import java.time.format.DateTimeFormatter;



public class TripRequest {

	private Airport arrival;
	private Airport departure;
	private boolean oneWay;
	private boolean economySeat;
	private boolean isInvalid = true;
	private boolean searchByDeparture;
	private LocalDate departureDate;
	private LocalDate arrivalDate;
	private LocalDate returnDepartureDate;
	private LocalDate returnArrivalDate;
	private String invalidMessage = "No Error";
	
	public TripRequest() {
		return;
	}
	
	public TripRequest(Airport departure, Airport arrival, String departureDate, String arrivalDate,
			String returnDepartureDate, String returnArrivalDate, boolean oneWay, boolean economySeat, boolean searchByDeparture) throws Exception {
		
		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDate depTime = LocalDate.parse(departureDate, dateParser);
		LocalDate arrTime = LocalDate.parse(arrivalDate, dateParser);
		LocalDate retDepTime = LocalDate.parse(returnDepartureDate, dateParser);
		LocalDate retArrTime = LocalDate.parse(returnArrivalDate, dateParser);
		try {
			this.departureDate = depTime;
			this.arrivalDate = arrTime;
			this.returnDepartureDate = retDepTime;
			this.returnArrivalDate = retArrTime;
			this.isInvalid = false;
		} catch(Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in date format, please check date format";
			//e.printStackTrace();
		}		
		
		this.departure = departure;
		this.arrival = arrival;
		this.oneWay = oneWay;
		this.economySeat = economySeat;
		this.searchByDeparture = searchByDeparture;
	}
	
	public void departure(Airport departure) {
		this.departure = departure;
	}
	
	public void arrival(Airport arrival) {
		this.arrival = arrival;
	}
	
	public void departureDate(String departureDate) {
		try {
			DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
			this.departureDate = LocalDate.parse(departureDate, dateParser);
			this.isInvalid = false;
		} catch(Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in date format, please check date format";
		}
	}
	
	public void arrivalDate(String arrivalDate) {
		try {	
			DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
			this.arrivalDate = LocalDate.parse(arrivalDate, dateParser);
			this.isInvalid = false;
		} catch(Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in date format, please check date format";
		}
	}
	
	public void returnDepartureDate(String returnDepartureDate) {
		try {
			DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
			this.returnDepartureDate = LocalDate.parse(returnDepartureDate, dateParser);
			this.isInvalid = false;
		} catch(Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in date format, please check date format";
		}
	}
	
	public void returnArrivalDate(String returnArrivalDate) {
		try {
			DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
			this.returnArrivalDate = LocalDate.parse(returnArrivalDate, dateParser);
			this.isInvalid = false;
		} catch(Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in date format, please check date format";
		}
	}
	
	public void roundTrip(boolean oneWay) {
		this.oneWay = oneWay;
	}
	
	public void seatClass(boolean economySeat) {
		this.economySeat = economySeat;
	}
	
	public void searchByDeparture(boolean searchByDeparture) {
		this.searchByDeparture = searchByDeparture;
	}
	
	public boolean isInvalid() {
		return this.isInvalid;
	}
	
	public boolean searchByDeparture() {
		return this.searchByDeparture;
	}
	
	public String invalidMessage() {
		return this.invalidMessage;
	}
	
	public Airport arrivalAirport() {
		return this.arrival;
	}
	
	public Airport departureAirport() {
		return this.departure;
	}
	
	public LocalDate arrivalDate() {
		return this.arrivalDate;
	}
	
	public LocalDate departureDate() {
		return this.departureDate;
	}
	
	public String arrivalDateString() {
		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		return dateParser.format(this.arrivalDate);
	}
	
	public String departureDateString() {
		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		return dateParser.format(this.departureDate);
	}
	
	public String returnDepartureDateString() {
		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		return dateParser.format(this.returnDepartureDate);
	}
	
	public String returnArrivalDateString() {
		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		return dateParser.format(this.returnArrivalDate);
	}
	
	public String getSeatClass() {
		return this.economySeat ? "coach" : "firstclass";
	}
	
	public boolean isRoundTrip() {
		return !this.oneWay;
	}
}
