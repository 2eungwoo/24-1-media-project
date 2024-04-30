//package mediaproject.its.controller;
//
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import mediaproject.its.auth.CustomUserDetails;
//import mediaproject.its.domain.dto.PostDto;
//import mediaproject.its.domain.entity.Post;
//import mediaproject.its.domain.entity.User;
//import mediaproject.its.domain.repository.PostRepository;
//import mediaproject.its.domain.repository.UserRepository;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest // 테스트용 애플리케이션 컨섹스트 생성
//@AutoConfigureMockMvc // MockMvc 생성
//public class ApiTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    public void mockMvcSetup(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @AfterEach // 테스트 실행 후 실행하는 메서드
//    public void clenUp(){
//        postRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
////    @Test
////    @DisplayName("jwt토큰 헤더로 넘겨서 user profile 볼수있는지 테스트")
////    public void testProtectedEndpoint_withValidJwt() throws Exception {
////
////        TestJWTUtil testJWTUtil = new TestJWTUtil("vmfhaltmskdlstkfkdgodyroqkfwkdbalroqkfwkdbalaaaaaaaaaaaaaaaabbbbb");
////        String token = testJWTUtil.createJwt("test","ROLE_USER",60*60*10L);
////
////        MockHttpSession session = new MockHttpSession();
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/its/api/profile/1").session(session)
////                        .header("Authorization", "Bearer " + token)
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
////
////    }
//
//    @Test
//    @Order(1)
//    @DisplayName("게시글 생성 테스트")
//    public void postBoardTest() throws Exception {
//
//        //given
//        User user = new User(1,"tester9","password1234","ROLE_USER","description","test@test.com");
//        userRepository.save(user);
//        CustomUserDetails customUserDetails = new CustomUserDetails(user);
//
//
//        Post post = Post.builder()
//                .title("test title")
//                .content("test content")
//                .user(user)
//                .comments(null)
//                .build();
//
//        // POST 요청을 위한 DTO 생성
//        PostDto.Request postDto = new PostDto.Request("test title", "test content", user, null);
//
//        // ObjectMapper를 사용하여 DTO를 JSON으로 직렬화
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        String content = objectMapper.writeValueAsString(postDto);
//
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/its/api/post")
//                        .with(SecurityMockMvcRequestPostProcessors.user(customUserDetails))
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content)
//        );
//
//        //then
//        MvcResult result = actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
//                .andExpect(jsonPath("$.content").value(postDto.getContent()))
//                .andReturn();
//
//    }
//}
