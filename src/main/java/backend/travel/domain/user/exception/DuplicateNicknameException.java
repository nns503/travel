package backend.travel.domain.user.exception;

import backend.travel.global.excepiton.DuplicationTravelException;

public class DuplicateNicknameException extends DuplicationTravelException {

    private static final String DEFAULT_MESSAGE = "이미 존재하는 닉네임입니다.";

    public DuplicateNicknameException() {
        super(DEFAULT_MESSAGE);
    }

    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException(Throwable cause) {
        super(cause);
    }

    public DuplicateNicknameException(String message, Throwable cause) {
        super(message, cause);
    }
}
