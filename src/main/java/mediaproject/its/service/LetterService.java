package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.LetterDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.LetterRepository;
import mediaproject.its.domain.repository.LetterRepositoryCustom;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static mediaproject.its.domain.entity.QLetter.letter;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserUtil userUtil;
    private final LetterRepositoryCustom letterRepositoryCustom;

    @Transactional(readOnly = true)
    public List<Letter> getLetterList(String username){
        userUtil.findUser(username);

        List<Letter> letters = letterRepositoryCustom.findAllLetters(username);
//        if(letters.get(0) != null){
//            if(!letters.get(0).getRecipient().equals(username)){
//                throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
//            }
//        }

        return letters;
    }

    @Transactional
    public Letter getLetterById(int letterId, String username){
        String recipientName = userUtil.findUser(username).getUsername();

        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        if(!letter.getRecipient().equals(recipientName)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        letter.readLetter();
        letterRepository.save(letter);

        return letter;
    }

    @Transactional(readOnly = true)
    public List<Letter> getSoftDeletedLetterList(String username){
        userUtil.findUser(username);

        List<Letter> letters = letterRepositoryCustom.findTrashcanLetters(username);
//        if(!letters.get(0).getRecipient().equals(username)){
//            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
//        }

        return letters;
    }

    @Transactional
    public Letter postLetter(LetterDto.Request letterRequest, int recipientId, String username){
        User sender = userUtil.findUser(username);
        User recipient = userUtil.findUserById(recipientId);

        if(sender.getId() == recipient.getId()){
            throw new CustomRestApiException(CommonErrorCode.INVALID_PARAMETER,"자기 자신에게 쪽지 보낼 수 없음");
        }

        LetterDto.Request letterRequestDto = LetterDto.Request.builder()
                .letterId(letterRequest.getLetterId())
                .content(letterRequest.getContent())
                .recipient(recipient.getUsername())
                .sender(sender.getUsername())
                .createdAt(LocalDateTime.now())
                .build();

        Letter newLetter = letterRequestDto.toEntity();

        letterRepository.save(newLetter);
        return newLetter;
    }

    @Transactional
    public Letter softDeleteLetter(int letterId, String username){
        String recipientName = userUtil.findUser(username).getUsername();

        Letter letter = letterRepository.findById(letterId)
                        .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));

        if(!letter.getRecipient().equals(recipientName)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        letter.softDelete();
        letterRepository.save(letter);

        return letter;
    }

    @Transactional
    public Letter deleteLetter(int letterId, String username){
        String recipientName = userUtil.findUser(username).getUsername();

        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));

        if(!letter.getRecipient().equals(recipientName)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        letterRepository.deleteById(letterId);

        return letter;
    }
}
