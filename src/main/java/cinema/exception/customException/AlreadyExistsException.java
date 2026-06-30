package cinema.exception.customException;

@SuppressWarnings("serial")
public class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException(String message) {
        super(message);
    }
}
