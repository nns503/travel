package backend.travel.domain.region.exception;

import backend.travel.global.excepiton.NotFoundTravelException;

public class NotFoundRegionException extends NotFoundTravelException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 일정입니다.";

    public NotFoundRegionException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundRegionException(String message) {
        super(message);
    }

    public NotFoundRegionException(Throwable cause) {
        super(cause);
    }

    public NotFoundRegionException(String message, Throwable cause) {
        super(message, cause);
    }
}
