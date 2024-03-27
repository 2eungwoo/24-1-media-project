package mediaproject.its.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.dto.UpdatePostRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    @Transactional
    public Post getPostById(long id){
        return postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException());
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
