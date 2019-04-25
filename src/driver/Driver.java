/**
 * 
 */
package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;

import airport.Airport;
import airport.Airports;
import dao.DaoReservation;
import dao.ServerInterface;
import flight.Flights;
import trip.Trip;
import trip.Trips;
import tripfinder.TripFinder;
import utils.ArrivalComparator;
import utils.DepartureComparator;
import utils.DurationComparator;
import utils.PriceComparator;
import utils.Saps;
import utils.TimeConverter;

import java.util.Scanner;

import TripRequest.TripRequest;

/**
 * @author Yijie Yan
 * @since 2019-03-15
 * @version 1.0
 *
 */
public class Driver {
	
	private static String teamName = "NoName";
	private static boolean exitFlag = false;
	/**
	 * Entry point for CS509 sample code driver
	 * 
	 * This driver will retrieve the list of airports from the CS509 server and
	 * print the list to the console sorted by 3 character airport code
	 * 
	 * @param args is the arguments passed to java vm in format of "CS509.sample
	 *             teamName" where teamName is a valid team
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in)); 
		//getting airport information and cache it
		Airports airports = ServerInterface.INSTANCE.getAirports();
		Collections.sort(airports);
		boolean confirmSelection = false;
		boolean inputParsed = false;
		boolean tryConfirmation = false;
		TripRequest tripRequest = null;
		
		//do the trip confirmation step here.
		while((!exitFlag)&&!confirmSelection){
			if (!inputParsed) {
				// tripRequest = testInput(reader, airports);
				try{
					tripRequest = parseInput(reader, airports);
				}catch(Exception e){
					tripRequest =new TripRequest();
					tripRequest.setisInvalid(true);
					inputParsed = false;
				}
				
				while(tripRequest.isInvalid()) {
					message("Error in input: " + tripRequest.invalidMessage(), true);
					// tripRequest = testInput(reader, airports);
					try{
						tripRequest = parseInput(reader, airports);
					}catch(Exception e){
						tripRequest =new TripRequest();
						tripRequest.setisInvalid(true);
						inputParsed = false;
					}
				}
				inputParsed = true;
			}
			Trips selectedTrips = searchTrips(reader,tripRequest,airports);
			// If no first leg trips are found
			if (selectedTrips.size() == 0 && !exitFlag) {
				message("No first leg trips found that match the request", true);
				if (retry(reader)) {
					inputParsed = false;
				} else 
					exitFlag = true;
			}
			// If no second leg trips are found
			if (selectedTrips.size() == 1 && tripRequest.isRoundTrip() && !exitFlag) {
				message("No second leg trips found that match the request", true);
				if (retry(reader)) {
					inputParsed = false;
				} else 
					exitFlag = true;
			}
			// If user exits before selecting a trip(s)
			if (selectedTrips.size() == 0 && exitFlag) {
				if (retry(reader)) {
					inputParsed = false;
					exitFlag = false;
				} else 
					exitFlag = true;
			}
			
			// If trips are found and user selects one/two
			if (selectedTrips.size() > 0) {
				float totalPrice = 0;
				for(Trip trip: selectedTrips){
					totalPrice += trip.getPrice();
				}
				message("The total price of the trip is "+totalPrice,true);
				message("Are you sure you want book this trip? (Yes/No)", false);
				String tripConfirm = reader.readLine();
				if (tripConfirm.equalsIgnoreCase("yes")) {
					tryConfirmation = true;
					confirmSelection = confirmBooking(reader,selectedTrips);
				} else {
					if (retry(reader)) {
						inputParsed = false;
						exitFlag = false;
					} else
						exitFlag = true;
					
				}
			}
			
			// If confirmation was tried but failed
			if (tryConfirmation && !confirmSelection) {
				message("Trip couldn't be booked successfully. Would you like to try again (Yes/No): ", false);
				String response = reader.readLine();
				if (response.equalsIgnoreCase("No")) {
					if (retry(reader)) {
						inputParsed = false;
					} else 
						exitFlag = true;
				} 
			}
		}
		
		message("Thank you for using WPI Airline Booking Software", true);

		
		
	}
	
	public static Trips searchTrips(BufferedReader reader,TripRequest tripRequest,Airports airports) throws Exception{
		Trips selectedTrips = new Trips();
		TripFinder tripFinder = new TripFinder(tripRequest,airports);
		message("Searching for trips ...", true);
		Trips firstLegTrips = tripFinder.findFirstLegTrips();
		if (firstLegTrips.size() == 0) {
			return selectedTrips;
		}
		Collections.sort(firstLegTrips, new DepartureComparator());
		firstLegTrips.print();
		message("Trips found", true);
		Trips secondLegTrips = null;
		boolean loopValid = true;
		boolean firstLeg = true;
		
		while(loopValid) {
			message("Commands: select <trip index>, sort <price, departure, arrival, duration>, exit", true);
			message("console> ", false);
			String input = reader.readLine();
			if (input.startsWith("select") && firstLeg) {
				int firstLegIndex = Integer.parseInt(input.split(" ")[1]); 
				message("You have selected Trip: "+firstLegTrips.get(firstLegIndex), true);
				selectedTrips.add(firstLegTrips.get(firstLegIndex));
				if (tripRequest.isRoundTrip()) {
					firstLeg = false;
					message("Searching for second leg trips ...", true);
					secondLegTrips = tripFinder.findSecondLegTrips();
					if (secondLegTrips.size() == 0) {
						return selectedTrips;
					}
					Collections.sort(secondLegTrips, new DepartureComparator());
					secondLegTrips.print();
					message("Second leg trips", true);
				}
				else if(!tripRequest.isRoundTrip()){
					loopValid = false;
				}
			}
			
			else if (input.startsWith("select") && !firstLeg) {
				int secondLegIndex = Integer.parseInt(input.split(" ")[1]);
				System.out.println(secondLegTrips.get(secondLegIndex));
				selectedTrips.add(secondLegTrips.get(secondLegIndex));
				loopValid = false;
			}
			
			else if (input.startsWith("sort")) {
				Trips toSort = firstLeg ? firstLegTrips : secondLegTrips;
				String sortBy = input.split(" ")[1];
				if (sortBy.equals("price")) {
					Collections.sort(toSort, new PriceComparator());
					toSort.print();
				} else if(sortBy.equals("departure")) {
					Collections.sort(toSort, new DepartureComparator());
					toSort.print();
				} else if (sortBy.equals("arrival")) {
					Collections.sort(toSort, new ArrivalComparator());
					toSort.print();
				} else if (sortBy.equals("duration")) {
					Collections.sort(toSort, new DurationComparator());
					toSort.print();
				}
			} else if (input.startsWith("exit")) {
				selectedTrips = new Trips();
				loopValid = false;
				exitFlag = true;
			}
		}
		return selectedTrips;
		
	}
	
	/**
	 * Handle the UI display when User choose one way trip option. 
	 * It will handle User input and use it for searching trip
	 * 
	 * @param reader
	 * @param airports
	 * @throws Exception 
	 * @pre user choose one way as their trip option 
	 * @post user input will be verified and invoke search function.
	 */
	public static TripRequest parseInput(BufferedReader reader, Airports airports) throws Exception {
		String departureDate, arrivalDate, returnDepartureDate, returnArrivalDate;
		TripRequest tripRequest = new TripRequest();
		message("Please choose type of trip(One-Way-1, Round-Trip -2):", false);
		int tripType = Integer.parseInt(reader.readLine());
		boolean oneWay = (tripType == 1);
		tripRequest.roundTrip(oneWay);
		message("Search by 1)departure date or 2)arrival date:> ", false);
		int searchBy = Integer.parseInt(reader.readLine());
		boolean searchByDeparture = (searchBy == 1);
		tripRequest.searchByDeparture(searchByDeparture);
		message("Here are list of Airports you can select from: ", true);
		airports.print();
		message("Please select the departure airport: ", false);
		int departureAirportIndex = Integer.parseInt(reader.readLine());
		message("Please select the arrival airport: ", false);
		int arrivalAirportIndex = Integer.parseInt(reader.readLine());
		
		Airport departure = airports.get(departureAirportIndex);
		Airport arrival = airports.get(arrivalAirportIndex);
		tripRequest.departure(departure);
		tripRequest.arrival(arrival);
		
		if (oneWay) {
			if (searchByDeparture) {
				message("Please input the departure date (yyyy_mm_dd);", false);
				departureDate = reader.readLine();
				message("Please input the earlist time for departure: (HH:mm)", false);
				String earliestDepartTime = reader.readLine();
				message("Please input the latest time for departure: (HH:mm)", false);
				String latestDepartTime = reader.readLine();
				
				tripRequest.departureDate(departureDate);
				tripRequest.timeFrame(earliestDepartTime, latestDepartTime);
			} else {
				message("Please input the arrival date(yyyy_mm_dd);", false);
				arrivalDate = reader.readLine();
				message("Please input the earlist time for arrival: (HH:mm)", false);
				String earliestArrivalTime = reader.readLine();
				message("Please input the latest time for arrival: (HH:mm)", false);
				String latestArrivalTime = reader.readLine();
				
				tripRequest.arrivalDate(arrivalDate);
				tripRequest.timeFrame(earliestArrivalTime, latestArrivalTime);
			}
			returnDepartureDate = "";
			returnArrivalDate = "";
		} else {
			if (searchByDeparture) {
				message("Please input the departure date of your first leg (yyyy_mm_dd);", false);
				departureDate = reader.readLine();
				message("Please input the earlist time for departure for your first leg: (HH:mm)", false);
				String earliestDepartTimeFirst = reader.readLine();
				message("Please input the latest time for departure for your first leg: (HH:mm)", false);
				String latestDepartTimeFirst = reader.readLine();
				message("Please input the departure date of your second leg (yyyy_mm_dd);", false);
				returnDepartureDate = reader.readLine();
				message("Please input the earlist time for departure for your second leg: (HH:mm)", false);
				String earliestDepartTimeSecond = reader.readLine();
				message("Please input the latest time for departure for your second leg: (HH:mm)", false);
				String latestDepartTimeSecond = reader.readLine();
				
				tripRequest.departureDate(departureDate);
				tripRequest.returnDepartureDate(returnDepartureDate);
				tripRequest.timeFrame(earliestDepartTimeFirst, latestDepartTimeFirst, earliestDepartTimeSecond, latestDepartTimeSecond);
			} else {
				message("Please input the arrival date of your first leg(yyyy_mm_dd);", false);
				arrivalDate = reader.readLine();
				message("Please input the earlist time for arrival for your first leg: (HH:mm)", false);
				String earliestArrivalTimeFirst = reader.readLine();
				message("Please input the latest time for arrival for your first leg: (HH:mm)", false);
				String latestArrivalTimeFirst = reader.readLine();
				message("Please input the arrival date of your second leg(yyyy_mm_dd);", false);
				returnArrivalDate = reader.readLine();
				message("Please input the earlist time for arrival for your second leg: (HH:mm)", false);
				String earliestArrivalTimeSecond = reader.readLine();
				message("Please input the latest time for arrival for your second leg: (HH:mm)", false);
				String latestArrivalTimeSecond = reader.readLine();
				
				tripRequest.arrivalDate(returnArrivalDate);
				tripRequest.returnArrivalDate(returnArrivalDate);
				tripRequest.timeFrame(earliestArrivalTimeFirst, latestArrivalTimeFirst, earliestArrivalTimeSecond, latestArrivalTimeSecond);
			}
		}
		message("Please input seat type (Coach-1, First class-2)", false);
		int seatType = Integer.parseInt(reader.readLine());
		boolean economySeat = (seatType == 1);
		tripRequest.seatClass(economySeat);
		return tripRequest;
	}
	
