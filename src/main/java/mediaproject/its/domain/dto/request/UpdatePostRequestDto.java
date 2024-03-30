package mediaproject.its.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequestDto {
    private String title;
    private String content;
}
