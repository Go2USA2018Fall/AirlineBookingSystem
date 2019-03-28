/**
 * 
 */
package driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import utils.Saps;

import java.util.Scanner;

/**
 * @author Yijie Yan
 * @since 2019-03-15
 * @version 1.0
 *
 */
public class Driver {

	/**
	 * Entry point for CS509 sample code driver
	 * 
	 * This driver will retrieve the list of airports from the CS509 server and
	 * print the list to the console sorted by 3 character airport code
	 * 
	 * @param args is the arguments passed to java vm in format of "CS509.sample
	 *             teamName" where teamName is a valid team
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		if (args.length != 1) {
			System.err.println("usage: CS509.sample teamName");
			System.exit(-1);
			return;
		}
		System.out.println("Please choose type of trip(One-Way-1, Round-Trip -2):");
		int tripType = input.nextInt();
		System.out.println("Here are list of Airports you can select from: ");

		String teamName = args[0];
		// Try to get a list of airports
		Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		for (Airport airport : airports) {
			System.out.println(airports.indexOf(airport) + " - " + airport.toString());
		}
		
		if(tripType == 1 ){
			oneWay( input,  airports, teamName);
		}
		else if (tripType == 2){
			roundTrip( input,  airports, teamName);
		}
		else{
			System.out.println("Invalid trip option. Please enter your option again!");
		}



	}
	
	/**
	 * Handle the UI display when User choose one way trip option. 
	 * It will handle User input and use it for searching trip
	 * 
	 * @param input
	 * @param airports
	 * @param teamName
	 * 
	 * @pre user choose one way as their trip option 
	 * @post user input will be verified and invoke search function.
	 */
	public static void oneWay(Scanner input, Airports airports,String teamName){
		System.out.println("Please input the number of the departure airport: ");
		int departureAirportIndex = input.nextInt();
		System.out.println("Please input the number of the arrival airport: ");
		int arrival = input.nextInt();
		String departureDate = null;
		do {
			System.out.println("Please input the Date of departure(yyyy_mm_dd);");
			departureDate = input.next();
		} while (!isValidDate(departureDate));
		String airportCode = airports.get(departureAirportIndex).code();
		System.out.println("Here is a list of flight leaving from "+airportCode);
		ServerInterface.INSTANCE.getFlightsFrom(teamName, airportCode, departureDate).print();
		//currently just display a list of flight. will be changed in the future iteration to complete search function. 
	}
	
	/**
	 * Handle the UI display when User choose round trip option. 
	 * It will handle User input and use it for searching trip
	 * 
	 * @param input
	 * @param airports
	 * @param teamName
	 * 
	 * @pre user choose round trip as their trip option 
	 * @post user input will be verified and invoke search function.
	 */
	public static void roundTrip(Scanner input, Airports airports,String teamName){
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
		ServerInterface.INSTANCE.getFlightsFrom(teamName, airportCode, departureDate).print();
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
