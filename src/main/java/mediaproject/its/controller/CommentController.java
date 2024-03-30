package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.request.CommentRequestDto;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/its/api/post/{postId}/comment")
    public ResponseEntity<?> postComment(@RequestBody CommentDto.Request commentRequest, @PathVariable int postId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        Comment newComment = commentService.postComment(commentRequest, postId, username);

        CommentDto.Response commentResponseDto = new CommentDto.Response(newComment);

        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("댓글 작성 성공")
                .data(commentResponseDto)
                .build());
    }

}
