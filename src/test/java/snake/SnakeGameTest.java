package snake;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import snake.core.BoardState;
import snake.core.Direction;
import snake.core.SnakeGame;

public class SnakeGameTest {
	@Test
	public void initTest() {
		SnakeGame game = new SnakeGame(10, 10);
		BoardState[][] board = game.getBoardState();
		int foodCtr = 0;
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) {
				if (i == 5 && j == 5)
					Assert.assertEquals(BoardState.SNAKE, board[i][j]);
				else {
					Assert.assertTrue("All slots have to be empty except one for food",
							BoardState.EMPTY.equals(board[i][j]) || BoardState.FOOD.equals(board[i][j]));
					if (board[i][j] == BoardState.FOOD) {
						foodCtr += 1;
					}
				}
			}

		Assert.assertEquals("There has to be only one food",1, foodCtr);

	}

	@Test
	public void moveUpSimple() {
		SnakeGame game = new SnakeGame(10, 10);
		boolean result = game.moveSnake(Direction.UP);
		if (result == true) {
			Assert.assertEquals(5, game.getSnake().get(0).x);
			Assert.assertEquals(4, game.getSnake().get(0).y);
		} else {
			Assert.assertEquals(5, game.getSnake().get(0).x);
			Assert.assertEquals(5, game.getSnake().get(0).y);
		}
	}

	@Test
	public void moveDOWNSimple() {
		SnakeGame game = new SnakeGame(10, 10);
		boolean result = game.moveSnake(Direction.DOWN);
		if (result == true) {
			Assert.assertEquals(5, game.getSnake().get(0).x);
			Assert.assertEquals(6, game.getSnake().get(0).y);
		} else {
			Assert.assertEquals(5, game.getSnake().get(0).x);
			Assert.assertEquals(5, game.getSnake().get(0).y);
		}
	}

	@Test
	public void moveLEFTSimple() {
		SnakeGame game = new SnakeGame(10, 10);
		boolean result = game.moveSnake(Direction.LEFT);
		if (result == true) {
			Assert.assertEquals(4, game.getSnake().get(0).x);
			Assert.assertEquals(5, game.getSnake().get(0).y);
		} else {
			Assert.assertEquals(5, game.getSnake().get(0).x);
			Assert.assertEquals(5, game.getSnake().get(0).y);
		}
	}

	@Test
	public void moveRIGHTSimple() {
		SnakeGame game = new SnakeGame(10, 10);
		boolean result = game.moveSnake(Direction.RIGHT);
		if (result == true) {
			Assert.assertEquals(6, game.getSnake().get(0).x);
			Assert.assertEquals(5, game.getSnake().get(0).y);
		} else {
			Assert.assertEquals(5, game.getSnake().get(0).x);
			Assert.assertEquals(5, game.getSnake().get(0).y);
		}
	}

	@Test
	public void moveAndGrow()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(2, 3));
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.UP);
		game.moveSnake(Direction.UP);
		Assert.assertEquals(2, game.getSnake().size());
	}

	@Test
	public void tailInReverseDirectionTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(6, 5));
		game.moveSnake(Direction.RIGHT);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(7, 5));
		game.moveSnake(Direction.RIGHT);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(7, 4));
		game.moveSnake(Direction.UP);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(7, 3));
		game.moveSnake(Direction.UP);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(6, 3));
		game.moveSnake(Direction.LEFT);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(5, 3));
		game.moveSnake(Direction.LEFT);
		Assert.assertEquals(7, game.getSnake().size());
		assertSnakePostion(game, Arrays.asList(new Point(5, 3), new Point(6, 3), new Point(7, 3), new Point(7, 4),
				new Point(7, 5), new Point(6, 5), new Point(5, 5)));
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		game.moveSnake(Direction.UP);
		assertSnakePostion(game, Arrays.asList(new Point(5, 2), new Point(5, 3), new Point(6, 3), new Point(7, 3),
				new Point(7, 4), new Point(7, 5), new Point(6, 5)));
		game.moveSnake(Direction.RIGHT);
		assertSnakePostion(game, Arrays.asList(new Point(6, 2), new Point(5, 2), new Point(5, 3), new Point(6, 3),
				new Point(7, 3), new Point(7, 4), new Point(7, 5)));
		game.moveSnake(Direction.RIGHT);
		assertSnakePostion(game, Arrays.asList(new Point(7, 2), new Point(6, 2), new Point(5, 2), new Point(5, 3),
				new Point(6, 3), new Point(7, 3), new Point(7, 4)));
		game.moveSnake(Direction.RIGHT);
		assertSnakePostion(game, Arrays.asList(new Point(8, 2), new Point(7, 2), new Point(6, 2), new Point(5, 2),
				new Point(5, 3), new Point(6, 3), new Point(7, 3)));
		game.moveSnake(Direction.RIGHT);
		assertSnakePostion(game, Arrays.asList(new Point(9, 2), new Point(8, 2), new Point(7, 2), new Point(6, 2),
				new Point(5, 2), new Point(5, 3), new Point(6, 3)));
		game.moveSnake(Direction.UP);
		assertSnakePostion(game, Arrays.asList(new Point(9, 1), new Point(9, 2), new Point(8, 2), new Point(7, 2),
				new Point(6, 2), new Point(5, 2), new Point(5, 3), new Point(6, 3)));
		game.moveSnake(Direction.LEFT);
		assertSnakePostion(game, Arrays.asList(new Point(8, 1), new Point(9, 1), new Point(9, 2), new Point(8, 2),
				new Point(7, 2), new Point(6, 2), new Point(5, 2), new Point(5, 3)));
		game.moveSnake(Direction.LEFT);
		assertSnakePostion(game, Arrays.asList(new Point(7, 1), new Point(8, 1), new Point(9, 1), new Point(9, 2),
				new Point(8, 2), new Point(7, 2), new Point(6, 2), new Point(5, 2)));
		game.moveSnake(Direction.LEFT);
		assertSnakePostion(game, Arrays.asList(new Point(6, 1), new Point(7, 1), new Point(8, 1), new Point(9, 1),
				new Point(9, 2), new Point(8, 2), new Point(7, 2), new Point(6, 2)));
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.LEFT);
		assertSnakePostion(game, Arrays.asList(new Point(2, 1), new Point(3, 1), new Point(4, 1), new Point(5, 1),
				new Point(6, 1), new Point(7, 1), new Point(8, 1), new Point(9, 1)));

	}

	@Test
	public void dontMoveBackTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		game.moveSnake(Direction.RIGHT);
		game.moveSnake(Direction.LEFT);
		assertSnakePostion(game, Arrays.asList(new Point(6, 5)));
		game.moveSnake(Direction.UP);
		game.moveSnake(Direction.DOWN);
		assertSnakePostion(game, Arrays.asList(new Point(6, 4)));
		game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		game.moveSnake(Direction.LEFT);
		game.moveSnake(Direction.RIGHT);
		assertSnakePostion(game, Arrays.asList(new Point(4, 5)));
		game.moveSnake(Direction.DOWN);
		game.moveSnake(Direction.UP);
		assertSnakePostion(game, Arrays.asList(new Point(4, 6)));
	}

	@Test
	public void collisionWithItseflTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(6, 5));
		game.moveSnake(Direction.RIGHT);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(7, 5));
		game.moveSnake(Direction.RIGHT);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(7, 4));
		game.moveSnake(Direction.UP);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(7, 3));
		game.moveSnake(Direction.UP);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(6, 3));
		game.moveSnake(Direction.LEFT);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(5, 3));
		game.moveSnake(Direction.LEFT);
		Assert.assertEquals(7, game.getSnake().size());
		assertSnakePostion(game, Arrays.asList(new Point(5, 3), new Point(6, 3), new Point(7, 3), new Point(7, 4),
				new Point(7, 5), new Point(6, 5), new Point(5, 5)));
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.DOWN));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.RIGHT));
		Assert.assertFalse("Expected a Collison", game.moveSnake(Direction.RIGHT));
	}

	@Test
	public void collisionWithRightWallTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.RIGHT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.RIGHT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.RIGHT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.RIGHT));
		Assert.assertFalse("Expected a Collison", game.moveSnake(Direction.RIGHT));
	}

	@Test
	public void collisionWithLeftWallTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.LEFT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.LEFT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.LEFT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.LEFT));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.LEFT));
		Assert.assertFalse("Expected a Collison", game.moveSnake(Direction.LEFT));
	}

	@Test
	public void collisionWithTopWallTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.UP));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.UP));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.UP));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.UP));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.UP));
		Assert.assertFalse("Expected a Collison", game.moveSnake(Direction.UP));
	}

	@Test
	public void collisionWithBottomWallTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		SnakeGame game = new SnakeGame(10, 10);
		setGameState(game, new LinkedList<Point>(game.getSnake()), new Point(0, 0));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.DOWN));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.DOWN));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.DOWN));
		Assert.assertTrue("Unexpected Collison", game.moveSnake(Direction.DOWN));
		Assert.assertFalse("Expected a Collison", game.moveSnake(Direction.DOWN));
	}

	private void assertSnakePostion(SnakeGame game, List<Point> expectedSnake) {
		Iterator<Point> actualIter = game.getSnake().iterator();
		Iterator<Point> expiter = expectedSnake.iterator();
		while (actualIter.hasNext()) {
			if (!expiter.hasNext()) {
				Assert.fail("Size of snakes not same");
			}
			Point actual = actualIter.next();
			Point expected = expiter.next();
			if (!actual.equals(expected)) {
				Assert.fail("Snake mismatch at actual: " + actual + " expected: " + expected);
			}
		}
	}

	private void setGameState(SnakeGame game, LinkedList<Point> snake, Point food)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field snakeField = SnakeGame.class.getDeclaredField("snake");
		snakeField.setAccessible(true);
		snakeField.set(game, snake);
		BoardState[][] board = new BoardState[game.getBoardSizeHorizontal()][game.getBoardSizeVertical()];
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = BoardState.EMPTY;

		for (Point point : snake) {
			board[point.x][point.y] = BoardState.SNAKE;
		}
		board[food.x][food.y] = BoardState.FOOD;
		Field boardFiled = SnakeGame.class.getDeclaredField("board");
		boardFiled.setAccessible(true);
		boardFiled.set(game, board);
	}

}
