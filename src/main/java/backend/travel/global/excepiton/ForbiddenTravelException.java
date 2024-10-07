package backend.travel.global.excepiton;

public class ForbiddenTravelException extends DefaultTravelException{
    public ForbiddenTravelException(String message) {
        super(message);
    }

    public ForbiddenTravelException(Throwable cause) {
        super(cause);
    }

    public ForbiddenTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
