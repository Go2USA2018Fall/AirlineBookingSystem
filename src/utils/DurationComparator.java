package utils;

import java.util.Comparator;

import trip.Trip;
/**
 * use to sort list by duration 
 * 
 * @author Michiele
 *
 */
public class DurationComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip one, Trip two) {
		double durationOne = one.duration();
		double durationTwo = two.duration();
		if (durationOne > durationTwo) return 1;
		if (durationOne < durationTwo) return -1;
		return 0;
	}

}
