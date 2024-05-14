package mediaproject.its.controller;

import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.util.AuthenticationHelper;
import mediaproject.its.util.MockTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // 테스트용 애플리케이션 컨섹스트 생성
@AutoConfigureMockMvc // MockMvc 생성
public class TestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationHelper authenticationHelper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username="tester", password="1234", roles="user")
    void test1() throws Exception {
        System.out.println("=== test1: " + SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    @WithAnonymousUser
    void test2() {
        System.out.println("=== test2: " + SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("테스트해보자ㅏ..")
    @MockTester
    void letsTest() throws Exception {
        User loginUser = authenticationHelper.getCurrentUser();
        assertEquals(loginUser.getUsername(),"mock_user");

    }



}
