package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.request.UpdatePostRequestDto;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.CommentRepository;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.error.UserErrorCode;
import mediaproject.its.response.exception.CustomIllegalArgumentException;
import mediaproject.its.response.exception.CustomRestApiException;
import mediaproject.its.response.exception.CustomUnAuthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment postComment(CommentDto.Request commentRequestDto, int postId, String username){

        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomRestApiException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage());
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND,CommonErrorCode.NOT_FOUND.getMessage()));

        commentRequestDto.setPost(post);
        commentRequestDto.setUser(user);
        commentRequestDto.setContent(commentRequestDto.getContent());
        commentRequestDto.setCommentId(commentRequestDto.getCommentId());

        Comment newComment = commentRequestDto.toEntity();
        commentRepository.save(newComment);

        return newComment;
    }

    @Transactional
    public Comment updateComment(int commentId, CommentDto.Request commentRequestDto ,String username){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User commentAuthor = comment.getUser();
        User user = userRepository.findByUsername(username);

        if(!user.equals(commentAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_ALREADY_EXISTS_ERROR, UserErrorCode.USER_ALREADY_EXISTS_ERROR.getMessage());
        }

        comment.update(commentRequestDto.getContent(),LocalDateTime.now());
        commentRepository.save(comment);
        return comment;

    }


    @Transactional
    public Comment deleteComment(int commentId, String username){

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND, CommonErrorCode.NOT_FOUND.getMessage()));

        User commentAuthor = comment.getUser();
        User user = userRepository.findByUsername(username);

        if(!user.equals(commentAuthor)){
            throw new CustomUnAuthorizedException(UserErrorCode.USER_UNAUTHORIZED,UserErrorCode.USER_UNAUTHORIZED.getMessage());
        }

        if(user == null){
            throw new CustomIllegalArgumentException(UserErrorCode.USER_ALREADY_EXISTS_ERROR, UserErrorCode.USER_ALREADY_EXISTS_ERROR.getMessage());
        }

        commentRepository.deleteById(commentId);
        return comment;

    }
}
