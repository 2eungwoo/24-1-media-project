package mediaproject.its.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.Post;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void loadUser(){
        User user = User.builder()
                .username("postTester4")
                .build();

        userRepository.save(user);
    }

    @AfterEach()
    public void deleteTestUser(){
        int userId = userRepository.findByUsername("postTester4").getId();
        userRepository.deleteById(userId);
    }

    @Test
    @DisplayName("포스트 작성 테스트")
    public void postPostTest() throws Exception {
        // given
        User user = userRepository.findByUsername("postTester3");
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        final String url = "/its/api/post";

        PostDto.Request profileDto = PostDto.Request.builder()
                .title("tester title")
                .content("tester content")
                .comments(null)
                .user(user)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(profileDto);


        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .with(SecurityMockMvcRequestPostProcessors.user(customUserDetails))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        result
                .andExpect(jsonPath("$.data.title").value("tester title"))
                .andDo(print());
    }

    @Test
    @DisplayName("포스트 all 조회 테스트")
    public void getPosts() throws Exception {
        // given
//        User user = User.builder()
//                .username("tester")
//                .build();
//        userRepository.save(user);
        User tester = userRepository.findByUsername("postTester4");

        Post post = Post.builder()
                .title("test post")
                .content("test content")
                .user(tester)
                .build();


        postRepository.save(post);

        final String url = "/its/posts";

        // when
        ResultActions result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));


        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test1 content"))
                .andDo(print());
    }

}