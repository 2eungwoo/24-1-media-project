package mediaproject.its.domain.dto;


import lombok.*;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostInterface;
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
        private List<Comment> comments;

        // Dto -> Entity
        public Post toEntity(){
            return Post.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .comments(comments)
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
        private int viewCount;
        private List<CommentDto.Response> comments;

        // Entity -> Dto
        public Response(Post post){
            this.postId = post.getId();
            this.username = post.getUser().getUsername();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.viewCount = post.getViewCount();
            this.comments = post.getComments().stream()
                    .map(CommentDto.Response::new)
                    .collect(Collectors.toList());
        }

    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterfaceResponse{
        private int postId;
        private String title;

        // Entity -> Dto
        public InterfaceResponse(PostInterface postInterface){
            this.postId = postInterface.getId();
            this.title = postInterface.getTitle();
        }
    }

}
