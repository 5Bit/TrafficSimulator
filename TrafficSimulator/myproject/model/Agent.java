package myproject.model;

/**
 * Interface for active model objects.
 */
public interface Agent {
	/**
	 * Setup() will setup stuff like new velocity for car
	 * 
	 * @param time
	 */
	public void setup(double time);

	/**
	 * Commit() will commit the changes, such as adding the velocity to the
	 * position
	 */
	public void commit();
}
