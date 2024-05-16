package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberHardDeleteSchedular {

    private final UserRepository userRepository;

    // 매달 1일 0시 0분 0초부터 시작
    // 초기 딜레이 : 1달(2592000초)
    @Scheduled(cron = "0 0 0 1 * *", initialDelayString = "PT2592000S", zone = "Asia/Seoul")
    @Transactional
    public void memberHardDelete(){

      List<User> users = userRepository.findMembersByActiveStatus(false);
      if(users.isEmpty()){
          return;
      }
      userRepository.deleteMembersByActiveStatus(false);
    }
}
