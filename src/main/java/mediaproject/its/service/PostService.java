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
import mediaproject.its.response.exception.CustomRestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .build();

        Post newPost = postRequestDto.toEntity();

        postRepository.save(newPost);
        return newPost;
    }

    // todo : entity에 직접 set 하지 않게 어떻게하지? -> Post 엔티티 내에서 update 메소드 구현하면 되나?
    @Transactional
    public Post updatePost(int postId, UpdatePostRequestDto request){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public void deletePost(int postId){
        postRepository.deleteById(postId);
    }


}
