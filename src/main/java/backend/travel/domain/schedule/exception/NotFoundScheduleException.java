package backend.travel.domain.schedule.exception;

import backend.travel.global.excepiton.NotFoundTravelException;

public class NotFoundScheduleException extends NotFoundTravelException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 스케줄입니다.";

    public NotFoundScheduleException(){
        super(DEFAULT_MESSAGE);
    }
    public NotFoundScheduleException(String message) {
        super(message);
    }

    public NotFoundScheduleException(Throwable cause) {
        super(cause);
    }

    public NotFoundScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}
