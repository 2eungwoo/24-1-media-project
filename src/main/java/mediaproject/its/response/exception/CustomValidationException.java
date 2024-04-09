package mediaproject.its.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mediaproject.its.response.dto.ErrorResponseDto;
import mediaproject.its.response.error.ErrorCode;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomValidationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
}