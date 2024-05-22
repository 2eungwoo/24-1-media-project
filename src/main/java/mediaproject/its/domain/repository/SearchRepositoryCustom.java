package mediaproject.its.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
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
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    /*
    select p1.*
    from (
            select post.id
            from post where position_type="BACKEND" and techstack_type="SPRING"
            order by created_at
    ) p2 join post p1 on p1.id=p2.id;
    */

    /* select post.* from post where position_type="BACKEND" and techstack_type="SPRING" order by created_at; */
    public List<PostDto.Response> findByFiltering(String title, String hiringType, String positionType, String processType, String recruitingType, String techStackType){
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .where(
                        title(title),
                        hiringType(hiringType),
                        positionType(positionType),
                        processType(processType),
                        recruitingType(recruitingType),
                        techStackType(techStackType)
                ).orderBy(post.createdAt.desc())
                .fetch();

        return PostRepositoryCustom.transformToDto(posts);
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
