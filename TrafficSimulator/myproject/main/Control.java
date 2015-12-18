package myproject.main;

import myproject.model.MP;
import myproject.model.Model;
import myproject.ui.*;
import myproject.model.swing.*;

public class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int NUMSTATES = 10;
	private UIMenu[] menus;
	private int state;

	private UIFormTest numberTest;
	private UIFormTest stringTest;
	private UIFormTest doubleTest;

	private UI ui;

	private Model model;

	Control(UI ui) {
		this.ui = ui;

		menus = new UIMenu[NUMSTATES];
		state = START;
		addSTART(START);
		addEXIT(EXIT);

		UIFormTest yearTest = input -> {
			try {
				int i = Integer.parseInt(input);
				return i > 1800 && i < 5000;
			} catch (NumberFormatException e) {
				return false;
			}
		};
		numberTest = input -> {
			try {
				Integer.parseInt(input);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		};
		doubleTest = input -> {
			try {
				double i = Double.parseDouble(input);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		};

		stringTest = input -> !"".equals(input.trim());

	}

	void run() {
		try {
			while (state != EXITED) {
				ui.processMenu(menus[state]);
			}
		} catch (UIError e) {
			ui.displayError("UI closed");
		}
	}

	private void addSTART(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", () -> ui.displayError("Error!"));
		m.add("Run simulation",
				() -> {

					{
						Model mod = new Model(new SwingAnimatorBuilder(), MP
								.getNumberOfRows(), MP.getNumberOfColumns());
						mod.run(1000);
						mod.dispose();
					}

				});

		m.add("Change simulation parameters",
				() -> {
					double temp[] = new double[2];
					String[] result1;
					UIFormBuilder f;

					do {
						f = new UIFormBuilder();
						f.add("Simulation time step (seconds) [MIN>= .1] [MAX<= 1] ",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
					} while (Double.parseDouble(result1[0]) < 0.1
							|| Double.parseDouble(result1[0]) > 1);

					MP.setTimeStep(Double.parseDouble(result1[0]));

					do {
						f = new UIFormBuilder();
						f.add("Simulation run time (seconds) [MIN>= 200] [MAX<= 1000]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));

					} while (Double.parseDouble(result1[0]) < 200
							|| Double.parseDouble(result1[0]) > 1000);
					MP.setRunTime(Double.parseDouble(result1[0]));

					do {
						f = new UIFormBuilder();
						f.add("Number of Columns [MIN >=1] [MAX<= 4]",
								numberTest);
						result1 = ui.processForm(f.toUIForm(""));
						MP.setNumberOfColumns(Integer.parseInt(result1[0]));
					} while (Integer.parseInt(result1[0]) < 1
							|| Integer.parseInt(result1[0]) > 4);

					do {
						f = new UIFormBuilder();
						f.add("Number of Rows [MIN>= 1] [MAX<= 4]", numberTest);
						result1 = ui.processForm(f.toUIForm(""));

					} while (Integer.parseInt(result1[0]) < 1
							|| Integer.parseInt(result1[0]) > 4);

					MP.setNumberOfRows(Integer.parseInt(result1[0]));

					do {
						f = new UIFormBuilder();
						f.add("Alternating traffic Pattern [T/F] ", stringTest);
						result1 = ui.processForm(f.toUIForm(""));
						if (result1[0].equals("T"))
							MP.setAlternatingRoads(true);
						else if (result1[0].equals("F"))
							MP.setAlternatingRoads(false);
						else
							result1[0] = "Wrong";
					} while (result1[0].equals("Wrong"));

					do {
						f = new UIFormBuilder();
						f.add("Car entry rate Min (seconds/car) [Min>= 2] [Max<= 25]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Car entry rate Max (seconds/car) [Min>= 2][Max<= 25]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);
					} while (temp[0] < 2 || temp[1] > 25 && temp[0] > temp[1]);

					MP.setSpawnCarMinMax(temp);

					do {
						f = new UIFormBuilder();
						f.add("Car stop distance Min (meters) [Min >= .5][Max<= 5]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Car stop distance Max (meters)[Min >= .5][Max<= 5]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < .5 || temp[1] > 5 && temp[0] > temp[1]);

					MP.setStopCarMinMax(temp);

					do {
						f = new UIFormBuilder();
						f.add("Car maximum velocity Min (meters/seconds) [Min >= 5][Max <= 10]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Car maximum velocity Max (meters/seconds) [Min >= 5][Max <= 10]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < 5 || temp[1] > 10 && temp[0] > temp[1]);
					MP.setVelocityCarMinMax(temp);

					do {
						f = new UIFormBuilder();
						f.add("Car brake distance Min (meters)[Min>= 8][Max<= 10]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Car brake distance Max (meters)[Min>= 8][Max<= 10]:",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < 8 || temp[1] > 10 && temp[0] > temp[1]);

					MP.setBrakeCarMinMax(temp);
					do {
						f = new UIFormBuilder();
						f.add("Car length Min (meters)[Min>= 5][Max<= 15]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Car length Max (meters)[Min>= 5][Max<= 15]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < 5 || temp[1] > 15 && temp[0] > temp[1]);
					MP.setCarSizeMinMax(temp);
					do {
						f = new UIFormBuilder();
						f.add("Road segment length Min (meters)[Min>= 200][Max<= 500]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Road segment length Max (meters)[Min>= 200][Max<= 500]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < 200 || temp[1] > 500
							&& temp[0] > temp[1]);

					MP.setActualRoadLength(temp);

					do {
						f = new UIFormBuilder();
						f.add("Traffic light green time Min (seconds)[Min >= 30][Max <= 180]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Traffic light green time Max (seconds)[Min >= 30][Max <= 180]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < 30 || temp[1] > 180 && temp[0] > temp[1]);

					MP.setGreenMinMax(temp);
					do {
						f = new UIFormBuilder();
						f.add("Traffic light yellow time Min (seconds)[Min >= 4][Max <= 15]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[0] = Double.parseDouble(result1[0]);
						f = new UIFormBuilder();
						f.add("Traffic light yellow time Max (seconds)[Min >= 4][Max <= 15]",
								doubleTest);
						result1 = ui.processForm(f.toUIForm(""));
						temp[1] = Double.parseDouble(result1[0]);

					} while (temp[0] < 4 || temp[1] > 15 && temp[0] > temp[1]);

					MP.setYellowMinMax(temp);
				});

		m.add("Get simulation parameters",
				() -> {
					StringBuilder returnString = new StringBuilder();
					returnString.append("Simulation time step (seconds): ");
					returnString.append(MP.getTimeStep());
					returnString.append("\n");

					returnString.append("Simulation run time (seconds): ");
					returnString.append(MP.getRunTime());
					returnString.append("\n");

					returnString.append("Number of Columns: ");
					returnString.append(MP.getNumberOfColumns());
					returnString.append("\n");

					returnString.append("Number of Rows: ");
					returnString.append(MP.getNumberOfRows());
					returnString.append("\n");

					returnString.append("Alternating traffic Pattern: ");
					returnString.append(MP.getAlternatingRoads());
					returnString.append("\n");

					returnString
							.append("Car entry rate Min/Max (seconds/car): ");
					returnString.append(MP.getSpawnCarMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getSpawnCarMinMax()[1]);
					returnString.append("\n");

					returnString.append("Car stop distance Min/Max (meters): ");
					returnString.append(MP.getStopCarMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getStopCarMinMax()[1]);
					returnString.append("\n");

					returnString
							.append("Car maximum velocity Min/Max (meters/seconds): ");
					returnString.append(MP.getVelocityCarMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getVelocityCarMinMax()[1]);
					returnString.append("\n");

					returnString
							.append("Car brake distance Min/Max (meters): ");
					returnString.append(MP.getBrakeCarMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getBrakeCarMinMax()[1]);
					returnString.append("\n");

					returnString.append("Car stop distance Min/Max (meters): ");
					returnString.append(MP.getStopCarMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getStopCarMinMax()[1]);
					returnString.append("\n");

					returnString.append("Car length Min/Max (Meters): ");
					returnString.append(MP.getCarSizeMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getCarSizeMinMax()[1]);
					returnString.append("\n");

					returnString
							.append("Road segment length Min/Max (meters): ");
					returnString.append(MP.getActualRoadLength()[0]);
					returnString.append("/");
					returnString.append(MP.getActualRoadLength()[1]);
					returnString.append("\n");

					returnString
							.append("Traffic light green/red time Min/Max (seconds): ");
					returnString.append(MP.getGreenMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getGreenMinMax()[1]);
					returnString.append("\n");

					returnString
							.append("Traffic light yellow time Min/Max (seconds): ");
					returnString.append(MP.getYellowMinMax()[0]);
					returnString.append("/");
					returnString.append(MP.getYellowMinMax()[1]);
					returnString.append("\n");
					ui.displayMessage(returnString.toString());
				});

		m.add("Exit", () -> state = EXIT);

		menus[stateNum] = m.toUIMenu("Tom's Traffic Simulator");
	}

	private void addEXIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", () -> {
		});
		m.add("Yes", () -> state = EXITED);
		m.add("No", () -> state = START);

		menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	}

}
