package myproject.model;

import java.util.List;

public class Path implements Agent {
	private Road roadArray[] = null;
	private Boolean isHorizontal;
	private Light lightArray[] = null;
	private int pathNumber = 0;
	private boolean isRightToLeft = false;
	private AnimatorBuilder bldr = null;
	private double spawnrate;

	/**
	 * Constructs a path when given appropriate information
	 * 
	 * @param lights
	 * @param builder
	 * @param horizontal
	 * @param pathNum
	 * @param standardDirection
	 */
	Path(Light[] lights, AnimatorBuilder builder, boolean horizontal,
			int pathNum, boolean rightToLeft) {
		if (lights.length == 0)
			throw new IllegalArgumentException();
		if (builder == null)
			throw new IllegalArgumentException();

		// calculate spawnrate
		this.spawnrate = ModelUtilities.randomizer(MP.getSpawnCarMinMax());

		pathNumber = pathNum;
		isHorizontal = horizontal;
		lightArray = lights;

		isRightToLeft = rightToLeft;
		CarSpawner spawner = null;
		bldr = builder;

		// assembles road array
		if (isHorizontal) {
			roadArray = new Road[MP.getNumberOfColumns() + 1];
			for (int i = 0; i <= MP.getNumberOfColumns(); i++) {
				roadArray[i] = new Road(MP.roadLength);
			}

			if (!isRightToLeft) {

				// draw them
				for (int i = 0; i <= MP.getNumberOfColumns(); i++) {
					builder.addHorizontalRoad(roadArray[i], pathNumber, i,
							isRightToLeft);
				}
			} else {
				Light[] templights = new Light[lights.length];

				// flip lights
				for (int i = 0; i < lights.length; i++) {
					templights[(lights.length - 1) - i] = lights[i];
				}
				lightArray = templights;

				// draw them
				for (int i = MP.getNumberOfColumns(); i >= 0; i--) {
					builder.addHorizontalRoad(roadArray[i], pathNumber,
							MP.getNumberOfColumns() - i, isRightToLeft);
				}
			}

		} else {
			roadArray = new Road[MP.getNumberOfRows() + 1];
			for (int i = MP.getNumberOfRows(); i >= 0; i--) // array created in
															// reverse
			{
				roadArray[i] = new Road(MP.roadLength);
			}
			if (!isRightToLeft) {
				for (int i = 0; i <= MP.getNumberOfRows(); i++) {
					builder.addVerticalRoad(roadArray[i], i, pathNumber,
							isRightToLeft);
				}
			} else {
				Light[] templights = new Light[lights.length];
				// flip lights
				for (int i = 0; i < lights.length; i++) {
					templights[(lights.length - 1) - i] = lights[i];
				}
				lightArray = templights;

				for (int i = MP.getNumberOfRows(); i >= 0; i--) {
					builder.addVerticalRoad(roadArray[i], MP.getNumberOfRows()
							- i, pathNumber, isRightToLeft);
				}
			}
		}
		// done drawing and assembling roads

		// Set up spawner
		spawner = new CarSpawner(this);

	}

	public Road getRoad(int roadNum) {
		return roadArray[roadNum];
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setup(double time) {
		// first update cars within the roads
		for (int i = (roadArray.length - 1); i >= 0; i--) {
			// update cars within the roads
			List<Car> cars = roadArray[i].getCars();
			int listCounter = cars.size();
			if (cars.size() != 0) {
				for (int j = listCounter; j > 0; j--) {
					if (cars.get(j - 1).atLight()) {
						if (lightArray.length <= i) {
							Car tempkill = cars.remove(j - 1);// kill said car
						} else if (lightArray[i].getState() == "Green") {

							if (i <= roadArray.length - 1) {
								if (!roadArray[i + 1].isFull()) {
									Car tempcar = roadArray[i].remove(cars
											.get(j - 1));
									tempcar.resetVelocity();
									tempcar.toNextRoad(i + 1);
									roadArray[i + 1].accept(tempcar);
								}
							}
						}
					} else
						cars.get(j - 1).setup(time);
				}
			}
		}

		if (time % this.spawnrate == 0) {
			Car newCar = new Car(this);
			if ((!roadArray[0].isFull()))
				roadArray[0].accept(newCar);
		}
	}

	public void commit() {
		// run commit for all roads and cars within the roads.

		for (int i = (roadArray.length - 1); i >= 0; i--) {
			List<Car> cars = roadArray[i].getCars();
			int listCounter = cars.size();
			if (cars.size() != 0) {
				for (int j = listCounter - 1; j >= 0; j--) {
					// run all car commits within here!
					cars.get(j).commit();
				}
			}
		}

	}

	/**
	 * This class is responsible for creating cars. Makes sense that there will
	 * only be one.
	 * 
	 * @author K.Field
	 *
	 */
	private static class CarSpawner {

		private Path path = null;

		public CarSpawner(Path myPath) {
			if(myPath == null) throw new IllegalArgumentException();
			path = myPath;
		}

		/**
		 * In charge of spawning cars for the path with this CMD
		 * 
		 * @return
		 */
		public Car spawnCar() {
			return new Car(path);
		}

	}
}