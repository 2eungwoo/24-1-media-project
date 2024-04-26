package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Likes;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.LikesRepository;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomRestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addPostLikes(String username, int postId){
//
//        userRepository.findById(userId)
//                .orElseThrow(()-> new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR,UserErrorCode.USER_NOT_FOUND_ERROR.getMessage()));

        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomIllegalArgumentException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));
        User postAuthor = post.getUser();

        if(user == postAuthor){
            throw new CustomRestApiException(CommonErrorCode.INVALID_PARAMETER,"자신의 게시글에는 좋아요 할 수 없음");
        }

        postRepository.findById(postId)
                .orElseThrow(()-> new CustomIllegalArgumentException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));

        likesRepository.customSave(user.getId(),postId);
    }

    @Transactional
    public Likes deletePostLikes(String username, int likesId){

        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        Likes likes = likesRepository.findById(likesId)
                        .orElseThrow(()-> new CustomIllegalArgumentException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));

        User likesOwner = likes.getUser();

        if(!likesOwner.equals(user)){
            throw new CustomRestApiException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        likesRepository.deleteById(likesId);
        return likes;
    }

}
