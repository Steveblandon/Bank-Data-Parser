package proj.core;

import java.awt.EventQueue;

import proj.core.gui.GUI;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI gui = new GUI();
					gui.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
