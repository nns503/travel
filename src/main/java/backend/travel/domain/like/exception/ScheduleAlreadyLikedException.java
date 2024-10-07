package backend.travel.domain.like.exception;

import backend.travel.global.excepiton.DuplicationTravelException;

public class ScheduleAlreadyLikedException extends DuplicationTravelException {

    private static final String DEFAULT_MESSAGE = "이미 좋아요를 누른 게시글입니다.";

    public ScheduleAlreadyLikedException() {
        super(DEFAULT_MESSAGE);
    }

    public ScheduleAlreadyLikedException(String message) {
        super(message);
    }

    public ScheduleAlreadyLikedException(Throwable cause) {
        super(cause);
    }

    public ScheduleAlreadyLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
