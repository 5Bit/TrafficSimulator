package myproject.model;

/**
 * A light has a boolean state.
 */
public class Light implements Agent {
	private int color = 0; // 0 = red, 1 = yellow, 2 = green
	private boolean isIncreasing;
	private double yellowTime;
	private double redGreenTime;
	private boolean isCopy;

	/**
	 * for making mainLights
	 */
	Light() {
		isIncreasing = true;

		redGreenTime = ModelUtilities.randomizer(MP.getGreenMinMax());
		yellowTime = ModelUtilities.randomizer(MP.getYellowMinMax());
		isCopy = false;
	}

	/**
	 * For making lights that depend on other lights. Use for the vertical roads
	 * 
	 * @param otherLight
	 */
	Light(Light otherLight) {
		if (otherLight.getState() == "Red")
			this.color = 2;
		else if (otherLight.getState() == "Yellow")
			this.color = 1;
		else if (otherLight.getState() == "Green")
			this.color = 0;

		this.isIncreasing = !otherLight.isIncreasing;
		this.redGreenTime = otherLight.redGreenTime;
		this.yellowTime = otherLight.yellowTime;
		isCopy = true;

	} // Created only by this package

	/**
	 * get's the current color of the light and returns the color name as
	 * string. "Red" = 0 "Yellow" = 1 "Green" = 2 Returns "ERROR" if the color
	 * isn't one of the three.
	 * 
	 * @return
	 */
	public String getState() {
		if (color < 0 || color > 2)
			return "ERROR";

		if (color == 0)
			return "Red";
		if (color == 1)
			return "Yellow";
		if (color == 2)
			return "Green";

		return "ERROR";
	}
	
	public boolean isEqual(Object thatObject) {
		if (this == thatObject)
			return true;
		if (!(thatObject instanceof Car))
			return false;
		Light that = (Light) thatObject;
		if (this.isIncreasing != that.isIncreasing)
			return false;
		if (this.yellowTime != that.yellowTime)
			return false;
		if (this.color != that.color)
			return false;
		if (this.redGreenTime != that.redGreenTime)
			return false;
		if(this.isCopy != that.isCopy)
			return false;
		return true;
	}


	/**
	 * alternates the light according to the current time and the amount of time
	 * each setting is allowed.
	 */
	public void setup(double time) {

		if (time % yellowTime == 0)
			if (color == 1) // if yellow
			{
				if (isIncreasing)
					color++;
				else
					color--;
			}
		if (time % redGreenTime == 0) // if time to switch
		{
			if (color == 0)// if green
			{
				isIncreasing = true;
				color++;
			} else // if red
			{
				isIncreasing = false;
				color--;
			}

		}
	}

	public void commit() {

	}
}
