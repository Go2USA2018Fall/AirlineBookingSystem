/**
 * 
 */
package driver;

import java.util.Scanner;
import java.io.InputStreamReader; 
import java.io.BufferedReader;
import java.io.IOException; 
import java.util.Collections;

import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import flight.Flights;

/**
 * @author blake
 * @since 2016-02-24
 * @version 1.0
 *
 */
public class Driver {

	public static String teamName = "NoName";
	
	private static void listAirports() {
		Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}
	}
	/**
	 * Entry point for CS509 sample code driver
	 * 
	 * This driver will retrieve the list of airports from the CS509 server and print the list 
	 * to the console sorted by 3 character airport code
	 * 
	 * @param args is the arguments passed to java vm in format of "CS509.sample teamName" where teamName is a valid team
	 */
	public static void main(String[] args) throws IOException {
//		if (args.length != 1) {
//			System.err.println("usage: CS509.sample teamName");
//			System.exit(-1);
//			return;
//		}
		// Display a welcome page
		// Prompt user for departure date and departure airport
		// CAll ServerInterface.INSTANCE.getFlightsFrom(BOS) should return list of flights
		// Loop through list of flights and print it
		BufferedReader reader =  
                new BufferedReader(new InputStreamReader(System.in)); 
      
		// Reading data using readLine 
		 
		System.out.println("******   WELCOME   *********");
		System.out.print("Please enter departure date (yyyy/mm/dd): ");
		String date = reader.readLine();
		date = date.replace("/","_");
		listAirports();
		System.out.print("Please select a departure airport from the above list of airports: ");
		String airport = reader.readLine();
		Flights flights = ServerInterface.INSTANCE.getFlightsFrom(teamName, airport, date);
		flights.print();
		// Try to get a list of airports
		
	}
}
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
 * @author blake
 * @since 2016-02-24
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
		
		System.out.println("Please input the number of the departure airport: ");
		String departureAirportIndex = input.next();
		String departureDate = null;
		do {
			System.out.println("Please input the Date of departure(MM/DD/YYYY): ");
			departureDate = input.next();
		} while (!isValidDate(departureDate));
		
		ServerInterface.INSTANCE.getAirports(teamName);
	}
	
	/**
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
