package test;

import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;

import TripRequest.TripRequest;
import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import flight.Flight;
import trip.Trip;
import trip.Trips;
import tripfinder.TripFinder;

public class TestTripRequriement {
	private Airports airports = null;
	private Airport departure = null;
	private Airport arrival = null;
	private TripRequest tripRequest = null;

	@Before
	public void initObjects() {
		 airports = ServerInterface.INSTANCE.getAirports();
		 departure = airports.get(25);
		 arrival = airports.get(27);
		try {
			tripRequest = new TripRequest(departure, arrival, "2019_05_18", "2019_05_17", "2019_06_04", "2019_05_18",
					true, true, true, "2019_05_17 00:01", "2019_05_17 23:00", "2019_05_18 00:01", "2019_05_18 23:59");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testlayoverCount() {

		TripFinder tripFinder = new TripFinder(tripRequest, airports);
		Trips trips = null;
		try {
			trips = tripFinder.findFirstLegTrips();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Trip trip : trips) {
			assertTrue("This is true", trip.tripFlights().size() <= 3);
		}

	}
	
	@Test
	public void testlayoverTime() {

		TripFinder tripFinder = new TripFinder(tripRequest, airports);
		Trips trips = null;
		try {
			trips = tripFinder.findFirstLegTrips();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Trip trip : trips) {
			int size = trip.tripFlights().size();
			for (int i = 0; i < size-1; i++) {
				//duration += tripFlights.get(i+1).flightDuration();
				long layoverDuration = zonedDateTimeDifference(trip.tripFlights().get(i).arrivalDate(),trip.tripFlights().get(i+1).departureDate(),ChronoUnit.MINUTES);
				assertTrue("this is true",layoverDuration>=30);
				assertTrue("this is true",layoverDuration<=600);
			}
		}
	}
	
	/*
	 * calculate difference in two zoned time 
	 * @param d1
	 * @param d2
	 * @param unit
	 * @return
	 */
	static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit){
	    return unit.between(d1, d2);
	}


}
