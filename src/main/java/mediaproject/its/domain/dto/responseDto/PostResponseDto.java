package mediaproject.its.domain.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponseDto {
    private UserResponseDto user;
    private String title;
    private String content;

}
