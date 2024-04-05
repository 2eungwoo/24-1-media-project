package mediaproject.its.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.JoinDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomDuplicateMemberException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinUser(JoinDto joinDto){
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        User user = userRepository.findByUsername(username);

        if(user != null){
            throw new CustomDuplicateMemberException(UserErrorCode.USER_ALREADY_EXISTS_ERROR.getMessage(), UserErrorCode.USER_ALREADY_EXISTS_ERROR);
        }

        User newUser = User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_USER")
                .build();

        userRepository.save(newUser);

    }

    @Transactional
    public void joinAdmin(JoinDto joinDto){
        String username = joinDto.getUsername();
        String password = joinDto.getPassword();

        User user = userRepository.findByUsername(username);

        if(user != null){
            throw new CustomDuplicateMemberException(UserErrorCode.USER_ALREADY_EXISTS_ERROR.getMessage(), UserErrorCode.USER_ALREADY_EXISTS_ERROR);
        }

        User newUser = User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(newUser);
    }
}
