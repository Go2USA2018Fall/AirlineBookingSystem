package TripRequest;

import airport.Airport;

import java.time.*;
import java.time.format.DateTimeFormatter;

import utils.TimeConverter;
/**
 * construct trip request base on user input also validate the input
 * 
 * @author Yijie Yan
 *
 */
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
	private LocalDateTime earliestFirst;
	private LocalDateTime earliestSecond;
	private LocalDateTime latestFirst;
	private LocalDateTime latestSecond;
	
	public TripRequest() {
		return;
	}
	/**
	 * ctor
	 * 
	 * @param departure
	 * @param arrival
	 * @param departureDate
	 * @param arrivalDate
	 * @param returnDepartureDate
	 * @param returnArrivalDate
	 * @param oneWay
	 * @param economySeat
	 * @param searchByDeparture
	 * @param earliestFirst
	 * @param latestFirst
	 * @param earliestSecond
	 * @param latestSecond
	 * @throws Exception
	 */
	public TripRequest(Airport departure, Airport arrival, String departureDate, String arrivalDate,
			String returnDepartureDate, String returnArrivalDate, boolean oneWay, boolean economySeat, boolean searchByDeparture,
			String earliestFirst, String latestFirst, String earliestSecond, String latestSecond) throws Exception {
		
		DateTimeFormatter dateParser = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		DateTimeFormatter timeParser = DateTimeFormatter.ofPattern("yyyy_MM_dd HH:mm");

		try {
			this.departureDate = LocalDate.parse(departureDate, dateParser);;
			this.arrivalDate = LocalDate.parse(arrivalDate, dateParser);;
			this.returnDepartureDate = LocalDate.parse(returnDepartureDate, dateParser);;
			this.returnArrivalDate = LocalDate.parse(returnArrivalDate, dateParser);;
			this.isInvalid = false;
			this.earliestFirst = LocalDateTime.parse(earliestFirst, timeParser);
			this.latestFirst = LocalDateTime.parse(latestFirst, timeParser);
			if (!oneWay) {
				this.earliestSecond = LocalDateTime.parse(earliestSecond, timeParser);
				this.latestSecond = LocalDateTime.parse(latestSecond, timeParser);
			}
		} catch(Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in date format, please check date format";
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
	
	public void timeFrame(String earliest, String latest) {
		try {
			if (this.searchByDeparture) {
				String elocalDate = this.departureDateString() + " " + earliest;
				this.earliestFirst = TimeConverter.toUTCTime(
						this.departure.latitude(), this.departure.longitude(),
						elocalDate);
				String llocalDate = this.departureDateString() + " " + latest;
				this.latestFirst = TimeConverter.toUTCTime(
						this.departure.latitude(), this.departure.longitude(),
						llocalDate);
			} else {
				String elocalDate = this.arrivalDateString() + " " + earliest;
				this.earliestFirst = TimeConverter.toUTCTime(
						this.arrival.latitude(), this.arrival.longitude(),
						elocalDate);
				String llocalDate = this.arrivalDateString() + " " + latest;
				this.latestFirst = TimeConverter.toUTCTime(
						this.arrival.latitude(), this.arrival.longitude(),
						llocalDate);
			}
		} catch (Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in time fram format, please check time fram format";
			
		}
		
	}
	
	public void timeFrame(String earliestFirst, String latestFirst, String earliestSecond, String latestSecond) {
		try {
			if (this.searchByDeparture) {
				String eflocalDate = this.departureDateString() + " "
						+ earliestFirst;
				this.earliestFirst = TimeConverter.toUTCTime(
						this.departure.latitude(), this.departure.longitude(),
						eflocalDate);
				String lflocalDate = this.departureDateString() + " "
						+ latestFirst;
				this.latestFirst = TimeConverter.toUTCTime(
						this.departure.latitude(), this.departure.longitude(),
						lflocalDate);
				String eslocalDate = this.returnDepartureDateString() + " "
						+ earliestSecond;
				this.earliestSecond = TimeConverter.toUTCTime(
						this.arrival.latitude(), this.arrival.longitude(),
						eslocalDate);
				String lslocalDate = this.returnDepartureDateString() + " "
						+ latestSecond;
				this.latestSecond = TimeConverter.toUTCTime(
						this.arrival.latitude(), this.arrival.longitude(),
						lslocalDate);

			} else {
				String eflocalDate = this.arrivalDateString() + " "
						+ earliestFirst;
				this.earliestFirst = TimeConverter.toUTCTime(
						this.arrival.latitude(), this.arrival.longitude(),
						eflocalDate);
				String lflocalDate = this.arrivalDateString() + " "
						+ latestFirst;
				this.latestFirst = TimeConverter.toUTCTime(
						this.arrival.latitude(), this.arrival.longitude(),
						lflocalDate);
				String eslocalDate = this.returnDepartureDateString() + " "
						+ earliestSecond;
				this.earliestSecond = TimeConverter.toUTCTime(
						this.departure.latitude(), this.departure.longitude(),
						eslocalDate);
				String lslocalDate = this.returnDepartureDateString() + " "
						+ latestSecond;
				this.latestSecond = TimeConverter.toUTCTime(
						this.departure.latitude(), this.departure.longitude(),
						lslocalDate);
			}
		} catch (Exception e) {
			this.isInvalid = true;
			this.invalidMessage = "Error in time fram format, please check time fram format";
		}
		

	}
	
	public boolean isInvalid() {
		return this.isInvalid;
	}
	
	public void setisInvalid(boolean isInvalid){
		this.isInvalid = isInvalid;
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
		return this.economySeat ? "Coach" : "FirstClass";
	}
	
	public boolean isRoundTrip() {
		return !this.oneWay;
	}
	
	public LocalDateTime earliestFirst() {
		return this.earliestFirst;
	}
	
	public LocalDateTime earliestSecond() {
		return this.earliestSecond;
	}
	
	public LocalDateTime latestFirst() {
		return this.latestFirst;
	}
	
	public LocalDateTime latestSecond() {
		return this.latestSecond;
	}
}
