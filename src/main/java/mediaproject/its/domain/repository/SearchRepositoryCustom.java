package mediaproject.its.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

import static mediaproject.its.domain.entity.QPost.post;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Post> findByFiltering(String title, String hiringType, String positionType, String processType, String recruitingType, String techStackType){
        return jpaQueryFactory.selectFrom(post)
                .where(title(title))
                .where(hiringType(hiringType))
                .where(positionType(positionType))
                .where(processType(processType))
                .where(recruitingType(recruitingType))
                .where(techStackType(techStackType))
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    private BooleanExpression title(String title){
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression hiringType(String hiringType) {
        return hasText(hiringType) ? post.hiringType.eq(hiringType) : null;
    }

    private BooleanExpression positionType(String positionType) {
        return hasText(positionType) ? post.positionType.eq(positionType) : null;
    }

    private BooleanExpression processType(String processType) {
        return hasText(processType) ? post.processType.eq(processType) : null;
    }

    private BooleanExpression recruitingType(String recruitingType) {
        return hasText(recruitingType) ? post.recruitingType.eq(recruitingType) : null;
    }

    private BooleanExpression techStackType(String techStackType) {
        return hasText(techStackType) ? post.techStackType.eq(techStackType) : null;
    }



}
