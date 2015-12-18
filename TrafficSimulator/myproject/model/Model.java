package myproject.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import myproject.util.Animator;

/**
 * An example to model for a simple visualization. The model contains roads
 * organized in a matrix. See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {
	private List<Agent> agents;
	private Animator animator;
	private boolean disposed;
	private double time;
	private Path rowArray[];
	private Path columnArray[];

	/**
	 * Creates a model to be visualized using the <code>builder</code>. If the
	 * builder is null, no visualization is performed. The number of
	 * <code>rows</code> and <code>columns</code> indicate the number of
	 * {@link Light}s, organized as a 2D matrix. These are separated and
	 * surrounded by horizontal and vertical {@link Road}s. For example, calling
	 * the constructor with 1 row and 2 columns generates a model of the form:
	 * 
	 * <pre>
	 *     |  |
	 *   --@--@--
	 *     |  |
	 * </pre>
	 * 
	 * where <code>@</code> is a {@link Light}, <code>|</code> is a vertical
	 * {@link Road} and <code>--</code> is a horizontal {@link Road}. Each road
	 * has one {@link Car}.
	 *
	 * <p>
	 * The {@link AnimatorBuilder} is used to set up an {@link Animator}.
	 * {@link AnimatorBuilder#getAnimator()} is registered as an observer of
	 * this model.
	 * <p>
	 */
	public Model(AnimatorBuilder builder, int rows, int columns) {
		if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
			throw new IllegalArgumentException();
		}
		if (builder == null) {
			builder = new NullAnimatorBuilder();
		}
		this.agents = new ArrayList<Agent>();
		setup(builder, rows, columns);
		this.animator = builder.getAnimator();
		super.addObserver(animator);
	}

	/**
	 * Run the simulation for <code>duration</code> model seconds.
	 */
	public void run(double duration) {
		if (disposed)
			throw new IllegalStateException();
		for (int i = 0; i < duration; i++, ++time) {

			// setup all agents within a
			for (Agent a : agents.toArray(new Agent[0])) {
				a.setup(time);
			}
			for (int r = rowArray.length - 1; r >= 0; r--) {
				rowArray[r].setup(i);
			}

			for (int c = columnArray.length - 1; c >= 0; c--) {
				columnArray[c].setup(i);
			}

			for (Agent a : agents.toArray(new Agent[0])) {
				a.commit();
			}

			for (int r = rowArray.length - 1; r >= 0; r--) {
				rowArray[r].commit();
			}

			for (int c = columnArray.length - 1; c >= 0; c--) {
				columnArray[c].commit();
			}

			super.setChanged();
			super.notifyObservers();
		}
	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		animator.dispose();
		disposed = true;
	}

	/**
	 * Construct the model, establishing correspondences with the visualizer.
	 */
	private void setup(AnimatorBuilder builder, int rows, int columns) {
		Light[][] intersections = new Light[rows][columns];
		Light[] rowtest = new Light[columns];
		rowArray = new Path[rows];
		Light[] columntest = new Light[rows];
		columnArray = new Path[columns];
		boolean toggleDir = false;
		Light[][] inverseIntersections = new Light[columns][rows];

		if (!MP.getAlternatingRoads()) {
			// create the rows
			for (int r = 0; r < rows; r++) {
				for (int i = 0; i < columns; i++) {
					intersections[r][i] = new Light();
					builder.addLight(intersections[r][i], r, i);
					agents.add(intersections[r][i]);
				}
				rowArray[r] = new Path(intersections[r], builder, true, r,
						false);
			}
			// end of creating rows
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++) {
					inverseIntersections[y][x] = new Light(intersections[x][y]);
					agents.add(inverseIntersections[y][x]);

				}
				columnArray[y] = new Path(inverseIntersections[y], builder,
						false, y, false);
			}
		} else {

			for (int r = 0; r < rows; r++) {
				for (int i = 0; i < columns; i++) {
					intersections[r][i] = new Light();
					builder.addLight(intersections[r][i], r, i);
					agents.add(intersections[r][i]);
				}
				rowArray[r] = new Path(intersections[r], builder, true, r,
						toggleDir);
				toggleDir = !toggleDir;
			}

			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++) {
					inverseIntersections[y][x] = new Light(intersections[x][y]);
					agents.add(inverseIntersections[y][x]);

				}
				columnArray[y] = new Path(inverseIntersections[y], builder,
						false, y, toggleDir);
				toggleDir = !toggleDir;
			}

		}

	}
}
