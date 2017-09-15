package macro;
/**
 * Used to describe macro exception as being thrown by the color event in which case the color did not match
 * @author Allen
 *
 */
public class ColorCheckFailed extends MacroException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9079115577074502191L;

	public ColorCheckFailed() {
		
	}

	public ColorCheckFailed(String message) {
		super(message);
	}

	public ColorCheckFailed(Throwable cause) {
		super(cause);
	}

	public ColorCheckFailed(String message, Throwable cause) {
		super(message, cause);
	}

	public ColorCheckFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
