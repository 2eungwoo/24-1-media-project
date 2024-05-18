package mediaproject.its.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mediaproject.its.domain.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /*
        select p.*
        from post p
        order by view_count desc
        limit 10;
     */
    public List<PostDto.Response> findPostsByViewCount(){
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .orderBy(post.viewCount.desc())
                .limit(10)
                .fetch();

        return transformToDto(posts);
    }

    /*
        select p.*
        from post p
        order by likes_count desc
        limit 10;

     */
    public List<PostDto.Response> findPostsByLikewCount(){
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .orderBy(post.likesCount.desc())
                .limit(10)
                .fetch();


        return transformToDto(posts);

    }

    static List<PostDto.Response> transformToDto(List<Post> posts) {
        List<PostDto.Response> postsResponseDto = new ArrayList<>();

        for(Post p : posts){
            PostDto.Response postsDto = PostDto.Response.builder()
                    .postId(p.getId())
                    .comments(p.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList()))
                    .username(p.getUser().getUsername())
                    .title(p.getTitle())
                    .likesCount(p.getLikesCount())
                    .viewCount(p.getViewCount())
                    .hiringType(p.getHiringType())
                    .positionType(p.getPositionType())
                    .processType(p.getProcessType())
                    .recruitingType(p.getRecruitingType())
                    .techStackType(p.getTechStackType())
                    .build();

            postsResponseDto.add(postsDto);
        }

        return postsResponseDto;
    }
}
