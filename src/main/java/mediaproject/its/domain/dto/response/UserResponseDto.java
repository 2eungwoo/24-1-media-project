package mediaproject.its.domain.dto.response;

import lombok.Getter;
import lombok.Setter;
import mediaproject.its.domain.entity.User;

@Getter
@Setter
public class UserResponseDto {

    private int userId;
    private String userName;
    private String userRole;

    public User toEntity() {
        return User.builder()
                .id(userId)
                .username(userName)
                .role(userRole)
                .build();
    }
}
