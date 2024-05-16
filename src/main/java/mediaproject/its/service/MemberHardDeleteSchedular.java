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

    // 매달 마지막날 22시에 실행
    // @Scheduled(cron = "0 0 22 L * ?")
    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    public void memberHardDelete(){

      List<User> users = userRepository.findMembersByActiveStatus(false);
      if(users.isEmpty()){
          return;
      }
      userRepository.deleteMembersByActiveStatus(false);
    }
}
