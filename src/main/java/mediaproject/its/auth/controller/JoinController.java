package mediaproject.its.auth.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommonResponseDto;
import mediaproject.its.domain.dto.JoinDto;
import mediaproject.its.auth.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<?> joinUser(JoinDto joinDto){
        boolean result = joinService.joinUser(joinDto);
        if(!result){
            return new ResponseEntity<>(new CommonResponseDto<>("회원가입 실패",joinDto), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CommonResponseDto<>("회원가입 성공",joinDto), HttpStatus.CREATED);
    }

    @PostMapping("/join-admin")
    public ResponseEntity<?> joinAdmin(JoinDto joinDto){
        boolean result = joinService.joinAdmin(joinDto);
        if(!result){
            return new ResponseEntity<>(new CommonResponseDto<>("회원가입 실패",joinDto), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CommonResponseDto<>("회원가입 성공",joinDto), HttpStatus.CREATED);
    }
}
