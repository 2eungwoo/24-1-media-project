package mediaproject.its.domain.dto;


import lombok.*;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class PostDto {

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Request{
        private String title;
        private String content;
        private User user;

        // Entity -> Dto
        public Post toEntity(){
            return Post.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private int postId;
        private String username;
        private String title;
        private String content;
        private List<CommentDto.Response> comments;

        // Entity -> Dto
        public Response(Post post){
            this.postId = post.getId();
            this.username = post.getUser().getUsername();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.comments = post.getComments().stream()
                    .map(CommentDto.Response::new)
                    .collect(Collectors.toList());
        }
    }

}
