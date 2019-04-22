package utils;

import java.util.Comparator;

import trip.Trip;

public class DurationComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip one, Trip two) {
		// TODO Auto-generated method stub
		double durationOne = one.duration();
		double durationTwo = two.duration();
		if (durationOne > durationTwo) return 1;
		if (durationOne < durationTwo) return -1;
		return 0;
	}

}
