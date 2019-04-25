/**
 * 
 */
package utils;

import java.util.Comparator;

import trip.Trip;

/**
 * use to sort trip list by price
 * 
 * @author Michiele
 *
 */
public class PriceComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip one, Trip two) {
		float onePrice = one.getPrice();
		float twoPrice = two.getPrice();
		
		if (onePrice == twoPrice) return 0;
		if (onePrice < twoPrice) return -1;
		return 1;
	}

}
