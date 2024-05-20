package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.FollowRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberHardDeleteSchedular {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 매달 실행
    // 초기 딜레이 : 1달
    @Scheduled(fixedDelay = 1000L * 60L * 60L * 24L * 30L, initialDelay = 1000L * 60L * 60L * 24L * 30L, zone="Asia/Seoul")
    @Transactional
    public void memberHardDelete(){
        log.info("method called");
//        todo : 딜레이시간 적용되게끔 fix 필요
        List<User> users = userRepository.findMembersByActiveStatus(false);
        if(users.isEmpty()){
            return;
        }

        // user id 추출 후 팔로우 끊고 유저 삭제

        Set<User> inactiveUsers = new HashSet<>(users);

        Set<Integer> userIds = inactiveUsers.stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        for (int userId : userIds) {
            System.out.println("Inactive User ID: " + userId);
            followRepository.deleteUserByInactiveUser(userId);
        }

        userRepository.deleteMembersByActiveStatus(false);
    }
}
