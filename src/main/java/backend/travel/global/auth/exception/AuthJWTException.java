package backend.travel.global.auth.exception;

public class AuthJWTException extends AuthTravelException{

    private static final String DEFAULT_MESSAGE = "JWT 토큰이 잘못되었습니다.";

    public AuthJWTException() {
        super(DEFAULT_MESSAGE);
    }

    public AuthJWTException(String message) {
        super(message);
    }

    public AuthJWTException(Throwable cause) {
        super(cause);
    }

    public AuthJWTException(String message, Throwable cause) {
        super(message, cause);
    }
}
