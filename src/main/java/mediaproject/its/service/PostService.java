package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.LikesRepository;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    @Transactional
    public Post getPostById(int postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        post.viewCountUp();

        return post;
    }

    @Transactional(readOnly = true)
    public List<PostInterface> getPostsOrderedByViewCount(){
        return postRepository.findHotPostsByViewCount();
    }

    @Transactional(readOnly = true)
    public List<PostInterface> getPostsOrderedByLikesCount(){
        return postRepository.findHotPostsByLikesCount();
    }


    @Transactional
    public Post postPost(PostDto.Request postRequest, String username){

        User user = userUtil.findUser(username);

        PostDto.Request postRequestDto = PostDto.Request.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .user(user)
                .comments(postRequest.getComments())
                .hiringType(postRequest.getHiringType())
                .positionType(postRequest.getPositionType())
                .processType(postRequest.getProcessType())
                .recruitType(postRequest.getRecruitType())
                .techStackType(postRequest.getTechStackType())
                .build();

        Post newPost = postRequestDto.toEntity();

        postRepository.save(newPost);
        return newPost;
    }

    // todo : entity에 직접 set 하지 않게 어떻게하지? -> Post 엔티티 내에서 update 메소드 구현하면 되나?
    // todo : todo 해결, 그러나 피드백 필요
    // todo : 올바른 에러를 날려주는게 맞는지??... 세션만료 에러를 내야하나?
    @Transactional
    public Post updatePost(int postId, PostDto.Request request, String username){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User postAuthor = post.getUser();
        User user = userUtil.findUser(username);

        if(!user.equals(postAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        post.update(request.getTitle(),request.getContent(),
                request.getHiringType(),
                request.getPositionType(),
                request.getProcessType(),
                request.getRecruitType(),
                request.getTechStackType(),
                LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post deletePost(int postId,String username){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User postAuthor = post.getUser();
        User user = userUtil.findUser(username);

        if(!user.equals(postAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        postRepository.deleteById(postId);
        return post;
    }


    @Transactional(readOnly = true)
    public List<PostInterface> findPostsLikedByUser(String username, int userId){

        userUtil.validUser(username);

        List<PostInterface> posts = likesRepository.findPostsLikedByUser(userId);
        return posts;
    }
}
