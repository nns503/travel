package backend.travel.global.excepiton;

public class DefaultTravelException extends RuntimeException {
    public DefaultTravelException(String message) {
        super(message);
    }

    public DefaultTravelException(Throwable cause) {
        super(cause);
    }
    public DefaultTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
