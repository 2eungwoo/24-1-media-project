package mediaproject.its.response.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@RequiredArgsConstructor
@Getter
public class CommonResponseDto<T> {
    private final HttpStatus statusCode;
    private final String message; //
    private final T data;

}

