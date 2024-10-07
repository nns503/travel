package backend.travel.domain.schedule.exception;

import backend.travel.global.excepiton.DuplicationTravelException;

public class ExistsScheduleAccessUserException extends DuplicationTravelException {

    private static final String DEFAULT_MESSAGE = "이미 권한이 부여된 유저입니다.";

    public ExistsScheduleAccessUserException(){
        super(DEFAULT_MESSAGE);
    }

    public ExistsScheduleAccessUserException(String message) {
        super(message);
    }

    public ExistsScheduleAccessUserException(Throwable cause) {
        super(cause);
    }

    public ExistsScheduleAccessUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
