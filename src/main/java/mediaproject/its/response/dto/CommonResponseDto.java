package mediaproject.its.response.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
public class CommonResponseDto<T> {
    private final boolean success = true;
    private HttpStatus statusCode;
    private String message; //
    private T data;
}

