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
import dao.ServerInterface;
import flight.Flights;
import trip.Trips;
import tripfinder.TripFinder;
import utils.ArrivalComparator;
import utils.DepartureComparator;
import utils.DurationComparator;
import utils.PriceComparator;
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
		Collections.sort(firstLegTrips, new DepartureComparator());
		firstLegTrips.print();
		Trips secondLegTrips = null;
		boolean loopValid = true;
		boolean firstLeg = true;
		
		while(loopValid) {
			System.out.println("Commands: select <trip index>, sort <price, departure, arrival, duration>, exit");
			System.out.print("console> ");
			String input = reader.readLine();
			if (input.startsWith("select") && firstLeg) {
				int firstLegIndex = Integer.parseInt(input.split(" ")[1]); 
				System.out.println(firstLegTrips.get(firstLegIndex));
				if (tripRequest.isRoundTrip()) {
					firstLeg = false;
					secondLegTrips = tripFinder.findSecondLegTrips();
					Collections.sort(secondLegTrips, new DepartureComparator());
					secondLegTrips.print();
					System.out.println("SECOND LEG TRIPS");
				}
			}
			
			else if (input.startsWith("select") && !firstLeg) {
				int secondLegIndex = Integer.parseInt(input.split(" ")[1]);
				System.out.println(secondLegTrips.get(secondLegIndex));
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
				loopValid = false;
			}
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
		TripRequest tripRequest = new TripRequest();
		System.out.println("Please choose type of trip(One-Way-1, Round-Trip -2):");
		int tripType = Integer.parseInt(reader.readLine());
		boolean oneWay = (tripType == 1);
		tripRequest.roundTrip(oneWay);
		System.out.print("Search by 1)departure date or 2)arrival date:> ");
		int searchBy = Integer.parseInt(reader.readLine());
		boolean searchByDeparture = (searchBy == 1);
		tripRequest.searchByDeparture(searchByDeparture);
		System.out.println("Here are list of Airports you can select from: ");
		airports.print();
		System.out.println("Please select the departure airport: ");
		int departureAirportIndex = Integer.parseInt(reader.readLine());
		System.out.println("Please select the arrival airport: ");
		int arrivalAirportIndex = Integer.parseInt(reader.readLine());
		if (oneWay) {
			if (searchByDeparture) {
				System.out.println("Please input the departure date (yyyy_mm_dd);");
				departureDate = reader.readLine();
				System.out.println("Please input the earlist time for departure: (HH:mm)");
				String earliestDepartTime = reader.readLine();
				System.out.println("Please input the latest time for departure: (HH:mm)");
				String latestDepartTime = reader.readLine();
				
				tripRequest.departureDate(departureDate);
				tripRequest.timeFrame(earliestDepartTime, latestDepartTime);
			} else {
				System.out.println("Please input the arrival date(yyyy_mm_dd);");
				arrivalDate = reader.readLine();
				System.out.println("Please input the earlist time for arrival: (HH:mm)");
				String earliestArrivalTime = reader.readLine();
				System.out.println("Please input the latest time for arrival: (HH:mm)");
				String latestArrivalTime = reader.readLine();
				
				tripRequest.arrivalDate(arrivalDate);
				tripRequest.timeFrame(earliestArrivalTime, latestArrivalTime);
			}
			returnDepartureDate = "";
			returnArrivalDate = "";
		} else {
			if (searchByDeparture) {
				System.out.println("Please input the departure date of your first leg (yyyy_mm_dd);");
				departureDate = reader.readLine();
				System.out.println("Please input the earlist time for departure for your first leg: (HH:mm)");
				String earliestDepartTimeFirst = reader.readLine();
				System.out.println("Please input the latest time for departure for your first leg: (HH:mm)");
				String latestDepartTimeFirst = reader.readLine();
				System.out.println("Please input the departure date of your second leg (yyyy_mm_dd);");
				returnDepartureDate = reader.readLine();
				System.out.println("Please input the earlist time for departure for your second leg: (HH:mm)");
				String earliestDepartTimeSecond = reader.readLine();
				System.out.println("Please input the latest time for departure for your second leg: (HH:mm)");
				String latestDepartTimeSecond = reader.readLine();
				
				tripRequest.departureDate(departureDate);
				tripRequest.returnDepartureDate(returnDepartureDate);
				tripRequest.timeFrame(earliestDepartTimeFirst, latestDepartTimeFirst, earliestDepartTimeSecond, latestDepartTimeSecond);
			} else {
				System.out.println("Please input the arrival date of your first leg(yyyy_mm_dd);");
				arrivalDate = reader.readLine();
				System.out.println("Please input the earlist time for arrival for your first leg: (HH:mm)");
				String earliestArrivalTimeFirst = reader.readLine();
				System.out.println("Please input the latest time for arrival for your first leg: (HH:mm)");
				String latestArrivalTimeFirst = reader.readLine();
				System.out.println("Please input the arrival date of your second leg(yyyy_mm_dd);");
				returnArrivalDate = reader.readLine();
				System.out.println("Please input the earlist time for arrival for your second leg: (HH:mm)");
				String earliestArrivalTimeSecond = reader.readLine();
				System.out.println("Please input the latest time for arrival for your second leg: (HH:mm)");
				String latestArrivalTimeSecond = reader.readLine();
				
				tripRequest.arrivalDate(returnArrivalDate);
				tripRequest.returnArrivalDate(returnArrivalDate);
				tripRequest.timeFrame(earliestArrivalTimeFirst, latestArrivalTimeFirst, earliestArrivalTimeSecond, latestArrivalTimeSecond);
			}
		}
		System.out.println("Please input seat type (Economy-1, First class-2)");
		int seatType = Integer.parseInt(reader.readLine());
		boolean economySeat = (seatType == 1);
		tripRequest.seatClass(economySeat);
		Airport departure = airports.get(departureAirportIndex);
		Airport arrival = airports.get(arrivalAirportIndex);
		tripRequest.departure(departure);
		tripRequest.arrival(arrival);
		return tripRequest;
	}
	
	private static TripRequest testInput(BufferedReader reader, Airports airports) throws Exception {
		airports.print();
		Airport departure = airports.get(25);
		Airport arrival = airports.get(27);
		return new TripRequest(departure, arrival, "2019_05_18", "2019_05_17", "2019_06_04", "2019_05_18", false, false, true,
				"00:01", "23:00", "00:01", "23:59");
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
		//ServerInterface.INSTANCE.getFlightsFrom(airportCode, departureDate).print();
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
