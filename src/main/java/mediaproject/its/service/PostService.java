package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.responseDto.UserResponseDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.dto.responseDto.UpdatePostRequestDto;
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

        // todo : fix bug
        // 유저 응답 dto를 만들어서 toEntity()로 엔티티 변환하고, post할때 이름,id만 넣게끔 했는데 안됨. 비밀번호까지 다들어감
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserName(username);
        userResponseDto.setUserId(user.getId());

        User userEntity = userResponseDto.toEntity();

        Post newPost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(userEntity)
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
