package utils;

import java.time.ZonedDateTime;
import java.util.Comparator;

import trip.Trip;
/**
 * use to sort the list by arrival time
 * 
 * @author Michiele
 *
 */
public class ArrivalComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip one, Trip two) {
		// TODO Auto-generated method stub
		ZonedDateTime oneDateTime = one.getArrivalDateTime();
		ZonedDateTime twoDateTime = two.getArrivalDateTime();
		return oneDateTime.compareTo(twoDateTime);
	}

}
