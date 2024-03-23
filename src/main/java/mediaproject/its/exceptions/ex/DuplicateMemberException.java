package mediaproject.its.exceptions.ex;

import lombok.Getter;
import org.springframework.dao.DuplicateKeyException;

@Getter
public class DuplicateMemberException extends DuplicateKeyException {
    public DuplicateMemberException(String msg) {
        super(msg);
    }
}