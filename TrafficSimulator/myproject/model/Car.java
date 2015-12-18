package myproject.model;

import java.util.List;
import java.util.Random;

public class Car implements Agent {

	Car(Path inPath) {
		if (inPath == null)
			throw new IllegalArgumentException();
		pathOn = inPath;
		Random randNum = new Random();
		carSize = ModelUtilities.randomizer(MP.getCarSizeMinMax());
		maxVelocity = ModelUtilities.randomizer(MP.getVelocityCarMinMax());
		this.brakeDistance = ModelUtilities.randomizer(MP.getBrakeCarMinMax());
		this.stopDistance = ModelUtilities.randomizer(MP.getStopCarMinMax());

	}

	int roadOn = 0;
	Path pathOn = null;
	private double carLength = MP.carLength;
	private double carSize;
	public boolean killme = false;
	private double position = 0;
	private double maxVelocity;
	private double currentVelocity = maxVelocity;
	private java.awt.Color color = new java.awt.Color((int) Math.ceil(Math
			.random() * 255), (int) Math.ceil(Math.random() * 255), 0);
	private double brakeDistance;
	private double stopDistance;

	public double getPosition() {
		return position;
	}

	public java.awt.Color getColor() {
		return color;
	}

	public boolean isEqual(Object thatObject) {
		if (this == thatObject)
			return true;
		if (!(thatObject instanceof Car))
			return false;
		Car that = (Car) thatObject;
		if (this.position != that.position)
			return false;
		if (this.currentVelocity != that.currentVelocity)
			return false;
		if (this.maxVelocity != that.maxVelocity)
			return false;
		if (!this.color.equals(that.color))
			return false;
		if (this.carLength != that.carLength)
			return false;
		if (this.roadOn != that.roadOn)
			return false;
		if (this.brakeDistance != that.brakeDistance)
			return false;
		if (this.stopDistance != that.stopDistance)
			return false;
		if (this.carSize != that.carSize)
			return false;
		return true;
	}

	public double getLength() {
		return carSize;
	}

	public boolean atLight() {
		if ((position + this.getLength() + stopDistance >= (pathOn.getRoad(
				roadOn).getLength() * pathOn.getRoad(roadOn)
				.calculateActualToRoadRatio()) - this.getLength())
				&& (this.currentVelocity == 0))
			return true;
		else
			return false;
	}

	public double getCurrentVelocity() {
		return currentVelocity;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public double getStopDistance() {
		return stopDistance;
	}

	public void toNextRoad(int roadNum) {
		roadOn = roadNum;
		position = 0;
	}

	/** for detecting and slowing down against cars in front */
	private void slowDownOrSpeedUp() {
		List<Car> array = pathOn.getRoad(roadOn).getCars();
		int thisCarIndex = array.indexOf(this);
		int listSize = array.size();

		if (listSize - 1 >= thisCarIndex) // if there is a car in front of me
		{
			if (thisCarIndex > 0) {
				Car otherCar = array.get(thisCarIndex - 1);
				if (this.position + this.getLength()
						- (otherCar.getPosition() + otherCar.getLength()) <= brakeDistance
						+ stopDistance) // if the other car is in brake distance
										// with the current velocity
				{
					currentVelocity = (maxVelocity / (brakeDistance - stopDistance))
							* ((otherCar.getPosition() - otherCar.getLength() - (position + stopDistance)) - stopDistance); // decrease
																															// current
																															// velocity
					currentVelocity = Math.max(0.0, currentVelocity);
					currentVelocity = Math.min(maxVelocity, currentVelocity);
				} else {

					if (this.position + this.carLength
							- (otherCar.getPosition() + otherCar.getLength()) > brakeDistance
							+ stopDistance) // if this car goes as fast as it
											// can and is still short of the
											// next car
					{
						currentVelocity = maxVelocity;
					}
				}
			}

		}

	}

	private void slowDownToStop() {
		if (pathOn.getRoad(roadOn).getCars().indexOf(this) == 0) {
			if ((position + currentVelocity) >= (pathOn.getRoad(roadOn)
					.getLength() * pathOn.getRoad(roadOn)
					.calculateActualToRoadRatio())
					- this.getLength())
				currentVelocity = 0;
			else if (position + this.carLength + stopDistance >= pathOn
					.getRoad(roadOn).getLength()
					* pathOn.getRoad(roadOn).calculateActualToRoadRatio()
					- this.getLength())
				currentVelocity = ((brakeDistance + stopDistance) / maxVelocity);
			else
				currentVelocity = maxVelocity;
		}
	}

	public void resetVelocity() {
		currentVelocity = maxVelocity;
	}

	public void setup(double time) {
		slowDownOrSpeedUp();
		slowDownToStop();
	}

	public void commit() {
		position += currentVelocity;
	}

}