package snake.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.swing.Timer;

import snake.core.BoardState;
import snake.core.Direction;
import snake.core.SnakeGame;

public class SnakeCanvas extends Canvas implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SnakeGame game;
	// should be greater than 4
	private static final int BLOCK_SIZE = 20;

	private int gameSpeed = 10;// movement per second

	private static final int HEADER_SIZE = 20;

	private Timer gameTimer;

	private boolean gameOver;

	private static final Queue<Direction> MOVEMENT_QUEUE = new LinkedList<>();

	private static final Set<Integer> MODIFIERS_PRESSED = new HashSet<>();

	public SnakeCanvas(SnakeGame game) {
		super();
		this.game = game;
		this.setBackground(Color.WHITE);
		this.setSize(BLOCK_SIZE * game.getBoardSizeHorizontal(),
				BLOCK_SIZE * game.getBoardSizeVertical() + HEADER_SIZE);
		this.gameTimer = new Timer(1000 / gameSpeed, this);
		this.addKeyListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2D = (Graphics2D) g;
		if (!this.gameOver) {
			drawHeader(graphics2D);
			BoardState[][] board = game.getBoardState();
			Point head = game.getSnake().get(0);
			Direction currentDir = game.getCurrentDirection();
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] == BoardState.SNAKE) {
						graphics2D.setColor(Color.BLACK);
						if (i == head.x && j == head.y) {
							drawHead(graphics2D, currentDir, i, j);
						} else {
							graphics2D.drawRect(BLOCK_SIZE * i, HEADER_SIZE + BLOCK_SIZE * j, BLOCK_SIZE, BLOCK_SIZE);
							graphics2D.fillRect(BLOCK_SIZE * i + 2, HEADER_SIZE + BLOCK_SIZE * j + 2, BLOCK_SIZE - 4,
									BLOCK_SIZE - 4);
						}
					} else if (board[i][j] == BoardState.FOOD) {
						graphics2D.setColor(Color.RED);
						graphics2D.drawOval(BLOCK_SIZE * i, HEADER_SIZE + BLOCK_SIZE * j, BLOCK_SIZE, BLOCK_SIZE);
						graphics2D.fillOval(BLOCK_SIZE * i + 2, HEADER_SIZE + BLOCK_SIZE * j + 2, BLOCK_SIZE - 4,
								BLOCK_SIZE - 4);
					}
				}
			}
		} else {
			drawGameOver(g);
		}

	}

	private void drawHeader(Graphics2D graphics2d) {
		graphics2d.setColor(Color.GREEN);
		graphics2d.fillRect(0, 0, this.game.getBoardSizeHorizontal() * BLOCK_SIZE, HEADER_SIZE);
		Font font = new Font("TimesRoman", Font.PLAIN, 15);
		graphics2d.setFont(font);
		graphics2d.setColor(Color.BLACK);
		graphics2d.drawString("Press space to pause and +/- to increase decrease speed. Current Speed: " + gameSpeed,
				10, 15);
	}

	private void drawGameOver(Graphics g) {
		Font font = new Font("TimesRoman", Font.PLAIN, 20);
		g.setFont(font);

		String gameOverStr = "Game Over!!! Press Space to Start Over";
		g.drawString(gameOverStr,
				((this.game.getBoardSizeHorizontal() * BLOCK_SIZE) / 2) - ((10 * gameOverStr.length()) / 2),
				(this.game.getBoardSizeVertical() * BLOCK_SIZE) / 2);

	}

	private void drawHead(Graphics2D graphics2D, Direction currentDir, int i, int j) {
		if (currentDir == null || currentDir == Direction.UP) {
			graphics2D.drawPolygon(
					new int[] { (BLOCK_SIZE * i) + (BLOCK_SIZE / 2), (BLOCK_SIZE * i), (BLOCK_SIZE * i) + BLOCK_SIZE },
					new int[] { (BLOCK_SIZE * j) + HEADER_SIZE, (BLOCK_SIZE * j) + BLOCK_SIZE + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE + HEADER_SIZE },
					3);
			graphics2D.fillPolygon(
					new int[] { (BLOCK_SIZE * i) + (BLOCK_SIZE / 2), (BLOCK_SIZE * i) + 2,
							(BLOCK_SIZE * i) + BLOCK_SIZE - 2 },
					new int[] { (BLOCK_SIZE * j) + 2 + HEADER_SIZE, (BLOCK_SIZE * j) + BLOCK_SIZE - 2 + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE - 2 + HEADER_SIZE },
					3);
		} else if (currentDir == Direction.DOWN) {
			graphics2D.drawPolygon(
					new int[] { BLOCK_SIZE * i, (BLOCK_SIZE * i) + BLOCK_SIZE, (BLOCK_SIZE * i) + (BLOCK_SIZE / 2) },
					new int[] { (BLOCK_SIZE * j) + HEADER_SIZE, (BLOCK_SIZE * j) + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE + HEADER_SIZE },
					3);
			graphics2D.fillPolygon(
					new int[] { BLOCK_SIZE * i + 2, (BLOCK_SIZE * i) + BLOCK_SIZE - 2,
							(BLOCK_SIZE * i) + (BLOCK_SIZE / 2) },
					new int[] { (BLOCK_SIZE * j) + 2 + HEADER_SIZE, (BLOCK_SIZE * j) + 2 + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE - 2 + HEADER_SIZE },
					3);
		} else if (currentDir == Direction.LEFT) {
			graphics2D.drawPolygon(
					new int[] { BLOCK_SIZE * i, (BLOCK_SIZE * i) + BLOCK_SIZE, (BLOCK_SIZE * i) + BLOCK_SIZE },
					new int[] { (BLOCK_SIZE * j) + (BLOCK_SIZE / 2) + HEADER_SIZE, (BLOCK_SIZE * j) + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE + HEADER_SIZE },
					3);
			graphics2D.fillPolygon(
					new int[] { BLOCK_SIZE * i + 2, (BLOCK_SIZE * i) + BLOCK_SIZE - 2,
							(BLOCK_SIZE * i) + BLOCK_SIZE - 2 },
					new int[] { (BLOCK_SIZE * j) + (BLOCK_SIZE / 2) + HEADER_SIZE, (BLOCK_SIZE * j) + 2 + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE - 2 + HEADER_SIZE },
					3);
		} else {
			graphics2D.drawPolygon(new int[] { BLOCK_SIZE * i, (BLOCK_SIZE * i) + BLOCK_SIZE, BLOCK_SIZE * i },
					new int[] { (BLOCK_SIZE * j) + HEADER_SIZE, (BLOCK_SIZE * j) + (BLOCK_SIZE / 2) + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE + HEADER_SIZE },
					3);
			graphics2D.fillPolygon(
					new int[] { BLOCK_SIZE * i + 2, (BLOCK_SIZE * i) + BLOCK_SIZE - 2, BLOCK_SIZE * i + 2 },
					new int[] { (BLOCK_SIZE * j) + 2 + HEADER_SIZE, (BLOCK_SIZE * j) + (BLOCK_SIZE / 2) + HEADER_SIZE,
							(BLOCK_SIZE * j) + BLOCK_SIZE - 2 + HEADER_SIZE },
					3);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean collided = false;
		if (MOVEMENT_QUEUE.isEmpty()) {
			collided = !this.game.moveSnakeInHeadDirection();
		} else {
			collided = !this.game.moveSnake(MOVEMENT_QUEUE.poll());
		}
		if (collided) {
			this.gameTimer.stop();
			this.gameOver = true;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean directionKeyPressed = false;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			MOVEMENT_QUEUE.offer(Direction.UP);
			directionKeyPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			MOVEMENT_QUEUE.offer(Direction.DOWN);
			directionKeyPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			MOVEMENT_QUEUE.offer(Direction.LEFT);
			directionKeyPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			MOVEMENT_QUEUE.offer(Direction.RIGHT);
			directionKeyPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			if (this.gameOver) {
				this.game = new SnakeGame(this.game.getBoardSizeHorizontal(), this.game.getBoardSizeVertical());
				this.gameOver = false;
				repaint();
			} else {
				boolean timerRunning = this.gameTimer.isRunning();
				if (timerRunning)
					this.gameTimer.stop();
				else if (this.game.getCurrentDirection() != null)
					this.gameTimer.start();
			}
			break;
		case KeyEvent.VK_SHIFT:
			MODIFIERS_PRESSED.add(Integer.valueOf(KeyEvent.VK_SHIFT));
			break;
		case KeyEvent.VK_EQUALS:
			if (!this.gameOver && MODIFIERS_PRESSED.contains(Integer.valueOf(KeyEvent.VK_SHIFT))) {
				changeGameSpeed(true);
				repaint();
			}
			break;
		case KeyEvent.VK_MINUS:
			changeGameSpeed(false);
			repaint();
			break;
		}

		if (directionKeyPressed && !this.gameOver && !this.gameTimer.isRunning()) {
			this.gameTimer.start();
		}
	}

	private void changeGameSpeed(boolean increament) {
		boolean wasRunning = false;
		if (this.gameTimer.isRunning()) {
			wasRunning = true;
			this.gameTimer.stop();
		}
		if(increament)
		 this.gameSpeed++;
		else
		 this.gameSpeed--;
		this.gameTimer = new Timer(1000 / gameSpeed, this);
		if (wasRunning)
			this.gameTimer.start();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			MODIFIERS_PRESSED.remove(Integer.valueOf(KeyEvent.VK_SHIFT));
		}
	}

}
