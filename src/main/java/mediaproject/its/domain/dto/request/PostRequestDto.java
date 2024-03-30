package mediaproject.its.domain.dto.request;

import lombok.*;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private int userId;

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
