package mediaproject.its.domain.dto;

import lombok.Getter;
import lombok.Setter;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

@Getter
@Setter
public class CommentDto {
    private User user;
    private Post post;
    private String content;

}
