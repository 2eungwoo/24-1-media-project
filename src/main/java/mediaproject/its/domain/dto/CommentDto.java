package mediaproject.its.domain.dto;

import lombok.Getter;
import lombok.Setter;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @Setter
    public static class Request{
        private int commentId;
        private String content;
        private User user;
        private Post post;

        // Dto -> Entity
        public Comment toEntity(){
            return Comment.builder()
                    .id(commentId)
                    .content(content)
                    .user(user)
                    .post(post)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Response{
        private int commentId;
        private String content;
        private String username;
        private int postId;
        private LocalDateTime updatedAt;
        private LocalDateTime createdAt;

        // Entity -> Dto
        public Response(Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
            this.username = comment.getUser().getUsername();
            this.postId = comment.getPost().getId();
            this.createdAt = comment.getCreatedAt();
            this.updatedAt = LocalDateTime.now();
        }
    }

}
