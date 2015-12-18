package myproject.ui;

public class UIFactory {
	private UIFactory() {}
	static private UI UI = new PopupUI();
	static public UI ui () {
		return UI;
	}
}
