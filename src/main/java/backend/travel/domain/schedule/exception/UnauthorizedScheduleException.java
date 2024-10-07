package backend.travel.domain.schedule.exception;

import backend.travel.global.excepiton.ForbiddenTravelException;

public class UnauthorizedScheduleException extends ForbiddenTravelException {

    private static final String DEFAULT_MESSAGE = "해당 게시글에 접근할 수 없습니다.";

    public UnauthorizedScheduleException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedScheduleException(String message) {
        super(message);
    }

    public UnauthorizedScheduleException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}
