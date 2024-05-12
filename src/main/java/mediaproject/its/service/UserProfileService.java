package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public User getProfileById(int userId){
        return userUtil.findUserById(userId);
    }

    @Transactional(readOnly = true)
    public User getMyProfileById(String username){

        return userUtil.findUser(username);
    }

    @Transactional
    public User updateProfile(int userId, String username, ProfileDto.Request profileRequestDto){

        User user = userUtil.findUserById(userId);
        User targetUser = userUtil.findUser(username);

        if(!user.equals(targetUser)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        user.updateProfile(profileRequestDto.getDescription(), profileRequestDto.getEmail());
        userRepository.save(user);

        return user;
    }

}
