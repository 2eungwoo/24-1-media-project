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
    public ProfileDto.Response getProfileById(int userId){
        User user = userUtil.findUserById(userId);
        return new ProfileDto.Response(user);
    }

    @Transactional(readOnly = true)
    public ProfileDto.Response getMyProfileById(String username){
        User user = userUtil.findUser(username);
        return new ProfileDto.Response(user);
    }

    @Transactional
    public ProfileDto.Response updateProfile(int userId, String username, ProfileDto.Request profileRequestDto){

        User user = userUtil.findUserById(userId);
        User targetUser = userUtil.findUser(username);

        if(!user.equals(targetUser)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        String newDescription = profileRequestDto.getDescription();
        String newEmail = profileRequestDto.getEmail();

        if(newDescription == null){
            newDescription = user.getDescription();
        }
        if(newEmail == null){
            newEmail = user.getEmail();
        }

        user.updateProfile(newDescription, newEmail);
        userRepository.save(user);

        return new ProfileDto.Response(user);
    }

}
