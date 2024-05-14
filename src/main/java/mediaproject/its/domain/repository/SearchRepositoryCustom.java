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

    public List<PostDto.Response> findByFiltering(String title, String hiringType, String positionType, String processType, String recruitingType, String techStackType){
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .where(
                        title(title),
                        hiringType(hiringType),
                        positionType(positionType),
                        processType(processType),
                        recruitingType(recruitingType),
                        techStackType(techStackType)
                )
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
