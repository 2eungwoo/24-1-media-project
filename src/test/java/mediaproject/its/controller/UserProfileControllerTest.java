package mediaproject.its.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mediaproject.its.TestJWTUtil;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨섹스트 생성
@AutoConfigureMockMvc // MockMvc 생성
class UserProfileControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void loadUser(){
        User user = User.builder()
                .username("tester")
                .description("test description")
                .email("email@naver.com")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    public void deleteTestUser(){
        final int testerId = userRepository.findByUsername("tester").getId();
        userRepository.deleteById(testerId);
    }

    @DisplayName("유저 프로필 조회 테스트")
    @Test
    public void getProfileTest() throws Exception {
        // given
        final int userId = userRepository.findByUsername("tester").getId();

        final String url = "/its/api/profile/" + userId;
        ProfileDto.Response profileDto = new ProfileDto.Response("test description","email@naver.com");

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description").value(profileDto.getDescription()))
                .andExpect(jsonPath("$.data.email").isNotEmpty())
                .andDo(print());
    }


    @DisplayName("유저 프로필 수정 테스트")
    @Test
    public void editProfileTest() throws Exception{

        // given
        User user = userRepository.findByUsername("tester");
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        final int userId = userRepository.findByUsername("tester").getId();
        final String url = "/its/api/profile/" + userId;

        ProfileDto.Request profileDto = new ProfileDto.Request("updated description","updated email");

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(profileDto);


        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .with(SecurityMockMvcRequestPostProcessors.user(customUserDetails))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));


        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description").value("updated description"))
                .andExpect(jsonPath("$.data.email").value("updated email"))
                .andDo(print());
    }
}