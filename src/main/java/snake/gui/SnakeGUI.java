package snake.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import snake.core.SnakeGame;

public class SnakeGUI {
	private static int GAME_HORIZONTAL = 34;
	private static int GAME_VERTICAL = 34;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame jframe = new JFrame("snake");
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe.setResizable(false);
			jframe.add(new SnakeCanvas(new SnakeGame(GAME_HORIZONTAL, GAME_VERTICAL)));
			jframe.pack();
			jframe.setVisible(true);
		});

	}
}
