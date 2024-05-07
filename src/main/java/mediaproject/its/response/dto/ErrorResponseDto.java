package mediaproject.its.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mediaproject.its.response.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class ErrorResponseDto<T> {
    private final boolean success = false;
    private final int errorStatus;
    private final HttpStatus httpStatus;
    private final String message;
    private final ErrorCode errorCode;

}
