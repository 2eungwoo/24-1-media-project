package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/its/api/signout")
    public CommonResponseDto<?> memberSignOut(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        memberService.signout(username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("유저 활성 상태 변경 성공")
                .data(null)
                .build();
    }
}
