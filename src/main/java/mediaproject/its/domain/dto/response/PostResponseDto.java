package mediaproject.its.domain.dto.response;

import lombok.*;
import mediaproject.its.domain.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private List<CommentResponseDto> comments;
    private int userId;
    private String username;

    // Entity -> Dto
    public PostResponseDto(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
    }

}
