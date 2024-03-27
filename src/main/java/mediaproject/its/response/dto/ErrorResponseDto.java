package mediaproject.its.response.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@RequiredArgsConstructor
@Getter
public class ErrorResponseDto {
    private final boolean success = false;
    private final HttpStatus httpStatus;
    private final String message;
    private final int status;
}
