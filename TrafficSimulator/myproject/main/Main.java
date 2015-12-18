package myproject.main;

import myproject.ui.UI;

/**
 * A static class to demonstrate the visualization aspect of simulation.
 */
public class Main {
	private Main() {
	}

	public static void main(String[] args) {
		UI ui = null;
		ui = new myproject.ui.PopupUI();
		Control control = new Control(ui);
		control.run();
	}
}
