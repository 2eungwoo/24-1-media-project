package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.entity.User;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.UpdatePostRequestDto;
import mediaproject.its.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
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

    @PostMapping("/api/its/post")
    public ResponseEntity<?> postPost(@RequestBody PostDto postDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
//        int userId = customUserDetails.getUser().getId();
//
//        System.out.println("user : " + userId);

        postService.postPost(postDto,username);

        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("포스트 등록 성공")
                .data(postDto)
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
