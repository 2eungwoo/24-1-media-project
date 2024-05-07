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
        Comment newComment = commentService.postComment(commentRequest, postId, username);

        CommentDto.Response commentResponseDto = new CommentDto.Response(newComment);

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
        Comment updatedComment = commentService.updateComment(commentId, commentRequestDto, username);

        CommentDto.Response updatedCommentDto = new CommentDto.Response(updatedComment);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("댓글 수정 성공")
                .data(updatedCommentDto)
                .build();
    }


    @DeleteMapping("/its/api/comment/{commentId}")
    public CommonResponseDto<?> deleteComment(@PathVariable int commentId, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
        Comment comment = commentService.deleteComment(commentId, username);

        CommentDto.Response deletedComment = new CommentDto.Response(comment);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("댓글 삭제 성공")
                .data(deletedComment)
                .build();
    }
}
