package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.CommentRepository;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import mediaproject.its.service.Util.UserUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Transactional
    public CommentDto.Response postComment(CommentDto.Request commentRequestDto, int postId, String username){

        User user = userUtil.findUser(username);

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));

        commentRequestDto.setPost(post);
        commentRequestDto.setUser(user);
        commentRequestDto.setContent(commentRequestDto.getContent());
        commentRequestDto.setCommentId(commentRequestDto.getCommentId());

        Comment newComment = commentRequestDto.toEntity();
        commentRepository.save(newComment);

        return new CommentDto.Response(newComment);
    }

    @Transactional
    public CommentDto.Response updateComment(int commentId, CommentDto.Request commentRequestDto ,String username){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User commentAuthor = comment.getUser();
        User user = userUtil.findUser(username);


        if(!user.equals(commentAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        comment.update(commentRequestDto.getContent(),LocalDateTime.now());
        commentRepository.save(comment);

        return new CommentDto.Response(comment);

    }


    @Transactional
    public CommentDto.Response deleteComment(int commentId, String username){

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User commentAuthor = comment.getUser();
        User user = userUtil.findUser(username);

        if(!user.equals(commentAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        commentRepository.deleteById(commentId);
        return new CommentDto.Response(comment);

    }
}
