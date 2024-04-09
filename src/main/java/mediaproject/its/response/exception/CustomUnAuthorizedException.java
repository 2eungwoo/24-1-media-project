package mediaproject.its.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mediaproject.its.response.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class CustomUnAuthorizedException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String message;

}
