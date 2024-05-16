package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberHardDeleteSchedular {

    private final UserRepository userRepository;

    // 매달 실행
    // 초기 딜레이 : 1달
    @Scheduled(fixedDelay = 1000L * 60L * 60L * 24L * 30L, initialDelay = 1000L * 60L * 60L * 24L * 30L, zone="Asia/Seoul")
    @Transactional
    public void memberHardDelete(){
        log.info("method called");
//        List<User> users = userRepository.findMembersByActiveStatus(false);
//        if(users.isEmpty()){
//            return;
//        }

//        userRepository.deleteMembersByActiveStatus(false);
        User user = userRepository.findMemberByActiveStatus(false);
        userRepository.deleteById(user.getId());
    }
}
