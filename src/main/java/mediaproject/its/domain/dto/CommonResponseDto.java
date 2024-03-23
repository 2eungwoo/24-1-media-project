package mediaproject.its.domain.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class CommonResponseDto<T> {
    private HttpStatus statusCode;
    private String message; //
    private T data;
}

