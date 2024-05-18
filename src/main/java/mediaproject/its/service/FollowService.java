package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.FollowDto;
import mediaproject.its.domain.dto.FollowInterface;
import mediaproject.its.domain.entity.Follow;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.FollowRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserUtil userUtil;
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFollow(String username, int userId){
        User fromUser = userUtil.findUser(username);
        User toUser = userUtil.findUserById(userId);

        System.out.println("================== from user : "+fromUser.getUsername());
        System.out.println("================== from user : "+toUser.getUsername());
        if(fromUser.getId() == toUser.getId()){
            throw new CustomRestApiException(CommonErrorCode.INVALID_PARAMETER,"자신을 팔로우 할 수 없음");
        }

        try{
            followRepository.customAddFollow(fromUser.getId(), toUser.getId());
            fromUser.updateFollowingsCount(1);
            toUser.updateFollowersCount(1);
            userRepository.save(fromUser);
            userRepository.save(toUser);
        }catch(Exception e){
            throw new CustomRestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR,"이미 팔로우 하였음");
        }
    }

    @Transactional
    public void deleteFollow(String username, int userId){
        User fromUser = userUtil.findUser(username);
        User toUser = userUtil.findUserById(userId);

        fromUser.updateFollowingsCount(-1);
        toUser.updateFollowersCount(-1);
        userRepository.save(fromUser);
        userRepository.save(toUser);

        followRepository.customDeleteFollow(fromUser.getId(), toUser.getId());
    }

    @Transactional(readOnly = true)
    public int countFollowers(int userId, String username){

        userUtil.findUserById(userId);
        userUtil.findUser(username);

        // 자신의 팔로워만 볼수있게 할 필요는 없을거같음
//        if(currentUserId != pathVarUserId){
//            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
//        }

        return followRepository.customCountFollowers(userId);
    }

    @Transactional(readOnly = true)
    public List<FollowInterface> getFollowerList(int userId, String username){
        userUtil.findUserById(userId);
        userUtil.findUser(username);

        return followRepository.findFollowersByToUserId(userId);
    }
}
