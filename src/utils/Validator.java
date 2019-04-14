package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import TripRequest.TripRequest;
import flight.Flight;
import flight.Flights;
import trip.Trip;
import trip.Trips;

public class Validator {

	public static Trips validateTrips(Trips trips, TripRequest tripRequest) throws Exception {
		Trips validatedTrips = new Trips();
		for (Trip trip : trips) {
			if (validateTrip(trip, tripRequest))
				validatedTrips.add(trip);
		}
		return validatedTrips;
	}
	
	private static boolean validateTrip(Trip trip, TripRequest tripRequest) throws Exception {
		boolean valid = true;
		Flights tripFlights = trip.tripFlights();
		
		int size = tripFlights.size();
		int duration = tripFlights.get(0).flightDuration();
		for (int i = 0; i < size-1; i++) {
			duration += tripFlights.get(i+1).flightDuration();
			if (i+1 == size-1)
				valid = valid && validateConnectingFlights(tripFlights.get(i), tripFlights.get(i+1));
			else
				valid = valid && validateConnectingFlights(tripFlights.get(i), tripFlights.get(i+1));
		}
		String seatClass = tripRequest.getSeatClass();
		for (Flight flight: tripFlights) {
			if (seatClass.equals("coach")) {
				valid = valid && flight.isCoachClassAvailable();
			} else if(seatClass.equals("firstclass")) {
				valid = valid && flight.isFirstClassAvailable();
			}
			
			if (!valid) break;
		}
		//valid = valid && validateFlightDates(tripFlights.get(size-1), tripRequest);
		return valid;
	}
	
//	private static boolean validateFlightDates(Flight arrivalFlight, TripRequest tripRequest) {
//		
//		boolean valid =  arrivalFlight.arrivalDate().before(tripRequest.arrivalDate());
//		return valid;
//	}

	private static boolean validateConnectingFlights(Flight first, Flight second) throws Exception {
		boolean valid = true;
		
		String arrivalTime = first.arrivalTime();
		String departureTime = second.departureTime();
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date arrival = format.parse(arrivalTime);
		Date departure = format.parse(departureTime);
		long layoverTime = (departure.getTime() - arrival.getTime())/60000;
		
		valid = valid && (layoverTime > 30 && layoverTime < 600);
		
		return valid;
	}
}
