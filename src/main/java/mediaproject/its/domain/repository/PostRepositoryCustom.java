package mediaproject.its.domain.repository;

import com.querydsl.core.types.dsl.Expressions;
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
    쿼리 개선 전.
    select p.*
    from post p
    order by view_count desc
    limit 10;
    */
    /*
쿼리 개선 후
(즉, ORDER BY가 인덱스를 탄 상황에서 SELECT 절까지 인덱스를 타도록 하는 것입니다.)
    select p1.*
    from (
            select post.*
            from post
            order by view_count desc
            limit 10
    ) p2 join post p1 on p2.id = p1.id
    */
    public List<PostDto.Response> findPostsByViewCount(){
        // sub query
        List<Post> top10Posts = jpaQueryFactory.selectFrom(post)
                .orderBy(post.viewCount.desc())
                .limit(10)
                .fetch();

        // main query
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .join(post)
                .on(post.id.eq(Expressions.constant(top10Posts.get(0).getId())))
                .fetch();

        return transformToDto(posts);
    }

    /* 개선 전 쿼리
        select p.*
        from post p
        order by likes_count desc
        limit 10;
     */

    /*

    # 첫번쨰 쿼리 튜닝
# index를 태워주기 위해 where절에 조건을 걸음
select p.*
from post p
where p.likes_count>0
order by likes_count desc
limit 10;

# 두번쨰 쿼리 튜닝
# where-order by 절에 인덱스를 태운 상황에서, select에도 인덱스를 타게 함
select p1.*
from (
         select post.id
         from post
         where post.view_count>0
         order by view_count desc
         limit 10
     ) p2 join post p1 on p2.id = p1.id

# 세번쨰 쿼리 튜닝
# (이미 인덱스를 태워서 b tree로 써칭하므로?) where로 range scan 없애는게 더 빠름
select p1.*
from (
         select post.id
         from post
         order by view_count desc
         limit 10
     ) p2 join post p1 on p2.id = p1.id


     */
    public List<PostDto.Response> findPostsByLikewCount(){
        // sub query
        List<Post> top10Posts = jpaQueryFactory.selectFrom(post)
                .orderBy(post.likesCount.desc())
                .limit(10)
                .fetch();

        // main query
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .join(post)
                .on(post.id.eq(Expressions.constant(top10Posts.get(0).getId())))
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
