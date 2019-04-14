package utils;

import java.time.ZonedDateTime;
import java.util.Comparator;

import trip.Trip;

public class DepartureComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip one, Trip two) {
		// TODO Auto-generated method stub
		ZonedDateTime oneDateTime = one.getDepartureDateTime();
		ZonedDateTime twoDateTime = two.getDepartureDateTime();
		return oneDateTime.compareTo(twoDateTime);
	}

}
