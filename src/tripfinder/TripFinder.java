package tripfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TripRequest.TripRequest;
import airport.Airport;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import trip.Trip;
import trip.Trips;
import utils.Validator;

public class TripFinder {
	
	private TripRequest tripRequest;
	private String seatClass;
	private Map<String, Flights> flightCache = new HashMap<String, Flights>();

	public TripFinder(TripRequest tripRequest) {
		this.tripRequest = tripRequest;
		this.seatClass = tripRequest.getSeatClass();
	}
	
	public Trips findFirstLegTrips() throws Exception {
		int stopOver = 0;
		Trips trips = new Trips();
		Flights flights = new Flights();
		findTrip(tripRequest.departureAirport(), tripRequest.arrivalAirport(), trips, flights, stopOver, false);
		System.out.println("Initial No. of Trips = "+ trips.size());
		trips = Validator.validateTrips(trips, tripRequest);
		System.out.println("Initial No. of Trips = "+ trips.size());
		trips.calculatePrice();
		return trips;
	}
	
	public Trips findSecondLegTrips() throws Exception {
		int stopOver = 0;
		Trips trips = new Trips();
		Flights flights = new Flights();
		findTrip(tripRequest.arrivalAirport(), tripRequest.departureAirport(), trips, flights, stopOver, true);
		System.out.println("Initial No. of Trips = "+ trips.size());
		trips = Validator.validateTrips(trips, tripRequest);
		System.out.println("Initial No. of Trips = "+ trips.size());
		trips.calculatePrice();
		return trips;
	}
	
	private void findTrip(Airport departure, Airport arrival, Trips trips, Flights flights, int stopOver, boolean returnTrip) throws Exception {
		if (stopOver > 2) {
			return;
		} else {
			Flights tmpFlights;
			String flightKey;
			if (!returnTrip)
				flightKey = departure.code()+tripRequest.departureDateString();
			else
				flightKey = departure.code()+tripRequest.returnDepartureDateString();
			if (flightCache.containsKey(flightKey))
				tmpFlights = flightCache.get(flightKey);
			else {
				if (!returnTrip)
					tmpFlights = ServerInterface.INSTANCE.getFlightsFrom(departure.code(), tripRequest.departureDateString());
				else
					tmpFlights = ServerInterface.INSTANCE.getFlightsFrom(departure.code(), tripRequest.returnDepartureDateString());
				flightCache.put(flightKey, tmpFlights);
			}
			for (Flight tmpFlight: tmpFlights) {
				if (tmpFlight.arrivalAirport().compareTo(arrival) == 0) {
					Flights newFlights = new Flights(flights);
					newFlights.add(tmpFlight);
					trips.add(new Trip(newFlights, this.seatClass));
				} else {
					Flights newFlights = new Flights(flights);
					newFlights.add(tmpFlight);
					findTrip(tmpFlight.arrivalAirport(), arrival, trips, newFlights, stopOver+1, returnTrip);
				}
			}
		}
	}
	
}
