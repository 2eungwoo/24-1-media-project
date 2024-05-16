package mediaproject.its.service.Util;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUtil {

    private final UserRepository userRepository;

    public void validUser(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }
        if(!user.getActiveStatus()){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,"탈퇴한 회원");
        }
    }


    public User findUser(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }
        if(!user.getActiveStatus()){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,"탈퇴한 회원");
        }

        return user;
    }

    public User findUserById(int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage()));
        if(!user.getActiveStatus()){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,"탈퇴한 회원");
        }
        return user;
    }
}