	private static TripRequest testInput(BufferedReader reader, Airports airports) throws Exception {
		airports.print();
		Airport departure = airports.get(25);
		Airport arrival = airports.get(27);
//		return new TripRequest(departure, arrival, "2019_05_18", "2019_05_17", "2019_06_04", "2019_05_18", false, false, false,
//				"00:01", "23:00", "00:01", "23:59");
		//The time frame we are entering here is UTC time zone
		return new TripRequest(departure, arrival, "2019_05_18", "2019_05_17", "2019_06_04", "2019_05_18", false, false, false,
				"2019_05_17 00:01", "2019_05_17 23:00", "2019_05_18 00:01", "2019_05_18 23:59");
	}
	
	
	
	private static boolean confirmBooking(BufferedReader reader, Trips selectedTrips){
		String xml_asString = DaoReservation.buildXML(selectedTrips);
		boolean lockStatus = ServerInterface.INSTANCE.lock();
		if(lockStatus){
			boolean reserveStatus = ServerInterface.INSTANCE.bookFlights(xml_asString);
			if(reserveStatus){
				ServerInterface.INSTANCE.unlock();
				message("The following trips have been successfully booked:", true);
				for(Trip trip: selectedTrips){
					trip.print();
				}
				message("Thank you for using WPI Airline booking system!", true);
				return true;
			}
			else{
				return false;
			}
			
		}
		else{
			return false;
		}
	}
	
	private static void message(String msg, boolean newline) {
		if (newline) {
			System.out.println(msg);
		} else {
			System.out.print(msg);
		}
	}
	
	private static boolean retry(BufferedReader reader) throws Exception {
		message("1) Retry with new inputs, 2) Exit  : ", false);
		String response = reader.readLine();
		if (response.equalsIgnoreCase("1")) {
			return true;
		} else 
			return false;
	}

}
