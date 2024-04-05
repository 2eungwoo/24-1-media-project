package mediaproject.its.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{

    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"Not Exist User",404),
    USER_ALREADY_EXISTS_ERROR(HttpStatus.CONFLICT, "Already Exist User",409),
    USER_UNAUTHORIZED(HttpStatus.FORBIDDEN, "UnAuthorized User",501),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final int status;
}
