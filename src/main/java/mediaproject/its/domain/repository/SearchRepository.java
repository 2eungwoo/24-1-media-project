package mediaproject.its.domain.repository;

import mediaproject.its.domain.dto.PostInterface;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends PostRepository     {

    @Query(value = "select p.* from post p where p.title =:title order by p.created_at desc", nativeQuery = true)
    List<PostInterface> findAllPostBySearch(String title);
}
