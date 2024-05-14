package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.ProfileDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/its/api/profile/my")
    public CommonResponseDto<?> getMyProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();

        ProfileDto.Response userProfileResponseDto = userProfileService.getMyProfileById(username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .data(userProfileResponseDto)
                .message("마이 프로필 조회 성공")
                .build();
    }


    @GetMapping("/its/api/profile/{userId}")
    public CommonResponseDto<?> getProfileById(@PathVariable int userId){
        ProfileDto.Response userProfileResponseDto  = userProfileService.getProfileById(userId);

        return CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK)
                        .data(userProfileResponseDto)
                        .message("유저 프로필 조회 성공")
                .build();
    }

    @PutMapping("/its/api/profile/{userId}")
    public CommonResponseDto<?> updatedProfile(@PathVariable int userId,
                                            @RequestBody ProfileDto.Request profileRequestDto,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();

        ProfileDto.Response updatedUserProfileResponseDto = userProfileService.updateProfile(userId,username,profileRequestDto);

        return CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK)
                        .message("유저 프로필 수정 성공")
                        .data(updatedUserProfileResponseDto)
                .build();

    }
}
