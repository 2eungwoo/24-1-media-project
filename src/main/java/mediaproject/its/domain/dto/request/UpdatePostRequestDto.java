package mediaproject.its.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdatePostRequestDto {
    private String title;
    private String content;
    private LocalDateTime updatedAt;
}
