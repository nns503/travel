package backend.travel.domain.cost.exception;

import backend.travel.global.excepiton.NotFoundTravelException;

public class NotFoundCostException extends NotFoundTravelException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 내역입니다.";

    public NotFoundCostException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundCostException(String message) {
        super(message);
    }

    public NotFoundCostException(Throwable cause) {
        super(cause);
    }

    public NotFoundCostException(String message, Throwable cause) {
        super(message, cause);
    }
}
