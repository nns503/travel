package backend.travel.global.excepiton;

public class NotFoundTravelException extends DefaultTravelException{
    public NotFoundTravelException(String message) {
        super(message);
    }

    public NotFoundTravelException(Throwable cause) {
        super(cause);
    }

    public NotFoundTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
