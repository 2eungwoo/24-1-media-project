package mediaproject.its.exceptions;

import mediaproject.its.exceptions.ex.*;
import mediaproject.its.response.error.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<ErrorResponseDto> appException(CustomAppException ex) {
        return ResponseEntity.ok().body(ErrorResponseDto.builder()
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(CustomNotFoundException ex) {
        return ResponseEntity.ok().body(ErrorResponseDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorStatus("Not Found Exepction occuured")
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> IllegalArgumentException(CustomIllegalArgumentException ex) {
        return ResponseEntity.ok().body(ErrorResponseDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorStatus("illegal Argument Exception occurred")
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<ErrorResponseDto> validationApiException(CustomValidationApiException ex) {
        return ResponseEntity.ok().body(ErrorResponseDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ErrorResponseDto> apiException(CustomApiException ex) {
        return ResponseEntity.ok().body(ErrorResponseDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build());
    }

}
