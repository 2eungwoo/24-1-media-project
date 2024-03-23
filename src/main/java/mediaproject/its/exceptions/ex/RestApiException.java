package mediaproject.its.exceptions.ex;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mediaproject.its.exceptions.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException{
    private final ErrorCode errorCode;
}
