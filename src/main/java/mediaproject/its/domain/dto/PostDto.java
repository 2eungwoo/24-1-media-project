package mediaproject.its.domain.dto;


import lombok.*;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostFilteringCategory.*;
import mediaproject.its.domain.entity.User;

import java.util.ArrayList;
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
        private HiringType hiringType;
        private PositionType positionType;
        private ProcessType processType;
        private RecruitType recruitType;
        private TechStackType techStackType;

        // Dto -> Entity
        public Post toEntity(){
            return Post.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .comments(new ArrayList<>())
                    .hiringType(hiringType)
                    .positionType(positionType)
                    .processType(processType)
                    .recruitType(recruitType)
                    .techStackType(techStackType)
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
        private int likesCount;
        private List<CommentDto.Response> comments = new ArrayList<>();

        // Entity -> Dto
        public Response(Post post){
            this.postId = post.getId();
            this.username = post.getUser().getUsername();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.viewCount = post.getViewCount();
            this.likesCount = post.getLikesCount();
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
        private Integer postId;
        private String title;
        private Integer view_count;
        private Integer likes_count;

        // Entity -> Dto
        public InterfaceResponse(PostInterface postInterface){
            this.postId = postInterface.getId();
            this.title = postInterface.getTitle();
            this.view_count = postInterface.getView_count();
            this.likes_count = postInterface.getLikes_count();
        }
    }

}
