package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.UpdatePostRequestDto;
import mediaproject.its.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/its")
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<?> getPost(){
        List<Post> posts = postService.getAllPost();
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회 성공")
                .data(posts)
                .build()
        );
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id){
        Post post = postService.getPostById(id);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회(단건) 성공")
                .data(post)
                .build());
    }

    @PostMapping("/post")
    public ResponseEntity<?> postPost(@RequestBody PostDto postDto){
        postService.postPost(postDto);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("포스트 등록 성공")
                .data(postDto)
                .build());
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto){
        Post updatedPost = postService.updatePost(id, updatePostRequestDto);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 수정 성공")
                .data(updatedPost)
                .build());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 삭제 성공")
                .data(null)
                .build());
    }

}
