package mediaproject.its.exceptions;

import mediaproject.its.domain.dto.error.ErrorDto;
import mediaproject.its.exceptions.ex.CustomAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<?> handleAppException(CustomAppException ex){
        return ResponseEntity.ok().body(ErrorDto.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build());
    }

}
