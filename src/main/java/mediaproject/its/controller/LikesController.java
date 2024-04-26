package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.LikesDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.Likes;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/its/api/likes/{postId}")
    public ResponseEntity<?> addPostLikes(@PathVariable int postId, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        // int userId = customUserDetails.getUser().getId();
        String username = customUserDetails.getUser().getUsername();

        // todo : 왜 userId 무조건 0 나오지?
        // System.out.println("============ user ID : " + userId);


        likesService.addPostLikes(username, postId);

        LikesDto.Response likesResponseDto = new LikesDto.Response(username,postId,0);

        return ResponseEntity.ok(CommonResponseDto.builder()
                        .statusCode(HttpStatus.CREATED)
                        .data(likesResponseDto)
                        .message("게시글 좋아요 등록 성공")
                .build());
    }

    @DeleteMapping("/its/api/likes/{likesId}")
    public ResponseEntity<?> deletePostLikes(@PathVariable int likesId, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();

        Likes deletedLikes = likesService.deletePostLikes(username, likesId);
        LikesDto.Response likesResponseDto = new LikesDto.Response(deletedLikes);

        return ResponseEntity.ok(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .data(likesResponseDto)
                .message("게시글 좋아요 삭제 성공")
                .build());

    }
}
