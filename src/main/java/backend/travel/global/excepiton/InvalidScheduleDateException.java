package backend.travel.global.excepiton;

public class InvalidScheduleDateException extends BadRequestTravelException {

    private static final String DEFAULT_MESSAGE = "날짜가 잘못 설정 되었습니다.";

    public InvalidScheduleDateException(){
        super(DEFAULT_MESSAGE);
    }

    public InvalidScheduleDateException(String message) {
        super(message);
    }

    public InvalidScheduleDateException(Throwable cause) {
        super(cause);
    }

    public InvalidScheduleDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
