package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.dto.UpdatePostRequestDto;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(long id){
        return postRepository.findById(id)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND.getMessage(), CommonErrorCode.NOT_FOUND));
    }

    @Transactional
    public void postPost(PostDto postDto){

        Post newPost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postRepository.save(newPost);
    }

    @Transactional
    public Post updatePost(long id, UpdatePostRequestDto request){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
        return post;
    }

    @Transactional
    public void deletePost(long id){
        postRepository.deleteById(id);
    }


}
