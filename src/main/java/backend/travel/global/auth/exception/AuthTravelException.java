package backend.travel.global.auth.exception;

import backend.travel.global.excepiton.DefaultTravelException;

public class AuthTravelException extends DefaultTravelException {

    private static final String DEFAULT_MESSAGE = "알 수 없는 오류가 발생하였습니다.";

    public AuthTravelException(){
        super(DEFAULT_MESSAGE);
    }

    public AuthTravelException(String message) {
        super(message);
    }

    public AuthTravelException(Throwable cause) {
        super(cause);
    }

    public AuthTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
