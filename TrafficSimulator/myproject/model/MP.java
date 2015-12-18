package myproject.model;

/**
 * Static class for model parameters.
 */

// hide MP behind an interface!
public class MP {

	public static double carLength = 10;
	/** Length of road to the UI */
	public static double roadLength = 200;
	/** Maximum car velocity, in meters/second */

	private static double[] actualRoadLength = { 200, 500 };
	/** Length of cars, in meters */
	private static double[] carSize = { 5, 10 };

	private static double[] maxVelocity = { 6, 10 };
	/** Number of rows in the simulation */
	private static int numberOfRows = 2;
	/** Number of columns in the simulation */
	private static int numberOfColumns = 3;
	/** Simulation time step */
	private static double timeStep = .1;
	/** Simulation run time */
	private static double runTime = 1000;
	/** Car spawn rate has min at 0 and max at 1 */
	private static double[] spawnRate = { 2, 25 }; // max is actually 25
	/** Car stop distance */
	private static double[] carStopDistance = { 0.5, 5 };
	/** Car brake distance */
	private static double[] carBrakeDistance = { 9, 10 };
	/** Traffic light green/red time */
	private static double[] trafficLightGreenTime = { 30, 180 };
	/** Traffic light yellow time */
	private static double[] trafficLightYellowTime = { 4, 15 };
	/** Is the grid alternating? */
	private static boolean alternatingRoads = true;

	private MP() {
	}

	public static void setNumberOfRows(int rowsIn) {

		numberOfRows = rowsIn;
	}

	public static int getNumberOfRows() {
		return numberOfRows;
	}

	public static void setNumberOfColumns(int columnsIn) {

		numberOfColumns = columnsIn;
	}

	public static int getNumberOfColumns() {
		return numberOfColumns;
	}

	public static void setTimeStep(double timeStepIn) {

		timeStep = timeStepIn;
	}

	public static double getTimeStep() {
		return timeStep;
	}

	public static void setRunTime(double runTimeIn) {

		runTime = runTimeIn;
	}

	public static double getRunTime() {
		return runTime;
	}

	/**
	 * Set the Model's car min and max sizes.
	 * 
	 * @param lengthIn
	 */
	public static void setCarSizeMinMax(double lengthIn[]) {
		if (lengthIn == null)
			throw new IllegalArgumentException();
		if (lengthIn.length < 2 || lengthIn.length > 2)
			throw new IllegalArgumentException();
		if (lengthIn[0] >= lengthIn[1])
			throw new IllegalArgumentException();
		carSize[0] = lengthIn[0];
		carSize[1] = lengthIn[1];
	}

	public static double[] getCarSizeMinMax() {
		return carSize;
	}

	public static void setVelocityCarMinMax(double velocityIn[]) {
		if (velocityIn == null)
			throw new IllegalArgumentException();
		if (velocityIn.length < 2 || velocityIn.length > 2)
			throw new IllegalArgumentException();
		if (velocityIn[0] >= velocityIn[1])
			throw new IllegalArgumentException();
		maxVelocity[0] = velocityIn[0];
		maxVelocity[1] = velocityIn[1];
	}

	public static double[] getVelocityCarMinMax() {
		return maxVelocity;
	}

	public static void setSpawnCarMinMax(double spawnIn[]) {
		if (spawnIn == null)
			throw new IllegalArgumentException();
		if (spawnIn.length < 2 || spawnIn.length > 2)
			throw new IllegalArgumentException();
		if (spawnIn[0] >= spawnIn[1])
			throw new IllegalArgumentException();
		spawnRate[0] = spawnIn[0];
		spawnRate[1] = spawnIn[1];
	}

	public static double[] getSpawnCarMinMax() {
		return spawnRate;
	}

	public static void setBrakeCarMinMax(double brakeIn[]) {
		if (brakeIn == null)
			throw new IllegalArgumentException();
		if (brakeIn.length < 2 || brakeIn.length > 2)
			throw new IllegalArgumentException();
		if (brakeIn[0] >= brakeIn[1])
			throw new IllegalArgumentException();
		carBrakeDistance[0] = brakeIn[0];
		carBrakeDistance[1] = brakeIn[1];
	}

	public static double[] getBrakeCarMinMax() {
		return carBrakeDistance;
	}

	public static void setStopCarMinMax(double stopIn[]) {
		if (stopIn == null)
			throw new IllegalArgumentException();
		if (stopIn.length < 2 || stopIn.length > 2)
			throw new IllegalArgumentException();
		if (stopIn[0] >= stopIn[1])
			throw new IllegalArgumentException();
		carStopDistance[0] = stopIn[0];
		carStopDistance[1] = stopIn[1];
	}

	public static double[] getStopCarMinMax() {
		return carStopDistance;
	}

	public static void setGreenMinMax(double greenTimeIn[]) {
		if (greenTimeIn == null)
			throw new IllegalArgumentException();
		if (greenTimeIn.length < 2 || greenTimeIn.length > 2)
			throw new IllegalArgumentException();
		if (greenTimeIn[0] >= greenTimeIn[1])
			throw new IllegalArgumentException();
		trafficLightGreenTime[0] = greenTimeIn[0];
		trafficLightGreenTime[1] = greenTimeIn[1];
	}

	public static double[] getGreenMinMax() {
		return trafficLightGreenTime;
	}

	public static void setYellowMinMax(double yellowTimeIn[]) {
		if (yellowTimeIn == null)
			throw new IllegalArgumentException();
		if (yellowTimeIn.length < 2 || yellowTimeIn.length > 2)
			throw new IllegalArgumentException();
		if (yellowTimeIn[0] >= yellowTimeIn[1])
			throw new IllegalArgumentException();
		trafficLightYellowTime[0] = yellowTimeIn[0];
		trafficLightYellowTime[1] = yellowTimeIn[1];
	}

	public static double[] getYellowMinMax() {
		return trafficLightYellowTime;
	}

	public static void setAlternatingRoads(boolean alternatingRoadDirectionsIn) {
		alternatingRoads = alternatingRoadDirectionsIn;
	}

	public static boolean getAlternatingRoads() {
		return alternatingRoads;
	}

	public static double[] getActualRoadLength() {
		return actualRoadLength;
	}

	public static void setActualRoadLength(double actualRoadLengthIn[]) {
		if (actualRoadLengthIn == null)
			throw new IllegalArgumentException();
		if (actualRoadLengthIn.length < 2 || actualRoadLengthIn.length > 2)
			throw new IllegalArgumentException();
		if (actualRoadLengthIn[0] >= actualRoadLengthIn[1])
			throw new IllegalArgumentException();
		actualRoadLength[0] = actualRoadLengthIn[0];
		actualRoadLength[1] = actualRoadLengthIn[1];
	}

	// create getters so we can hide the actual values!

}
