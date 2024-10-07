package backend.travel.global.excepiton;

public class DuplicationTravelException extends DefaultTravelException{
    public DuplicationTravelException(String message) {
        super(message);
    }

    public DuplicationTravelException(Throwable cause) {
        super(cause);
    }

    public DuplicationTravelException(String message, Throwable cause) {
        super(message, cause);
    }
}
