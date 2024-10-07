package backend.travel.global.excepiton;

public class BadRequestTravelException extends DefaultTravelException{
    public BadRequestTravelException(String message) {
        super(message);
    }

    public BadRequestTravelException(Throwable cause) {
        super(cause);
    }

    public BadRequestTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
