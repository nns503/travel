package backend.travel.domain.user.exception;

public class NotFoundUsernameException extends NotFoundUserException {

    public NotFoundUsernameException() {
        super();
    }
    public NotFoundUsernameException(String message) {
        super(message);
    }

    public NotFoundUsernameException(Throwable cause) {
        super(cause);
    }

    public NotFoundUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
