package mediaproject.its.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mediaproject.its.response.error.ErrorCode;

@Getter
public class CustomRestApiException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomRestApiException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
