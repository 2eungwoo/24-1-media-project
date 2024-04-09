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
    private final HttpStatus httpStatus;
    private final String message;
    private final ErrorCode errorCode;

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private final List<ValidationError> validErrors;
//
//    @Getter
//    @Builder
//    @RequiredArgsConstructor
//    public static class ValidationError {
//
//        private final String field;
//        private final String message;
//        // todo : method naming. of 말고 다른거로 하고싶음. of로 한다면 왜 of로 쓰는지 이유를 알고싶음
//        public static ValidationError of(final FieldError fieldError) {
//            return ValidationError.builder()
//                    .field(fieldError.getField())
//                    .message(fieldError.getDefaultMessage())
//                    .build();
//        }
//    }

}
