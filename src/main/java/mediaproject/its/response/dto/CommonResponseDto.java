package mediaproject.its.response.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonResponseDto<T> {
    private final boolean success = true;
    private HttpStatus statusCode;
    private String message; //
    private T data;


    public CommonResponseDto<?> response(HttpStatus httpStatus,String message, T data){
        return CommonResponseDto.builder()
                .statusCode(httpStatus)
                .message(message)
                .data(data)
                .build();
    }
    public CommonResponseDto<?> errorResponse(HttpStatus httpStatus,String message, T data){
        return CommonResponseDto.builder()
                .statusCode(httpStatus)
                .message(message)
                .data(data)
                .build();
    }

    public ErrorResponseDto2<?> errorResponse2(HttpStatus httpStatus, String message, T data){
        return ErrorResponseDto2.builder()
                .httpStatus(httpStatus)
                .message(message)
                .data(data)
                .build();
    }
}

