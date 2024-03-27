package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.dto.UpdatePostRequestDto;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND.getMessage(), CommonErrorCode.NOT_FOUND));
    }

    @Transactional
    public void postPost(PostDto postDto, String username){

        System.out.println("username : "+username);
//        User user = userRepository.findById(userId)
//                .orElseThrow(()->new CustomRestApiException(UserErrorCode.USER_NOT_FOUND_ERROR.getMessage(), UserErrorCode.USER_NOT_FOUND_ERROR));

        User user = userRepository.findByUsername(username);
        if(user == null){
             throw new CustomRestApiException(UserErrorCode.USER_NOT_FOUND_ERROR.getMessage(), UserErrorCode.USER_NOT_FOUND_ERROR);
        }


        Post newPost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(user)
                .build();

        postRepository.save(newPost);
    }

    @Transactional
    public Post updatePost(int postId, UpdatePostRequestDto request){
        Post post = postRepository.findById(postId).orElseThrow();
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
