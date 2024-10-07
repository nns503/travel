package backend.travel.domain.user.exception;

import backend.travel.global.excepiton.NotFoundTravelException;

public class NotFoundUserException extends NotFoundTravelException {

    private static final String DEFAULT_MESSAGE = "회원을 찾을 수 없습니다.";

    public NotFoundUserException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(Throwable cause) {
        super(cause);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
