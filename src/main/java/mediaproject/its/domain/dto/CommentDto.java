package mediaproject.its.domain.dto;

import lombok.Getter;
import lombok.Setter;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

import java.time.LocalDateTime;

public class CommentDto {
    private User user;
    private Post post;
    private String content;

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

        // Entity -> Dto
        public Response(Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
            this.username = comment.getUser().getUsername();
            this.postId = comment.getPost().getId();
        }
    }

}
