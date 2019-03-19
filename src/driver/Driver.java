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
		int departureAirportIndex = input.nextInt();
		String departureDate = null;
		do {
			System.out.println("Please input the Date of departure(yyyy_mm_dd);");
			departureDate = input.next();
		} while (!isValidDate(departureDate));
		String airportCode = airports.get(departureAirportIndex).code();
		System.out.println("Here is a list of flight leaving from "+airportCode);
		ServerInterface.INSTANCE.getFlightsFrom(teamName, airportCode, departureDate).print();

		
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
