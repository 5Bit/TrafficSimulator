package myproject.model;

import java.util.List;
import java.util.ArrayList;

/**
 * A road holds cars.
 */
public class Road {
	Road(double inRoadLength) {

		roadLength = ModelUtilities.randomizer(MP.getActualRoadLength());
		actualRoadLength = inRoadLength;
	}

	private double roadLength;
	private double actualRoadLength;
	private List<Car> cars = new ArrayList<Car>();

	/**
	 * Adds up the length of each car and it's stop distance, and returns the
	 * difference of that against the roadLength
	 * 
	 * @return
	 */

	public double calculateActualToRoadRatio() {
		return actualRoadLength / roadLength;
	}

	public double spaceLeft() {
		double roadAmountLeft = roadLength;

		if (cars.isEmpty())
			return roadLength;

		int numOfCars = cars.size();
		return cars.get(numOfCars - 1).getPosition();
	}

	public boolean isFull() {
		double roadLeft = spaceLeft();

		if (roadLeft >= MP.getCarSizeMinMax()[1])
			return false;
		else
			return true;
	}
	
	public boolean isEqual(Object thatObject) {
		if (this == thatObject)
			return true;
		if (!(thatObject instanceof Car))
			return false;
		Road that = (Road) thatObject;
		if (this.roadLength != that.roadLength)
			return false;
		if (this.actualRoadLength != that.actualRoadLength)
			return false;
		if (!this.cars.equals(that.cars))
			return false;
		return true;
	}

	public double getLength() {
		return roadLength;
	}

	/**
	 * If the road has room, accept's the car - if not, returns false
	 * 
	 * @param d
	 */
	public boolean accept(Car d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}

		if (isFull())
			return false;
		else
			cars.add(d);
		return true;
	}

	/**
	 * get's the index of the car, and removes it from cars, returning the car
	 * removed Throw's IllegalArgumentException if c is null
	 * 
	 * @param c
	 * @return
	 */
	public Car remove(Car c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		int carIndex = cars.indexOf(c);
		return cars.remove(carIndex);

	}

	public List<Car> getCars() {
		return cars;
	}
}
