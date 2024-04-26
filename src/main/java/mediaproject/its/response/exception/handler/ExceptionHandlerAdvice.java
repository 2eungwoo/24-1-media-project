package mediaproject.its.response.exception.handler;

import mediaproject.its.response.dto.ErrorResponseDto;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.ErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice  {

    /*
        <ErrorResponseDto>
        HttpStatus httpStatus,
        String message,
        ErrorCode errorCode,
     */
    @ExceptionHandler(CustomRestApiException.class)
    public ErrorResponseDto<?> customRestApiException(CustomRestApiException e) {
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getMessage();
        ErrorCode errorCode = e.getErrorCode();
        return new ErrorResponseDto<>(httpStatus,message,errorCode);
    }

    @ExceptionHandler(CustomDuplicateMemberException.class)
    public ErrorResponseDto<?> customDuplicateMemberException(CustomDuplicateMemberException e){
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getMessage();
        ErrorCode errorCode = UserErrorCode.USER_ALREADY_EXISTS_ERROR;
        System.out.println("customDuplicatieMemberException class call");
        return new ErrorResponseDto<>(httpStatus,message,errorCode);
    }

    @ExceptionHandler(CustomUnAuthorizedException.class)
    public ErrorResponseDto<?> customUnAuthorizedException(CustomUnAuthorizedException e){
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getMessage();
        ErrorCode errorCode = UserErrorCode.USER_UNAUTHORIZED;
        System.out.println("CustomUnAuthorizedException class call");
        return new ErrorResponseDto<>(httpStatus,message,errorCode);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ErrorResponseDto<?> customIllegalArgumentException(CustomIllegalArgumentException e) {
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getMessage();
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return new ErrorResponseDto<>(httpStatus,message,errorCode);
    }




}