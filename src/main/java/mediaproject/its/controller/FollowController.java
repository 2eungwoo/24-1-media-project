package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.FollowDto;
import mediaproject.its.domain.dto.FollowInterface;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/its/api/follow/{userId}")
    public CommonResponseDto<?> addFollow(@PathVariable int userId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        // userId == Target User ID
        // username == From User Name

        String username = customUserDetails.getUser().getUsername();

        followService.addFollow(username, userId);

        FollowDto.Response followResponseDto = FollowDto.Response.builder()
                .fromUserName(username)
                .toUserId(userId)
                .build();

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("팔로우 등록 성공")
                .data(followResponseDto)
                .build();
    }

    @DeleteMapping("/its/api/follow/{userId}")
    public CommonResponseDto<?> deleteFollow(@PathVariable int userId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        // userId == Target User ID
        // username == From User Name

        String username = customUserDetails.getUser().getUsername();

        followService.deleteFollow(username, userId);

        FollowDto.Response followResponseDto = FollowDto.Response.builder()
                .fromUserName(username)
                .toUserId(userId)
                .build();

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("팔로우 삭제 성공")
                .data(followResponseDto)
                .build();
    }

    /* 팔로워 목록 조회*/
    @GetMapping("/its/api/profile/{userId}/followers")
    public CommonResponseDto<?> getFollowerList(@PathVariable int userId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();

        List<FollowInterface> followers = followService.getFollowerList(userId, username);
        List<FollowDto.InterfaceResponse> followersListDto = new ArrayList<>();


        for (FollowInterface f : followers) {
            FollowDto.InterfaceResponse followersDto = FollowDto.InterfaceResponse.builder()
                    .followerId(f.getId())
                    .followerName(f.getUser_name())
                    .build();
            followersListDto.add(followersDto);
        }

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("팔로워 리스트 조회 성공")
                .data(followersListDto)
                .build();
    }


    /* 팔로워 수 조회*/
    @GetMapping("/its/api/profile/{userId}/followers-count")
    public CommonResponseDto<?> getFollowerCount(@PathVariable int userId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        int followers = followService.countFollowers(userId, username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("팔로워 수 조회 성공")
                .data(followers)
                .build();
    }

}
