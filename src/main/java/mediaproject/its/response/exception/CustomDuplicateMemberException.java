package mediaproject.its.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mediaproject.its.response.error.ErrorCode;
import org.springframework.dao.DuplicateKeyException;

@Getter
public class CustomDuplicateMemberException extends DuplicateKeyException {

    private final ErrorCode errorCode;

    public CustomDuplicateMemberException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}