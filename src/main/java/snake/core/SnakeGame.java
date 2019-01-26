package snake.core;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import snake.core.exception.UnsupportedMovementException;

public class SnakeGame {
	private int boardSizeHorizontal;
	private int boardSizeVertical;
	private LinkedList<Point> snake;

	private BoardState[][] board;
	private Random foodLocationGenerator;
	private Direction currentDirection;

	public SnakeGame(int boardSizeHorizontal, int boardSizeVertical) {
		this.boardSizeHorizontal = boardSizeHorizontal;
		this.boardSizeVertical = boardSizeVertical;
		this.board = new BoardState[boardSizeHorizontal][boardSizeVertical];
		snake = new LinkedList<Point>();
		foodLocationGenerator = new Random(System.currentTimeMillis());
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				this.board[i][j] = BoardState.EMPTY;

		snake.add(new Point(boardSizeHorizontal / 2, boardSizeVertical / 2));
		board[boardSizeHorizontal / 2][boardSizeVertical / 2] = BoardState.SNAKE;
		createNewFood();
	}

	private void createNewFood() {
		int foodX = foodLocationGenerator.nextInt(boardSizeHorizontal);
		int foodY = foodLocationGenerator.nextInt(boardSizeVertical);
		while (board[foodX][foodY] != BoardState.EMPTY) {
			foodX = foodLocationGenerator.nextInt(boardSizeHorizontal);
			if (board[foodX][foodY] != BoardState.EMPTY)
				foodY = foodLocationGenerator.nextInt(boardSizeVertical);
		}
		board[foodX][foodY] = BoardState.FOOD;
	}

	public boolean moveSnakeInHeadDirection() {
		return moveSnake(currentDirection);
	}

	/**
	 * This method will move snake in the direction its already moving
	 * 
	 * @return will return true if movement was successful, false return from this
	 *         method will be because of collision
	 */
	public boolean moveSnake(Direction directionOfMovement) {
		if (movementIsPossibleInDirection(directionOfMovement)) {
			Point head = snake.getFirst();
			Point nextHead = new Point();
			switch (directionOfMovement) {
			case DOWN:
				nextHead.setLocation(head.x, head.y + 1);
				break;
			case LEFT:
				nextHead.setLocation(head.x - 1, head.y);
				break;
			case RIGHT:
				nextHead.setLocation(head.x + 1, head.y);
				break;
			case UP:
				nextHead.setLocation(head.x, head.y - 1);
				break;
			default:
				throw new UnsupportedMovementException();
			}
			// collision cases
			if (nextHead.x < 0 || nextHead.x >= boardSizeHorizontal)
				return false;

			if (nextHead.y < 0 || nextHead.y >= boardSizeVertical)
				return false;

			if (board[nextHead.x][nextHead.y] == BoardState.SNAKE)
				return false;
			// collision cases end

			snake.addFirst(nextHead);

			if (board[nextHead.x][nextHead.y] != BoardState.FOOD) {
				Point last = snake.getLast();
				board[last.x][last.y] = BoardState.EMPTY;
				snake.removeLast();
			} else {
				createNewFood();
			}

			board[nextHead.x][nextHead.y] = BoardState.SNAKE;
			currentDirection = directionOfMovement;
			return true;
		}
		return true;

	}

	private boolean movementIsPossibleInDirection(Direction directionOfMovement) {
		if (currentDirection == Direction.UP && directionOfMovement == Direction.DOWN) {
			return false;
		}
		if (currentDirection == Direction.DOWN && directionOfMovement == Direction.UP) {
			return false;
		}
		if (currentDirection == Direction.LEFT && directionOfMovement == Direction.RIGHT) {
			return false;
		}
		if (currentDirection == Direction.RIGHT && directionOfMovement == Direction.LEFT) {
			return false;
		}
		return true;
	}

	public List<Point> getSnake() {
		return ImmutableList.copyOf(snake);
	}

	public BoardState[][] getBoardState() {
		BoardState[][] copy = new BoardState[board.length][];
		for (int i = 0; i < board.length; i++) {
			copy[i] = new BoardState[board[i].length];
			for (int j = 0; j < board[i].length; j++) {

				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	public int getBoardSizeHorizontal() {
		return boardSizeHorizontal;
	}

	public int getBoardSizeVertical() {
		return boardSizeVertical;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

}
