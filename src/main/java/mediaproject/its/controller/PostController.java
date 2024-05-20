package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.PostContentDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.service.PostService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // todo : List로 ResponseDto 형태로 어떻게 반환하지?
    // 해결! https://velog.io/@nyong_i/List%EB%A5%BC-Dto%EB%A1%9C-%EB%B0%98%ED%99%98%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95-RESTful-API
    @GetMapping("/its/posts")
    public CommonResponseDto<?> getPost(){
        List<PostDto.Response> postsResponseDto = postService.getAllPost();

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회 성공")
                .data(postsResponseDto)
                .build();

    }

    @GetMapping("/its/post/{id}")
    public CommonResponseDto<?> getPostById(@PathVariable int id){
        PostContentDto.Response postResponseDto = postService.getPostById(id);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회(단건) 성공")
                .data(postResponseDto)
                .build();
    }

    @GetMapping("/its/posts/hot-view")
    public CommonResponseDto<?> getPostsOrderedByViewCount(){

        List<PostDto.Response> hotViewPostsDto = postService.getPostsOrderedByViewCount();

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("조회수 많은 순 포스트 조회 성공")
                .data(hotViewPostsDto)
                .build();
    }

    @GetMapping("/its/posts/hot-likes")
    public CommonResponseDto<?> getPostsOrderedByLikesCount(){

        List<PostDto.Response> hotLikedPostsDto = postService.getPostsOrderedByLikesCount();

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("좋아요 많은 순 포스트 조회 성공")
                .data(hotLikedPostsDto)
                .build();
    }

    @PostMapping("/its/api/post")
    public CommonResponseDto<?> postPost(@RequestBody PostDto.Request postRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
        PostDto.Response postResponseDto  = postService.postPost(postRequest,username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("포스트 등록 성공")
                .data(postResponseDto)
                .build();
    }

    @PatchMapping("/its/api/post/{postId}")
    public CommonResponseDto<?> updatePost(@PathVariable int postId, @RequestBody PostDto.Request updatePostRequestDto,@AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
        PostDto.Response updatedPostResponseDto = postService.updatePost(postId, updatePostRequestDto, username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 수정 성공")
                .data(updatedPostResponseDto)
                .build();
    }

    @DeleteMapping("/its/api/post/{postId}")
    public CommonResponseDto<?> deletePost(@PathVariable int postId,@AuthenticationPrincipal CustomUserDetails customUserDetails ){

        String username = customUserDetails.getUser().getUsername();

        PostDto.Response deletedPostResponseDto = postService.deletePost(postId,username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 삭제 성공")
                .data(deletedPostResponseDto)
                .build();
    }


    @GetMapping("/its/api/likes/posts/{userId}")
    public CommonResponseDto<?> findPostsLikedByUser(@PathVariable int userId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String username = customUserDetails.getUser().getUsername();
        List<PostInterface> posts = postService.findPostsLikedByUser(username, userId);

        List<PostDto.InterfaceResponse> postsInterfaceResponseDto = extractPostInterfaceList(posts);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("좋아요 등록 포스트 리스트 조회 성공")
                .data(postsInterfaceResponseDto)
                .build();
    }

    static List<PostDto.InterfaceResponse> extractPostInterfaceList(List<PostInterface> posts) {
        List<PostDto.InterfaceResponse> postsInterfaceResponseDto = new ArrayList<>();

        for (PostInterface p : posts) {
            PostDto.InterfaceResponse postsDto = PostDto.InterfaceResponse.builder()
                    .postId(p.getId())
                    .title(p.getTitle())
                    .userName(p.getUser_name())
                    .view_count(p.getView_count())
                    .likes_count(p.getLikes_count())
                    .comment_count(p.getComment_count())
                    .hiring_type(p.getHiring_type())
                    .position_type(p.getPosition_type())
                    .process_type(p.getProcess_type())
                    .recruiting_type(p.getRecruiting_type())
                    .techstack_type(p.getTechstack_type())
                    .build();
            postsInterfaceResponseDto.add(postsDto);
        }
        return postsInterfaceResponseDto;
    }

    @GetMapping("/clear-cache")
    @CacheEvict("post")
    public String clearCache(){
        return "cache has been cleared";
    }

}
