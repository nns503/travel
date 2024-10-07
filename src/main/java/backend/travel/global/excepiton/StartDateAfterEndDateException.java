package backend.travel.global.excepiton;

public class StartDateAfterEndDateException extends InvalidScheduleDateException {

    private static final String DEFAULT_MESSAGE = "시작 날짜는 반드시 종료 날짜 이전이어야 합니다.";

    public StartDateAfterEndDateException() {
        super(DEFAULT_MESSAGE);
    }

    public StartDateAfterEndDateException(String message) {
        super(message);
    }

    public StartDateAfterEndDateException(Throwable cause) {
        super(cause);
    }

    public StartDateAfterEndDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
