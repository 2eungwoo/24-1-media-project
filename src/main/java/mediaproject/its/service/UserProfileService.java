package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getProfileById(int userId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage()));

        return user;
    }

    @Transactional
    public User updateProfile(int userId, String username, ProfileDto.Request profileRequestDto){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage()));

        User targetUser = userRepository.findByUsername(username);
        if(targetUser == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        if(!user.equals(targetUser)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        user.updateProfile(profileRequestDto.getDescription(), profileRequestDto.getEmail());
        userRepository.save(user);

        return user;
    }

}
