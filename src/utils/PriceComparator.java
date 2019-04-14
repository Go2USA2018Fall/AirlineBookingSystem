/**
 * 
 */
package utils;

import java.util.Comparator;

import trip.Trip;

/**
 * @author user
 *
 */
public class PriceComparator implements Comparator<Trip> {

	@Override
	public int compare(Trip one, Trip two) {
		// TODO Auto-generated method stub
		float onePrice = one.getPrice();
		float twoPrice = two.getPrice();
		
		if (onePrice == twoPrice) return 0;
		if (onePrice < twoPrice) return -1;
		return 1;
	}

}
