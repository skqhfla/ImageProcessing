package Main;

import Design.Design;

public class Main {
	static Design frame = null;
	
	public Design getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		frame = new Design();
		frame.setVisible(true);
	}
}