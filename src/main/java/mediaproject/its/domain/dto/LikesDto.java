package mediaproject.its.domain.dto;

import lombok.*;
import mediaproject.its.domain.entity.Likes;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

public class LikesDto {

    @Builder
    @AllArgsConstructor
    public static class Request{
        private User user;
        private Post post;

        public Likes toEntity(){
            return Likes.builder()
                    .user(user)
                    .post(post)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String username;
        private int postId;
        private int likesId;

        public Response(Likes likes){
            this.username = likes.getUser().getUsername();
            this.postId = likes.getPost().getId();
            this.likesId = likes.getId();
        }
    }
}
