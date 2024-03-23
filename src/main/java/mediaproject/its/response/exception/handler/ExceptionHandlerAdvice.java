package mediaproject.its.response.exception.handler;

import jakarta.persistence.EntityNotFoundException;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.ErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.DuplicateMemberException;
import mediaproject.its.response.exception.RestApiException;
import mediaproject.its.response.dto.ErrorResponseDto;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    //모든 에러 -> 하위 에러에서 못받을 때
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        // NestedExceptionUtils.getMostSpecificCause() -> 가장 구체적인 원인, 즉 가장 근본 원인을 찾아서 반환
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(), errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handleSystemException(RestApiException e){
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(),errorCode.getCode(),  errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

    //메소드가 잘못되었거나 부적합한 인수를 전달했을 경우 -> 필수 파라미터 없을 때
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        ErrorCode errorCode = CommonErrorCode.ILLEGAL_ARGUMENT_ERROR;
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(),errorCode.getCode(),
                String.format("%s %s", errorCode.getMessage(), NestedExceptionUtils.getMostSpecificCause(e).getMessage()));
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

    //@Valid 유효성 검사에서 예외가 발생했을 때 -> requestbody에 잘못 들어왔을 때
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorCode errorCode = CommonErrorCode.INVALID_ARGUMENT_ERROR;
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(),
                errorCode.getCode(),
                errorCode.getMessage(),
                e.getBindingResult());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

    //잘못된 포맷 요청 -> Json으로 안보내다던지
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ErrorCode errorCode = CommonErrorCode.INVALID_FORMAT_ERROR;
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(), errorCode.getCode(),  errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

    //중복 회원 예외처리
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<?> handleHttpClientErrorException(DuplicateMemberException e){
        ErrorCode errorCode = UserErrorCode.MEMBER_ALREADY_EXISTS_ERROR;
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(),errorCode.getCode(), e.getMessage()+ errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e){
        ErrorCode errorCode = UserErrorCode.MEMBER_NOT_FOUND_ERROR;
        ErrorResponseDto errorResponseDto = ErrorResponseDto.of(errorCode.getHttpStatus(),errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponseDto);
    }

}