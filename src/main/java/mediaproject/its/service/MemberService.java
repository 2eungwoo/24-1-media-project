package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.WithdrawlDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final MemberHardDeleteSchedular memberHardDeleteSchedular;

    @Transactional
    public WithdrawlDto.Response inactive(String username){
        User user = userUtil.findUser(username);
        int userId = user.getId();
        User us = userUtil.findUserById(userId);

        if(!user.equals(us)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        user.updateActiveStatus();
        User softDeletedMember = userRepository.save(user);

        return new WithdrawlDto.Response(softDeletedMember);

    }

    @Transactional
    public WithdrawlDto.Response rejoinSoftDeletedUser(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        user.updateActiveStatus();
        User rejoinUserDto = userRepository.save(user);
        return new WithdrawlDto.Response(rejoinUserDto);
    }


    @Transactional
    public WithdrawlDto.Response deleteMember(String username){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        memberHardDeleteSchedular.memberHardDelete();

         WithdrawlDto.Response deletedMemberDto = new WithdrawlDto.Response(user);
         return deletedMemberDto;
    }


}
