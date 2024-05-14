package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.repository.CommentRepository;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/its/api/comment/{postId}")
    public CommonResponseDto<?> postComment(@RequestBody CommentDto.Request commentRequest, @PathVariable int postId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        CommentDto.Response commentResponseDto = commentService.postComment(commentRequest, postId, username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("댓글 작성 성공")
                .data(commentResponseDto)
                .build();
    }

    @PutMapping("/its/api/comment/{commentId}")
    public CommonResponseDto<?> updateComment(@PathVariable int commentId,
                                           @RequestBody CommentDto.Request commentRequestDto,
                                           @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
        CommentDto.Response updatedCommentDto = commentService.updateComment(commentId, commentRequestDto, username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("댓글 수정 성공")
                .data(updatedCommentDto)
                .build();
    }


    @DeleteMapping("/its/api/comment/{commentId}")
    public CommonResponseDto<?> deleteComment(@PathVariable int commentId, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
        CommentDto.Response deletedComment = commentService.deleteComment(commentId, username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("댓글 삭제 성공")
                .data(deletedComment)
                .build();
    }
}
