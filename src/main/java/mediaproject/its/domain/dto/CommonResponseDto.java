package mediaproject.its.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommonResponseDto<T> {
    private String message; //
    private T data;
}

