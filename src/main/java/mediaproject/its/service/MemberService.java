package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Transactional
    public void signout(String username){
        User user = userUtil.findUser(username);
        user.updateActiveStatus();

    }

}
