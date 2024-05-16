package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.WithdrawlDto;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.MemberHardDeleteSchedular;
import mediaproject.its.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/its/api/inactive")
    public CommonResponseDto<?> memberInactive(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        WithdrawlDto.Response softDeletedMember = memberService.inactive(username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("유저 회원 비활성화 성공")
                .data(softDeletedMember)
                .build();
    }

    @PutMapping("/its/api/rejoin")
    public CommonResponseDto<?> memberRejoin(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        WithdrawlDto.Response rejoinUserDto = memberService.rejoinSoftDeletedUser(username);


        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("유저 재활성화 성공")
                .data(rejoinUserDto)
                .build();
    }


    @PutMapping("/its/api/withdrawl")
    public CommonResponseDto<?> memberWithdrawl(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        WithdrawlDto.Response deletedMemberDto = memberService.deleteMember(username);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("유저 영구 탈퇴 성공")
                .data(deletedMemberDto)
                .build();
    }
}
