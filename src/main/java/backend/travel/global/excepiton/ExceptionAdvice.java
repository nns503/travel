package backend.travel.global.excepiton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultTravelException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(DefaultTravelException e) {
        return new ResponseEntity<>(
                new ErrorCode("500_INTERNAL_SERVER_ERROR", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BadRequestTravelException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(BadRequestTravelException e) {
        return new ResponseEntity<>(
                new ErrorCode("400_BAD_REQUEST", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedTravelException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(UnauthorizedTravelException e) {
        return new ResponseEntity<>(
                new ErrorCode("401_UNAUTHORIZED", e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ForbiddenTravelException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(ForbiddenTravelException e) {
        return new ResponseEntity<>(
                new ErrorCode("403_Forbidden", e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NotFoundTravelException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(NotFoundTravelException e) {
        return new ResponseEntity<>(
                new ErrorCode("404_NOT_FOUND", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicationTravelException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(DuplicationTravelException e) {
        return new ResponseEntity<>(
                new ErrorCode("409_CONFLICT", e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorKey = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
            errors.put(errorKey, error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
