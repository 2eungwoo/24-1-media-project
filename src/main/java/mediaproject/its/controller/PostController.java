package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.exceptions.ex.CustomAppException;
import mediaproject.its.exceptions.ex.CustomIllegalArgumentException;
import mediaproject.its.response.error.ErrorResponseDto;
import mediaproject.its.response.response.CommonResponseDto;
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
        try{
            List<Post> posts = postService.getAllPost();
            return ResponseEntity.ok().body(CommonResponseDto.builder()
                    .statusCode(HttpStatus.OK)
                    .message("포스트 조회 성공")
                    .data(posts)
                    .build()
            );
        }catch(CustomAppException ex){
            throw new CustomAppException("포스트 조회 실패");
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable long id){
        try{
            Post post = postService.getPostById(id);
            return ResponseEntity.ok().body(CommonResponseDto.builder()
                    .statusCode(HttpStatus.OK)
                    .message("포스트 조회(단건) 성공")
                    .data(post)
                    .build());
        }catch(CustomIllegalArgumentException ex){
            throw new CustomIllegalArgumentException("포스트 조회(단건) 실패");
        }

    }

    @PostMapping("/post")
    public ResponseEntity<?> postPost(@RequestBody PostDto postDto){
        try {
            postService.postPost(postDto);
            return ResponseEntity.ok().body(CommonResponseDto.builder()
                    .statusCode(HttpStatus.CREATED)
                    .message("포스트 등록 성공")
                    .data(postDto)
                    .build());
        }catch(CustomAppException ex){
            throw new CustomAppException("포스트 등록 실패");
        }
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto updatePostRequestDto){
        try{
            Post updatedPost = postService.updatePost(id, updatePostRequestDto);
            return ResponseEntity.ok().body(CommonResponseDto.builder()
                    .statusCode(HttpStatus.OK)
                    .message("포스트 수정 성공")
                    .data(updatedPost)
                    .build());
        }catch(CustomAppException ex){
            throw new CustomAppException("포스트 수정 실패");
        }

    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try{
            postService.deletePost(id);
            return ResponseEntity.ok().body(CommonResponseDto.builder()
                    .statusCode(HttpStatus.OK)
                    .message("포스트 삭제 성공")
                    .data(null)
                    .build());
        }catch(CustomAppException ex){
            throw new CustomAppException("포스트 삭제 실패");
        }

    }

}
