package airplane;

public class Airplane {
	private int firstClassCapacity;
	private int coachClassCapacity;
	private String airplanemanufacture;
	private String model;
	/**
	 * Default constructor
	 * 
	 * Constructor without params. Requires object fields to be explicitly
	 * set using setter methods
	 * 
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */	
	public Airplane () {
		airplanemanufacture = "";
		model = "";
		firstClassCapacity = Integer.MAX_VALUE;
		coachClassCapacity = Integer.MAX_VALUE;
	}
	
	public Airplane(String airplanemanufacture, String model,int coachClassCapacity,
		      int firstClassCapacity) {
		this.firstClassCapacity = firstClassCapacity;
		this.coachClassCapacity = coachClassCapacity;
		this.airplanemanufacture = airplanemanufacture;
		this.model = model;
	}	
	
	public boolean isValid() {
		return true;
	}
	
	
	public void firstClassCapacity (int firstClassCapacity) {
			this.firstClassCapacity=firstClassCapacity;
	}
	
	public void coachClassCapacity (int coachClassCapacity) {
		this.coachClassCapacity=coachClassCapacity;
	}
	
	public void airplanemanufacture (String airplanemanufacture) {
		this.airplanemanufacture=airplanemanufacture;
	}
	
	public void model (String model) {
		this.model=model;
	}
}
