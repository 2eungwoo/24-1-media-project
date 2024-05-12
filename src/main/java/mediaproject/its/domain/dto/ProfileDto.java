package mediaproject.its.domain.dto;

import lombok.*;
import mediaproject.its.domain.entity.User;


public class ProfileDto {

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Request{
        private String description;
        private String email;

        public User toEntity(){
            return User.builder()
                    .description(description)
                    .email(email)
                    .build();
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response{
        private String username;
        private String description;
        private String email;

        public Response(User user){
            this.username = user.getUsername();
            this.description = user.getDescription();
            this.email = user.getEmail();
        }
    }


}
