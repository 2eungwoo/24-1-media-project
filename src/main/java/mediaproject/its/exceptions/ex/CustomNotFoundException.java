package mediaproject.its.exceptions.ex;

import java.util.Map;

public class CustomNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomNotFoundException(String message) {
        super(message);
    }

    public CustomNotFoundException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}