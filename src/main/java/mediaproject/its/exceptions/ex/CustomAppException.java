package mediaproject.its.exceptions.ex;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CustomAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomAppException(String message){
        super(message);
    }

}

