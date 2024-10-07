package backend.travel.domain.user.exception;

import backend.travel.global.excepiton.DuplicationTravelException;

public class DuplicateUsernameException extends DuplicationTravelException {

    private static final String DEFAULT_MESSAGE = "이미 존재하는 아이디입니다.";

    public DuplicateUsernameException() {
        super(DEFAULT_MESSAGE);
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }

    public DuplicateUsernameException(Throwable cause) {
        super(cause);
    }

    public DuplicateUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
