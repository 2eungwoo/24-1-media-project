package mediaproject.its.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mediaproject.its.repository.Post;
import mediaproject.its.dto.PostDto;
import mediaproject.its.repository.PostRepository;
import mediaproject.its.dto.UpdatePostRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("id 찾을 수 없음"));
    }

    @Transactional
    public String postPost(PostDto postDto){

        Post test = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postRepository.save(test);
        return "good";
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
