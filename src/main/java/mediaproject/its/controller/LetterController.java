package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.LetterDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.LetterService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;


    @GetMapping("/its/api/letter")
    public CommonResponseDto<?> getLetterList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        List<Letter> letters = letterService.getLetterList(username);
        List<LetterDto.Response> lettersResponseDto = new ArrayList<>();

        for(Letter l : letters){
            LetterDto.Response lettersDto = LetterDto.Response.builder()
                    .sender(l.getSender())
                    .recipient(l.getRecipient())
                    .content(l.getContent())
                    .activeStatus(l.getActiveStatus())
                    .readStatus(l.getReadStatus())
                    .createdAt(l.getCreatedAt())
                    .build();
            lettersResponseDto.add(lettersDto);
        }

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("쪽지 리스트 조회 성공")
                .data(lettersResponseDto)
                .build();
    }

    // 쪽지 단건 조회
    @GetMapping("/its/api/letter/{letterId}")
    public CommonResponseDto<?> getLetterById(@PathVariable int letterId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        Letter letter = letterService.getLetterById(letterId,username);

        LetterDto.Response letterResponseDto = new LetterDto.Response(letter);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("쪽지 단건 조회 성공")
                .data(letterResponseDto)
                .build();
    }

    // 쪽지 보내기
    @PostMapping("/its/api/letter/{recipientId}")
    public CommonResponseDto<?> postLetter(@RequestBody LetterDto.Request letterRequest, @PathVariable int recipientId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        Letter letter = letterService.postLetter(letterRequest,recipientId,username);

        LetterDto.Response letterResponseDto = new LetterDto.Response(letter);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("쪽지 보내기 성공")
                .data(letterResponseDto)
                .build();
    }

    @PutMapping("/its/api/letter/{letterId}")
    public CommonResponseDto<?> softDeleteLetter(@PathVariable int letterId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        Letter letter = letterService.softDeleteLetter(letterId, username);

        LetterDto.Response letterResponseDto = new LetterDto.Response(letter);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("쪽지 휴지통 보내기 성공")
                .data(letterResponseDto)
                .build();
    }

    @DeleteMapping("/its/api/letter/{letterId}")
    public CommonResponseDto<?> deleteLetter(@PathVariable int letterId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        Letter letter = letterService.deleteLetter(letterId, username);

        LetterDto.Response letterResponseDto = new LetterDto.Response(letter);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("쪽지 삭제 성공")
                .data(letterResponseDto)
                .build();
    }

    @GetMapping("/its/api/letter/trashcan")
    public CommonResponseDto<?> getSoftDeletedLetterList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        String username = customUserDetails.getUser().getUsername();
        List<Letter> letters = letterService.getSoftDeletedLetterList(username);
        List<LetterDto.Response> lettersResponseDto = new ArrayList<>();

        for(Letter l : letters){
            LetterDto.Response lettersDto = LetterDto.Response.builder()
                    .sender(l.getSender())
                    .recipient(l.getRecipient())
                    .content(l.getContent())
                    .activeStatus(l.getActiveStatus())
                    .readStatus(l.getReadStatus())
                    .createdAt(l.getCreatedAt())
                    .build();
            lettersResponseDto.add(lettersDto);
        }

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("쪽지 휴지통 조회 성공")
                .data(lettersResponseDto)
                .build();
    }

}
