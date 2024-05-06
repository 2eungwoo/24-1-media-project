package mediaproject.its.domain.repository;

import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    /*
    select p.*
    from post p
    order by view_count desc
    limit 10;
    */
    @Query(value = "SELECT p.* FROM post p ORDER BY view_count DESC LIMIT 10;", nativeQuery = true)
    List<PostInterface> findHotPostsByViewCount();



}
