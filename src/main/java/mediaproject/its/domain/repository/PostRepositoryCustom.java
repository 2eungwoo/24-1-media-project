package mediaproject.its.domain.repository;

import com.querydsl.jpa.JPAExpressions;
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

    // todo : from절 서브쿼리 안되는데 어떡하지? 네이티브로 돌릴까?
    private final JPAQueryFactory jpaQueryFactory;

    /*
        select p1.*
        from (
                select post.id
                from post
                order by created_at desc
              ) p2 join post p1 on p2.id = p1.id

    */
    /*
     select p1.*
     from (
             select post.id
             from post
             order by view_count desc
           ) p2 join post p1 on p2.id = p1.id

 */
    public List<PostDto.Response> findPostsByViewCount(){
        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .where(post.id.in(
                        JPAExpressions.select(post.id)
                                .from(post)
                                .orderBy(post.viewCount.desc())
                ))
                .fetch();

        return transformToDto(posts);
    }

    /*
     select p1.*
     from (
             select post.id
             from post
             order by likes_count desc
           ) p2 join post p1 on p2.id = p1.id

 */
    public List<PostDto.Response> findPostsByLikewCount(){
        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .where(post.id.in(
                        JPAExpressions.select(post.id)
                                .from(post)
                                .orderBy(post.likesCount.desc())
                ))
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
