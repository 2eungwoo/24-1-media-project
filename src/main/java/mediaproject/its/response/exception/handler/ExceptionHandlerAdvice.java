package mediaproject.its.response.exception.handler;

import mediaproject.its.response.dto.ErrorResponseDto;
import mediaproject.its.response.exception.CustomDuplicateMemberException;
import mediaproject.its.response.exception.CustomRestApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice  {

    /*
        <ErrorResponseDto>
        HttpStatus httpStatus,
        String message,
        int status
     */
    @ExceptionHandler(CustomRestApiException.class)
    public ErrorResponseDto customRestApiException(CustomRestApiException e) {
        return new ErrorResponseDto(e.getErrorCode().getHttpStatus(), e.getMessage(),e.getErrorCode().getStatus());
    }

    @ExceptionHandler(CustomDuplicateMemberException.class)
    public ErrorResponseDto customDuplicateMemberException(CustomDuplicateMemberException e){
        return new ErrorResponseDto(e.getErrorCode().getHttpStatus(),e.getMessage(),e.getErrorCode().getStatus());
    }


}