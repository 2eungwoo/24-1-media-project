package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommonResponseDto;
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
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseEntity<?> getPost(){
        List<Post> posts = postService.getAllPost();
        return new ResponseEntity<>(new CommonResponseDto<>("포스트 조회 성공", posts), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id){
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(new CommonResponseDto<>("포스트 단건 조회 성공",post),HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<?> postPost(@RequestBody PostDto postDto){
        postService.postPost(postDto);
        return new ResponseEntity<>(new CommonResponseDto<>("포스트 등록 성공",postDto),HttpStatus.CREATED);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto){
        Post updatedPost = postService.updatePost(id, updatePostRequestDto);
        return new ResponseEntity<>(new CommonResponseDto<>("포스트 수정 성공",updatedPost),HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity<>(new CommonResponseDto<>("포스트 삭제 성공",null),HttpStatus.OK);
    }

}
