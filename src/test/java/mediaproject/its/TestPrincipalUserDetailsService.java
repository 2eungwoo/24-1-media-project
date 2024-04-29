package mediaproject.its;

import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TestPrincipalUserDetails implements UserDetailsService {

    private final String USER_NAME = "tester";

    private User getUser(){
        return User.builder()
                .username("tester_aaa")
                .password("1234")
                .email("test@test.com")
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(USER_NAME)) {
            return new CustomUserDetails(getUser());
        }
        return null;
    }
}
