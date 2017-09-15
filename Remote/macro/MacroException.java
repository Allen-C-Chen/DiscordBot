package macro;
/**
 * Any exception relating to macro events i.e. color check throwing an exception if color doesn't match
 * @author Allen
 *
 */
public class MacroException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2385019576867194410L;

	public MacroException() {
	}

	public MacroException(String message) {
		super(message);
	}

	public MacroException(Throwable cause) {
		super(cause);
	}

	public MacroException(String message, Throwable cause) {
		super(message, cause);
	}

	public MacroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
