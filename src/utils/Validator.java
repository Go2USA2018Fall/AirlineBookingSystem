package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

import TripRequest.TripRequest;
import flight.Flight;
import flight.Flights;
import trip.Trip;
import trip.Trips;

public class Validator {

	public static Trips validateTrips(Trips trips, TripRequest tripRequest, boolean secondLegTrips) throws Exception {
		Trips validatedTrips = new Trips();
		for (Trip trip : trips) {
			if (validateTrip(trip, tripRequest, secondLegTrips))
				validatedTrips.add(trip);
		}
		return validatedTrips;
	}
	
	private static boolean validateTrip(Trip trip, TripRequest tripRequest, boolean secondLegTrips) throws Exception {
		boolean valid = true;
		Flights tripFlights = trip.tripFlights();
		
		int size = tripFlights.size();
		//int duration = tripFlights.get(0).flightDuration();
		for (int i = 0; i < size-1; i++) {
			//duration += tripFlights.get(i+1).flightDuration();
			if (i+1 == size-1)
				valid = valid && validateConnectingFlights(tripFlights.get(i), tripFlights.get(i+1));
			else
				valid = valid && validateConnectingFlights(tripFlights.get(i), tripFlights.get(i+1));
			
			if (!valid) return false;
		}
		String seatClass = tripRequest.getSeatClass();
		for (Flight flight: tripFlights) {
			if (seatClass.equals("coach")) {
				valid = valid && flight.isCoachClassAvailable();
			} else if(seatClass.equals("firstclass")) {
				valid = valid && flight.isFirstClassAvailable();
			}
			
			if (!valid) return false;
		}
		
		valid = valid && validateTimeFrame(trip, tripRequest, secondLegTrips);
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
		valid = valid && (first.arrivalDate().compareTo(second.departureDate()) <= 0);
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date arrival = format.parse(arrivalTime);
		Date departure = format.parse(departureTime);
		long layoverTime = (departure.getTime() - arrival.getTime())/60000;
		
		valid = valid && (layoverTime > 30 && layoverTime < 600);
		
		return valid;
	}
	
	public static boolean validateTimeFrame(Trip trip, TripRequest tripRequest, boolean secondLeg) {
		LocalTime lower = null;
		LocalTime higher = null;
		if (secondLeg) {
			lower = tripRequest.earliestSecond();
			higher = tripRequest.latestSecond();
		}
		else { 
			lower = tripRequest.earliestFirst();
			higher = tripRequest.latestFirst();
		}
		if (tripRequest.searchByDeparture()) {
			LocalTime departureTime = trip.getDepartureDateTime().toLocalTime();
			return departureTime.isAfter(lower) && departureTime.isBefore(higher);
		}
		LocalTime arrivalTime = trip.getArrivalDateTime().toLocalTime();
		return arrivalTime.isAfter(lower) && arrivalTime.isBefore(higher);
	}
}
