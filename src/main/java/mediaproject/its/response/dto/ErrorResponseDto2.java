package mediaproject.its.response.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class ErrorResponseDto2<T> {
    private final boolean success = false;
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
    private final T data;
}