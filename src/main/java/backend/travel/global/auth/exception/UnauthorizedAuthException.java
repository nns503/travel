package backend.travel.global.auth.exception;

import backend.travel.global.excepiton.UnauthorizedTravelException;

public class UnauthorizedAuthException extends UnauthorizedTravelException {

    private static final String DEFAULT_MESSAGE = "인증에 실패하였습니다.";

    public UnauthorizedAuthException(){
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedAuthException(String message) {
        super(message);
    }

    public UnauthorizedAuthException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
