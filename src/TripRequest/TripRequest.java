package TripRequest;

import airport.Airport;
import java.time.*;
import java.time.format.DateTimeFormatter;



public class TripRequest {

	private Airport arrival;
	private Airport departure;
	private boolean oneWay;
	private boolean economySeat;
	private LocalDate departureDate;
	private LocalDate arrivalDate;
	private LocalDate returnDepartureDate;
	private LocalDate returnArrivalDate;
	private boolean isInvalid = true;
	private String invalidMessage = "No Error";
	
	public TripRequest(Airport departure, Airport arrival, String departureDate, String arrivalDate,
			String returnDepartureDate, String returnArrivalDate, boolean oneWay, boolean economySeat) throws Exception {
		
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
	}
	
	public boolean isInvalid() {
		return this.isInvalid;
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
	
	public String returnArrivalDate() {
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
