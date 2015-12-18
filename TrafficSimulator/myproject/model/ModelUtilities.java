package myproject.model;

import java.util.Random;

public class ModelUtilities {

	/**
	 * Used to create randomized variables within the data given.
	 * 
	 * @param randomizedArray
	 * @return
	 */
	public static double randomizer(double[] randomizedArray) {
		if (randomizedArray.length != 2)
			throw new IllegalArgumentException();
		Random randNum = new Random();
		double temp = randomizedArray[1] - randomizedArray[0];

		double temp2 = ((randNum.nextDouble() * temp) + randomizedArray[0]);

		int temp3 = (int) temp2;
		return temp3;
	}
}
