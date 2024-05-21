package mediaproject.its.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostContent;
import org.springframework.stereotype.Repository;

import java.util.List;

import static mediaproject.its.domain.entity.QPost.post;
import static mediaproject.its.domain.entity.QPostContent.postContent;

@Repository
@RequiredArgsConstructor
public class PostContentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /*
        select pc.*
        from post_content pc
            inner join post on p.id = pc.post_id
        where p.id =:postId
     */

    public PostContent findWithContent(int postId){
        return jpaQueryFactory.selectFrom(postContent)
                .innerJoin(post).on(post.id.eq(postContent.postId))
                .where(post.id.eq(postId))
                .fetchOne();
    }

}
