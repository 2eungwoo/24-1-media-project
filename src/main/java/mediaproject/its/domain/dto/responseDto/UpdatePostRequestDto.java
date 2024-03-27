package mediaproject.its.domain.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequestDto {
    private String title;
    private String content;
}
