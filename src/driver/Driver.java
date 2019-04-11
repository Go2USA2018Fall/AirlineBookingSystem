/**
 * 
 */
package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import flight.Flights;
import trip.Trips;
import tripfinder.TripFinder;
import utils.Saps;

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
		Airports airports = ServerInterface.INSTANCE.getAirports();
		Collections.sort(airports);
		//TripRequest tripRequest = parseInput(reader, airports);
		TripRequest tripRequest = testInput(reader, airports);
		while(tripRequest.isInvalid()) {
			System.out.println("Error in input: " + tripRequest.invalidMessage());
			tripRequest = parseInput(reader, airports);
		}
		
		TripFinder tripFinder = new TripFinder(tripRequest);
		Trips firstLegTrips = tripFinder.findFirstLegTrips();
		firstLegTrips.print();
		System.out.print("Please select a trip by index: ");
		int firstLegIndex = Integer.parseInt(reader.readLine());
		System.out.println(firstLegTrips.get(firstLegIndex));
		if (tripRequest.isRoundTrip()) {
			Trips secondLegTrips = tripFinder.findSecondLegTrips();
			secondLegTrips.print();
			System.out.print("Please select the second leg trip by index: ");
			int secondLegIndex = Integer.parseInt(reader.readLine());
			System.out.println(secondLegTrips.get(secondLegIndex));
		}
		
	}
	
	/**
	 * Handle the UI display when User choose one way trip option. 
	 * It will handle User input and use it for searching trip
	 * 
	 * @param input
	 * @param airports
	 * @param teamName
	 * @throws Exception 
	 * @pre user choose one way as their trip option 
	 * @post user input will be verified and invoke search function.
	 */
	public static TripRequest parseInput(BufferedReader reader, Airports airports) throws Exception {
		String departureDate, arrivalDate, returnDepartureDate, returnArrivalDate;
		System.out.println("Please choose type of trip(One-Way-1, Round-Trip -2):");
		int tripType = Integer.parseInt(reader.readLine());
		boolean oneWay = (tripType == 1);
		System.out.println("Here are list of Airports you can select from: ");
		airports.print();
		System.out.println("Please input the number of the departure airport: ");
		int departureAirportIndex = Integer.parseInt(reader.readLine());
		System.out.println("Please input the number of the arrival airport: ");
		int arrivalAirportIndex = Integer.parseInt(reader.readLine());
		if (oneWay) {
			System.out.println("Please input the Date of departure(yyyy_mm_dd);");
			departureDate = reader.readLine();
			System.out.println("Please input the Date of arrival(yyyy_mm_dd);");
			arrivalDate = reader.readLine();
			returnDepartureDate = "";
			returnArrivalDate = "";
		} else {
			System.out.println("Please input the departure date your first leg (yyyy_mm_dd);");
			departureDate = reader.readLine();
			System.out.println("Please input the arrival date of your first leg(yyyy_mm_dd);");
			arrivalDate = reader.readLine();
			System.out.println("Please input the departure date your second leg (yyyy_mm_dd);");
			returnDepartureDate = reader.readLine();
			System.out.println("Please input the arrival date of your second leg(yyyy_mm_dd);");
			returnArrivalDate = reader.readLine();
		}
		System.out.println("Please input seat type (Economy-1, First class-2)");
		int seatType = Integer.parseInt(reader.readLine());
		boolean economySeat = (seatType == 1);
		Airport departure = airports.get(departureAirportIndex);
		Airport arrival = airports.get(arrivalAirportIndex);
		
		return new TripRequest(departure, arrival, departureDate, arrivalDate, returnDepartureDate, returnArrivalDate, oneWay, economySeat);
	}
	
	private static TripRequest testInput(BufferedReader reader, Airports airports) throws Exception {
		airports.print();
		Airport departure = airports.get(25);
		Airport arrival = airports.get(27);
		return new TripRequest(departure, arrival, "2019_05_16", "2019_05_17", "2019_05_17", "2019_05_18", false, false);
	}
	
	/**
	 * Handle the UI display when User choose round trip option. 
	 * It will handle User input and use it for searching trip
	 * 
	 * @param input
	 * @param airports
	 * @param teamName
	 * @throws Exception 
	 * 
	 * @pre user choose round trip as their trip option 
	 * @post user input will be verified and invoke search function.
	 */
	public static void roundTrip(Scanner input, Airports airports,String teamName) throws Exception{
		System.out.println("Please input the number of the departure airport: ");
		int departureAirportIndex = input.nextInt();
		System.out.println("Please input the number of the arrival airport: ");
		int arrival = input.nextInt();
		String departureDate = null;
		String returndepartureDate = null;
		do {
			System.out.println("Please input the Date of departure(yyyy_mm_dd);");
			departureDate = input.next();
		} while (!isValidDate(departureDate));
		do {
			System.out.println("Please input the Date of departure(yyyy_mm_dd);");
			returndepartureDate = input.next();
		} while (!isValidDate(returndepartureDate));
		String airportCode = airports.get(departureAirportIndex).code();
		System.out.println("Here is a list of flight leaving from "+airportCode);
		ServerInterface.INSTANCE.getFlightsFrom(airportCode, departureDate).print();
		//currently just display a list of flight from departure airport. will be changed in the future iteration to complete search function. 
	}
	

	/**
	 * Validate the time format of user input. 
	 * 
	 * @param inDate
	 * @return boolean
	 */
	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Saps.DAY_FORMAT);
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			System.out.println("Date formate is wrong! Please enter the correct formate(MM/DD/YYYY): ");
			return false;
		}
		return true;
	}

}
