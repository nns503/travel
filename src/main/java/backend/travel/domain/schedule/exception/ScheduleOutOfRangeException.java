package backend.travel.domain.schedule.exception;

import backend.travel.global.excepiton.InvalidScheduleDateException;

public class ScheduleOutOfRangeException extends InvalidScheduleDateException {

    private static final String DEFAULT_MESSAGE = "스케줄 내에 일정으로 구현해야합니다";

    public ScheduleOutOfRangeException() {
        super(DEFAULT_MESSAGE);
    }

    public ScheduleOutOfRangeException(String message) {
        super(message);
    }

    public ScheduleOutOfRangeException(Throwable cause) {
        super(cause);
    }

    public ScheduleOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
