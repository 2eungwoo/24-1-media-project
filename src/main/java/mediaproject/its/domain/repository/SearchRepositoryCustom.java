package mediaproject.its.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import mediaproject.its.domain.entity.Post;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static mediaproject.its.domain.entity.QPost.post;

@Repository
public class SearchRepositoryCustom extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public SearchRepositoryCustom(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Post> findByTitle(String title){
        return jpaQueryFactory.selectFrom(post)
                .where(
                        post.title.contains(title)
                )
                .orderBy(post.createdAt.desc())
                .fetch();
    }
}
