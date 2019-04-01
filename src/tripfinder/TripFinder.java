package tripfinder;

import TripRequest.TripRequest;
import airport.Airport;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import trip.Trips;

public class TripFinder {
	
	private TripRequest tripRequest;

	public TripFinder(TripRequest tripRequest) {
		this.tripRequest = tripRequest;
	}
	
	public Trips findTrips() {
		int stopOver = 0;
		Trips trips = new Trips();
		Flights flights = new Flights();
		findTrip(tripRequest.departureAirport(), tripRequest.arrivalAirport(), trips, flights, stopOver);
		return trips;
	}
	
	private void findTrip(Airport departure, Airport arrival, Trips trips, Flights flights, int stopOver) {
		if (stopOver > 0) {
			return;
		} else {
			Flights tmpFlights = ServerInterface.INSTANCE.getFlightsFrom(departure.code(), tripRequest.departureDateString());
			for (Flight tmpFlight: tmpFlights) {
				if (tmpFlight.arrivalAirport().compareTo(arrival) == 0) {
					flights.add(tmpFlight);
					trips.add(flights);
				} else {
					flights.add(tmpFlight);
					findTrip(tmpFlight.arrivalAirport(), arrival, trips, flights, stopOver+1);
				}
			}
		}
	}
	
}
