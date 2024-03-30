package mediaproject.its.domain.dto.response;

import lombok.Getter;
import mediaproject.its.domain.entity.Comment;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final String content;
    private final String username;
    // private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private LocalDateTime createdAt = LocalDateTime.now();
    private final int postId;

    // Entity -> Dto
    public CommentResponseDto(Comment comment){
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.postId = comment.getPost().getId();
    }
}
