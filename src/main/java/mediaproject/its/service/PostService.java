package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.LikesRepository;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.repository.PostRepositoryCustom;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final UserUtil userUtil;
    private final PostRepositoryCustom postRepositoryCustom;

    @Transactional(readOnly = true)
    public List<PostDto.Response> getAllPost(){

        List<Post> posts = postRepository.findAll();
        List<PostDto.Response> postsResponseDto = new ArrayList<>();

        for(Post p : posts) {
            PostDto.Response postsDto = PostDto.Response.builder()
                    .postId(p.getId())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .username(p.getUser().getUsername())
                    .viewCount(p.getViewCount())
                    .likesCount(p.getLikesCount())
                    .comments(p.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList()))
                    .hiringType(p.getHiringType())
                    .positionType(p.getPositionType())
                    .processType(p.getProcessType())
                    .recruitingType(p.getRecruitingType())
                    .techStackType(p.getTechStackType())
                    .build();
            postsResponseDto.add(postsDto);
        }

        return postsResponseDto;
    }

    @Transactional
    public PostDto.Response getPostById(int postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        post.viewCountUp();

        return new PostDto.Response(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto.Response> getPostsOrderedByViewCount(){
        return postRepositoryCustom.findPostsByViewCount();
    }

    @Transactional(readOnly = true)
    public List<PostDto.Response> getPostsOrderedByLikesCount(){
        return postRepositoryCustom.findPostsByLikewCount();
    }


    @Transactional
    public PostDto.Response postPost(PostDto.Request postRequest, String username){

        User user = userUtil.findUser(username);

        PostDto.Request postRequestDto = PostDto.Request.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .user(user)
                .comments(postRequest.getComments())
                .hiringType(postRequest.getHiringType())
                .positionType(postRequest.getPositionType())
                .processType(postRequest.getProcessType())
                .recruitingType(postRequest.getRecruitingType())
                .techStackType(postRequest.getTechStackType())
                .build();

        Post newPost = postRequestDto.toEntity();
        postRepository.save(newPost);

        return new PostDto.Response(newPost);
    }

    @Transactional
    public PostDto.Response updatePost(int postId, PostDto.Request request, String username){
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
                request.getRecruitingType(),
                request.getTechStackType(),
                LocalDateTime.now());
        postRepository.save(post);

        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response deletePost(int postId,String username){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User postAuthor = post.getUser();
        User user = userUtil.findUser(username);

        if(!user.equals(postAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        postRepository.deleteById(postId);

        return new PostDto.Response(post);
    }


    @Transactional(readOnly = true)
    public List<PostInterface> findPostsLikedByUser(String username, int userId){

        userUtil.validUser(username);
        userUtil.findUserById(userId);

        List<PostInterface> posts = likesRepository.findPostsLikedByUser(userId);
        return posts;
    }
}
