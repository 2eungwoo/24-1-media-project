package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.request.PostRequestDto;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.request.UpdatePostRequestDto;
import mediaproject.its.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/its/posts")
    public ResponseEntity<?> getPost(){
        List<Post> posts = postService.getAllPost();
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회 성공")
                .data(posts)
                .build()
        );
    }

    @GetMapping("/its/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id){
        Post post = postService.getPostById(id);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회(단건) 성공")
                .data(post)
                .build());
    }

    @PostMapping("/its/api/post")
    public ResponseEntity<?> postPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        // todo : bug fix
        // 임시 세션에서(SecurityContextHolder) .getId()하면 아이디가 안불러와짐. 항상 0만 불러와짐.
        // 그래서 임시방편으로 username으로 조치
        String username = customUserDetails.getUser().getUsername();
//        String userRole = customUserDetails.getUser().getRole();
//        int userId = customUserDetails.getUser().getId();

//        System.out.println("11===============================");
//        System.out.println("log in user role : "+ userRole);
//        System.out.println("log in user name : "+ username);
//        System.out.println("log in user id : "+ userId); // 왜 얘만 안나오냐 이말이지
//        System.out.println("11===============================");

        postService.postPost(postRequestDto,username);

        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("포스트 등록 성공")
                .data(postRequestDto)
                .build());
    }

    @PutMapping("/its/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int id, @RequestBody UpdatePostRequestDto updatePostRequestDto){
        Post updatedPost = postService.updatePost(id, updatePostRequestDto);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 수정 성공")
                .data(updatedPost)
                .build());
    }

    @DeleteMapping("/its/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id){
        postService.deletePost(id);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 삭제 성공")
                .data(null)
                .build());
    }

}
