package mediaproject.its.domain.repository;

import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    /*
    쿼리 개선 전.
    select p.*
    from post p
    order by view_count desc
    limit 10;
    */
/*
쿼리 개선 후
(즉, WHERE-GROUP BY가 인덱스를 탄 상황에서 SELECT 절까지 인덱스를 타도록 하는 것입니다.)
    select p1.*
    from (
            select post.*
            from post
            order by view_count desc
            limit 10
    ) p2 join post p1 on p2.id = p1.id
    */
    //@Query(value = "SELECT p.* FROM post p ORDER BY view_count DESC LIMIT 10;", nativeQuery = true)
    @Query(value = "select p1.* from(select post.* from post order by view_count desc limit 10) p2 join post p1 on p2.id = p1.id",nativeQuery = true)
    List<PostInterface> findHotPostsByViewCount();

    /*
        select p.*
        from post p
        order by likes_count desc
        limit 10;

     */

    @Query(value = "SELECT p.* FROM post p ORDER BY likes_count DESC LIMIT 10;"   , nativeQuery = true)
    List<PostInterface> findHotPostsByLikesCount();

}
