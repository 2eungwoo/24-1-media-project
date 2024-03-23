package mediaproject.its.response.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
public class CommonResponseDto<T> {
    private HttpStatus statusCode;
    private String message; //
    private T data;
}

