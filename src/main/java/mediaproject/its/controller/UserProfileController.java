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

    @GetMapping("/its/api/profile/{userId}")
    public ResponseEntity<?> getProfileById(@PathVariable int userId){
        User user = userProfileService.getProfileById(userId);

        ProfileDto.Response userResponseDto = new ProfileDto.Response(user);

        return ResponseEntity.ok(CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK)
                        .data(userResponseDto)
                        .message("유저 프로필 조회 성공")
                .build());
    }

    @PutMapping("/its/api/profile/{userId}")
    public ResponseEntity<?> updatedProfile(@PathVariable int userId,
                                            @RequestBody ProfileDto.Request profileRequestDto,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();

        User updatedUser = userProfileService.updateProfile(userId,username,profileRequestDto);

        ProfileDto.Response userResponseDto = new ProfileDto.Response(updatedUser);

        return ResponseEntity.ok(CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK)
                        .message("유저 프로필 수정 성공")
                        .data(userResponseDto)
                .build());

    }
}
