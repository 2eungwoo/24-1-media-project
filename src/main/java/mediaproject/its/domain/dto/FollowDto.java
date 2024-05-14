package mediaproject.its.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mediaproject.its.domain.entity.Follow;

public class FollowDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response{
        private String fromUserName;
        private int toUserId;

        public Response(Follow follow){
            this.fromUserName = follow.getFromUser().getUsername();
            this.toUserId = follow.getToUser().getId();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class InterfaceResponse{
        private Integer followerId;
        private String followerName;

        public InterfaceResponse(FollowInterface followInterface){
            this.followerId = followInterface.getId();
            this.followerName = followInterface.getUser_name();
        }
    }
}
