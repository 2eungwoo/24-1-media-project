package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.dto.request.UpdatePostRequestDto;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(int postId){
        return postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

    }

    // todo : 올바른 에러를 날려주는게 맞는지??... 세션만료 에러를 내야하나?
    @Transactional
    public Post postPost(PostDto.Request postRequest, String username){

        User user = userRepository.findByUsername(username);
        if(user == null){
             throw new CustomRestApiException(UserErrorCode.USER_NOT_FOUND_ERROR, UserErrorCode.USER_NOT_FOUND_ERROR.getMessage());
        }

        PostDto.Request postRequestDto = PostDto.Request.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .user(user)
                .comments(postRequest.getComments())
                .build();

        Post newPost = postRequestDto.toEntity();

        postRepository.save(newPost);
        return newPost;
    }

    // todo : entity에 직접 set 하지 않게 어떻게하지? -> Post 엔티티 내에서 update 메소드 구현하면 되나?
    // todo : todo 해결, 그러나 피드백 필요
    // todo : 올바른 에러를 날려주는게 맞는지??... 세션만료 에러를 내야하나?
    @Transactional
    public Post updatePost(int postId, UpdatePostRequestDto request, String username){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User postAuthor = post.getUser();
        User user = userRepository.findByUsername(username);

        if(!user.equals(postAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_ALREADY_EXISTS_ERROR, UserErrorCode.USER_ALREADY_EXISTS_ERROR.getMessage());
        }

        post.update(request.getTitle(),request.getContent(), LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post deletePost(int postId,String username){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User postAuthor = post.getUser();
        User user = userRepository.findByUsername(username);

        if(!user.equals(postAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_ALREADY_EXISTS_ERROR, UserErrorCode.USER_ALREADY_EXISTS_ERROR.getMessage());
        }

        postRepository.deleteById(postId);
        return post;
    }


}
