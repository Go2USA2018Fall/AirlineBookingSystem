package tripfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import TripRequest.TripRequest;
import airplane.Airplane;
import airplane.Airplanes;
import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import trip.Trip;
import trip.Trips;
import utils.Validator;

public class TripFinder {
	
	private TripRequest tripRequest;
	private String seatClass;
	private Map<String, Flights> flightCacheByDeparture = new HashMap<String, Flights>();
	private Map<String, Flights> flightCacheByArrival = new HashMap<String, Flights>();
	private Map<String, Airplane> airplaneData = new HashMap<String, Airplane>();
	private Map<String, Airport> airportData = new HashMap<String, Airport>();
	
	public TripFinder(TripRequest tripRequest,Airports airports) {
		this.tripRequest = tripRequest;
		this.seatClass = tripRequest.getSeatClass();
		Airplanes airplanes = ServerInterface.INSTANCE.getAirplanes();
		for (Airplane airplane: airplanes) {
			airplaneData.put(airplane.getModel(), airplane);
		}
		
		for (Airport airport :airports){
			airportData.put(airport.code(), airport);
		}
	}
	
	public Trips findFirstLegTrips() throws Exception {
		int stopOver = 0;
		Trips trips = new Trips();
		Flights flights = new Flights();
		if (this.tripRequest.searchByDeparture())
			findTripByDeparture(tripRequest.departureAirport(), tripRequest.arrivalAirport(), trips, flights, stopOver, false);
		else {
			findTripByArrival(tripRequest.departureAirport(), tripRequest.arrivalAirport(), trips, flights, stopOver, false);
		}
		trips = Validator.validateTrips(trips, tripRequest, false);
		trips.calculatePrice();
		return trips;
	}
	
	public Trips findSecondLegTrips() throws Exception {
		int stopOver = 0;
		Trips trips = new Trips();
		Flights flights = new Flights();
		if (this.tripRequest.searchByDeparture())
			findTripByDeparture(tripRequest.arrivalAirport(), tripRequest.departureAirport(), trips, flights, stopOver, true);
		else {
			findTripByArrival(tripRequest.arrivalAirport(), tripRequest.departureAirport(), trips, flights, stopOver, true);
		}
		trips = Validator.validateTrips(trips, tripRequest, true);
		trips.calculatePrice();
		return trips;
	}
	
	private void findTripByDeparture(Airport departure, Airport arrival, Trips trips, Flights flights, int stopOver, boolean returnTrip) throws Exception {
		if (stopOver > 2) {
			return;
		} else {
			Flights tmpFlights;
			String flightKey;
			if (!returnTrip)
				flightKey = departure.code()+tripRequest.departureDateString();
			else
				flightKey = departure.code()+tripRequest.returnDepartureDateString();
			if (flightCacheByDeparture.containsKey(flightKey))
				tmpFlights = flightCacheByDeparture.get(flightKey);
			else {
				if (!returnTrip)
					tmpFlights = ServerInterface.INSTANCE.getFlightsFrom(departure.code(), tripRequest.departureDateString(), this.airplaneData,this.airportData);
				else
					tmpFlights = ServerInterface.INSTANCE.getFlightsFrom(departure.code(), tripRequest.returnDepartureDateString(), this.airplaneData,this.airportData);
				flightCacheByDeparture.put(flightKey, tmpFlights);
			}
			for (Flight tmpFlight: tmpFlights) {
				if (tmpFlight.arrivalAirport().compareTo(arrival) == 0) {
					Flights newFlights = new Flights(flights);
					newFlights.add(tmpFlight);
					trips.add(new Trip(newFlights, this.seatClass));
				} else {
					Flights newFlights = new Flights(flights);
					newFlights.add(tmpFlight);
					findTripByDeparture(tmpFlight.arrivalAirport(), arrival, trips, newFlights, stopOver+1, returnTrip);
				}
			}
		}
	}
	
	private void findTripByArrival(Airport departure, Airport arrival, Trips trips, Flights flights, int stopOver, boolean returnTrip) throws Exception {
		if (stopOver > 2) {
			return;
		} else {
			Flights tmpFlights;
			String flightKey;
			if (!returnTrip)
				flightKey = arrival.code()+tripRequest.arrivalDateString();
			else
				flightKey = arrival.code()+tripRequest.returnArrivalDateString();
			if (flightCacheByArrival.containsKey(flightKey))
				tmpFlights = flightCacheByArrival.get(flightKey);
			else {
				if (!returnTrip)
					tmpFlights = ServerInterface.INSTANCE.getFlightsTo(arrival.code(), tripRequest.arrivalDateString(), this.airplaneData, this.airportData);
				else
					tmpFlights = ServerInterface.INSTANCE.getFlightsTo(arrival.code(), tripRequest.returnArrivalDateString(), this.airplaneData, this.airportData);
				flightCacheByArrival.put(flightKey, tmpFlights);
			}
			for (Flight tmpFlight: tmpFlights) {
				if (tmpFlight.departureAirport().compareTo(departure) == 0) {
					Flights newFlights = new Flights(flights);
					newFlights.add(tmpFlight);
					Collections.reverse(newFlights);
					trips.add(new Trip(newFlights, this.seatClass));
				} else {
					Flights newFlights = new Flights(flights);
					newFlights.add(tmpFlight);
					findTripByArrival(departure, tmpFlight.departureAirport(), trips, newFlights, stopOver+1, returnTrip);
				}
			}
		}
	}
}
