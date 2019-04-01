package TripRequest;

import airport.Airport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class TripRequest {

	private Airport arrival;
	private Airport departure;
	private boolean oneWay;
	private boolean economySeat;
	private Date departureDate;
	private Date arrivalDate;
	private boolean isInvalid = true;
	private String invalidMessage = "No Error";
	
	public TripRequest(Airport departure, Airport arrival, String departureDate, String arrivalDate,
				boolean oneWay, boolean economySeat) throws Exception {
		DateFormat dateParser = new SimpleDateFormat("yyyy_mm_dd");
		try {
			this.departureDate = dateParser.parse(departureDate);
			this.arrivalDate = dateParser.parse(arrivalDate);
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
}