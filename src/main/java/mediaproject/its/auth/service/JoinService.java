package mediaproject.its.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.JoinDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean joinUser(JoinDto joinDto){
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist)
            return false;
        else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setRole("ROLE_USER");

            userRepository.save(user);
            return true;
        }
    }

    @Transactional
    public boolean joinAdmin(JoinDto joinDto){
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist)
            return false;
        else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setRole("ROLE_ADMIN");

            userRepository.save(user);
            return true;
        }
    }
}
