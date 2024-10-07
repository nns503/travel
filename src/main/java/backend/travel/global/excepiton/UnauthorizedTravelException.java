package backend.travel.global.excepiton;

public class UnauthorizedTravelException extends DefaultTravelException {
    public UnauthorizedTravelException(String message) {
        super(message);
    }

    public UnauthorizedTravelException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
