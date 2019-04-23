/**
 * 
 */
package utils;

/**
 * @author blake
 * @version 1.2
 * @since 2016-02-24
 *
 */
public class QueryFactory {
	
	/**
	 * Return a query string that can be passed to HTTP URL to request list of airports
	 * 
	 * @param teamName is the name of the team to specify the data copy on server
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirports(String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airports";
	}
	
	public static String getFlightsFrom(String teamName, String code, String date) {
		return "?team=" + teamName + "&action=list&list_type=departing&airport="+code+"&day="+date;
	}
	
	public static String getAirplanes(String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airplanes";
	}
	
	public static String getFlightsTo(String teamName, String code, String date) {
		return "?team=" + teamName + "&action=list&list_type=arriving&airport="+code+"&day="+date;
	}
	/**
	 * Lock the server database so updates can be written
	 * 
	 * @param teamName is the name of the team to acquire the lock
	 * @return the String written to HTTP POST to lock server database 
	 */
	public static String lock (String teamName) {
		return "team=" + teamName + "&action=lockDB";
	}
	
	/**
	 * Unlock the server database after updates are written
	 * 
	 * @param teamName is the name of the team holding the lock
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock (String teamName) {
		return "team=" + teamName + "&action=unlockDB";
	}
	
	/**
	 * Reserve seat on different flights 
	 * 
	 * @param teamName
	 * @param xmlFlights
	 * @return  the String written to the HTTP POST to reserve seat on flights
	 */
	public static String reserveSeat(String teamName,String xmlFlights){
		return "team=" + teamName + "&action=buyTickets" + "&flightData=" +xmlFlights;
	}
	
}
