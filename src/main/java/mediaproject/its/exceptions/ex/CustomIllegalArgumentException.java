package mediaproject.its.exceptions.ex;


import java.util.Map;

public class CustomIllegalArgumentException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomIllegalArgumentException(String message) {
        super(message);
    }

    public CustomIllegalArgumentException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}
