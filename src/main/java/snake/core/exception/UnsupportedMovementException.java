package snake.core.exception;

public class UnsupportedMovementException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5151299090859771919L;

	public UnsupportedMovementException() {
		super();
	}

	public UnsupportedMovementException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsupportedMovementException(String message, Throwable cause) {
		super(message, cause);

	}

	public UnsupportedMovementException(String message) {
		super(message);
	}

	public UnsupportedMovementException(Throwable cause) {
		super(cause);
	}

}
