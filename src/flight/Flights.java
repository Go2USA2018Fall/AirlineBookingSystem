package flight;

import java.util.ArrayList;

import airport.Airport;

public class Flights extends ArrayList<Flight>{
	private static final long serialVersionUID = 1L;
	
	public void print() {
		for (Flight f: this) {
			f.print();
		}
	}
}
