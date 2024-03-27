package mediaproject.its.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included",400),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists",404),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error",500),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final int status;
}