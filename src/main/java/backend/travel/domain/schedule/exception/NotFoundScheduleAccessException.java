package backend.travel.domain.schedule.exception;

import backend.travel.global.excepiton.NotFoundTravelException;

public class NotFoundScheduleAccessException extends NotFoundTravelException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 스케줄 권한입니다.";

    public NotFoundScheduleAccessException(){
        super(DEFAULT_MESSAGE);
    }

    public NotFoundScheduleAccessException(String message) {
        super(message);
    }

    public NotFoundScheduleAccessException(Throwable cause) {
        super(cause);
    }

    public NotFoundScheduleAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
