package mediaproject.its.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.util.AuthenticationHelper;
import mediaproject.its.util.MockTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        assertEquals(loginUser.getUsername(),"test user");

        final int userId = loginUser.getId();

        final String url = "/its/api/profile/" + userId;
        ProfileDto.Response profileDto = new ProfileDto.Response("test user","test description","email@naver.com");

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.username").value(profileDto.getUsername()))
//                .andExpect(jsonPath("$.data.description").value(profileDto.getDescription()))
//                .andExpect(jsonPath("$.data.email").isNotEmpty())
                .andDo(print());
    }

}
