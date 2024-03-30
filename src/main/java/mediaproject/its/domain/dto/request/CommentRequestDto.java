package mediaproject.its.domain.dto.request;

import lombok.*;
import mediaproject.its.domain.dto.response.PostResponseDto;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String content;
//    private User user;
//    private PostResponseDto post;
    private int userId;
    private int postId;


    // Dto -> Entity
    public Comment toEntity(){
        return Comment.builder()
                .content(content)
                .build();
    }
}
