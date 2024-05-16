package mediaproject.its.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import mediaproject.its.domain.entity.User;

public class WithdrawlDto {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Response{
        private int userId;
        private String username;

        public Response(User user){
            this.userId = user.getId();
            this.username = user.getUsername();
        }
    }
}
