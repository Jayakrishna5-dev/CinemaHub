package cinema.exception.customException;

@SuppressWarnings("serial")
public class AccessDeniedException extends RuntimeException {
	public AccessDeniedException(String message) {
        super(message);
    }
}
