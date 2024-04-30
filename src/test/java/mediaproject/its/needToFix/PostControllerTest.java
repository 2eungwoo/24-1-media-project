//package mediaproject.its.controller;
//
//import mediaproject.its.TestJWTUtil;
//import mediaproject.its.domain.entity.Post;
//import mediaproject.its.domain.entity.User;
//import mediaproject.its.domain.repository.PostRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest // 테스트용 애플리케이션 컨섹스트 생성
//@AutoConfigureMockMvc // MockMvc 생성
//public class PostControllerTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @BeforeEach
//    public void mockMvcSetup(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @AfterEach // 테스트 실행 후 실행하는 메서드
//    public void clenUp(){
//        postRepository.deleteAll();
//    }
//
//
//    @DisplayName("getTest : 포스트 조회")
//    @Test
//    public void getPost() throws Exception{
//        // given
//        final String url = "/its/posts";
//        User user = User.builder()
//                .id(99)
//                .username("tester")
//                .build();
//
//        Post post1 = Post.builder()
//                .title("title")
//                .content("content")
//                .user(user)
//                .build();
//
//        Post post2 = Post.builder()
//                .title("title 2")
//                .content("content 2")
//                .user(user)
//                .build();
//
//        postRepository.save(post1);
//        postRepository.save(post2);
//
//        // when
//        final ResultActions result = mockMvc.perform(get(url)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        result
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].title").value(post1.getTitle()))
//                .andExpect(jsonPath("$[0].content").value(post1.getContent()));
//    }
//
//    @DisplayName("postPost : 포스트 작성")
//    @Test
//    public void postPost() throws Exception{
//        // given
//        final String url = "/its/api/post/{id}";
//        final String requestBody = "{\"title\":\"new title\", \"content\":\"new content\"}";
//
//        // when
//        final ResultActions result = mockMvc.perform(post(url)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(requestBody));
//
//        // then
//        result
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").value("new title"))
//                .andExpect(jsonPath("$.content").value("new content"));
//    }
//
//    @DisplayName("updatePost : 포스트 수정")
//    @Test
//    public void updatePost() throws Exception{
//        // given
//        final String url = "/its/api/post/{id}"; // url을 /test/1 로 하면 안먹고 {id}로 해야 먹음 왜지?
//        Post post = Post.builder()
//                .title("title")
//                .content("content")
//                .build();
//        postRepository.save(post);
//        final String requestBody = "{\"title\":\"updated title\", \"content\":\"updated content\"}";
//
//        // when
//        final ResultActions result = mockMvc.perform(put(url, post.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody));
//
//        // then
//        result
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("updated title"))
//                .andExpect(jsonPath("$.content").value("updated content"));
//    }
//
//    @DisplayName("deletePost: 포스트 삭제")
//    @Test
//    public void deletePost() throws Exception {
//        // given
//        final String url = "/its/api/post/{id}"; // url을 /test/1 로 하면 안먹고 {id}로 해야 먹음 왜지?
//        Post post = Post.builder()
//                .title("testtitle")
//                .content("testcontent")
//                .build();
//        postRepository.save(post);
//
//        // when
//        mockMvc.perform(delete(url, post.getId()))
//                .andExpect(status().isOk());
//
//        // then
//        List<Post> posts = postRepository.findAll();
//        assertThat(posts).isEmpty();
//
//
//    }
//
//    // displayname이 왜 콘솔창에 안나오는지?
//
//
//}
