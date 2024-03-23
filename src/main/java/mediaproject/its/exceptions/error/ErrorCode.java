package mediaproject.its.exceptions.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    int getCode();
    String getMessage();
}