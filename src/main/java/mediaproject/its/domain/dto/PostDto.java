package mediaproject.its.domain.dto;


import lombok.*;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;

import java.io.Serializable;
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
        private User user;
        private String content;
        private List<Comment> comments;
        private String hiringType;
        private String positionType;
        private String processType;
        private String recruitingType;
        private String techStackType;

        // Dto -> Entity
        public Post toEntity(){
            return Post.builder()
                    .title(title)
                    .user(user)
                    .comments(new ArrayList<>())
                    .hiringType(hiringType)
                    .positionType(positionType)
                    .processType(processType)
                    .recruitingType(recruitingType)
                    .techStackType(techStackType)
                    .build();
        }
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response implements Serializable {
        private int postId;
        private String username;
        private String title;
        private int viewCount;
        private int likesCount;
        private int commentCount;
        private List<CommentDto.Response> comments = new ArrayList<>();
        private String hiringType;
        private String positionType;
        private String processType;
        private String recruitingType;
        private String techStackType;

        // Entity -> Dto
        public Response(Post post){
            this.postId = post.getId();
            this.username = post.getUser().getUsername();
            this.title = post.getTitle();
            this.viewCount = post.getViewCount();
            this.likesCount = post.getLikesCount();
            this.commentCount = post.getCommentCount();
            this.comments = post.getComments().stream()
                    .map(CommentDto.Response::new)
                    .collect(Collectors.toList());
            this.hiringType = post.getHiringType();
            this.positionType = post.getPositionType();
            this.processType = post.getProcessType();
            this.recruitingType = post.getRecruitingType();
            this.techStackType = post.getTechStackType();
        }

    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterfaceResponse{
        private Integer postId;
        private String username;
        private String title;
        private Integer view_count;
        private Integer likes_count;
        private Integer comments_count;
        private String hiring_type;
        private String position_type;
        private String process_type;
        private String recruiting_type;
        private String techstack_type;

        // Entity -> Dto
        public InterfaceResponse(PostInterface postInterface){
            this.postId = postInterface.getId();
            this.username = postInterface.getUsername();
            this.title = postInterface.getTitle();
            this.view_count = postInterface.getView_count();
            this.likes_count = postInterface.getLikes_count();
            this.comments_count = postInterface.getComments_count();
            this.hiring_type = postInterface.getHiring_type();
            this.position_type = postInterface.getPosition_type();
            this.process_type = postInterface.getProcess_type();
            this.recruiting_type = postInterface.getRecruiting_type();
            this.techstack_type = postInterface.getTechstack_type();
        }
    }

}
