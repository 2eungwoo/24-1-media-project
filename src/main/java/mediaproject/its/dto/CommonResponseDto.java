package mediaproject.its.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommonResponseDto<T> {
    private String message; //
    private T data;
}

