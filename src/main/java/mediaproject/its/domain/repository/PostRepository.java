package mediaproject.its.domain.repository;

import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query(value = "        select p1.*\n" +
            "        from (\n" +
            "                select post.id\n" +
            "                from post\n" +
            "                order by created_at desc\n" +
            "              ) p2 join post p1 on p2.id = p1.id" , nativeQuery = true)
    List<PostInterface> findAllPostsOrderByLatest();

    @Query(value = "        select p1.*\n" +
            "        from (\n" +
            "                select post.id\n" +
            "                from post\n" +
            "                order by view_count desc\n" +
            "              ) p2 join post p1 on p2.id = p1.id" , nativeQuery = true)
    List<PostInterface> findPostsByViewCount();

    @Query(value = "        select p1.*\n" +
            "        from (\n" +
            "                select post.id\n" +
            "                from post\n" +
            "                order by likes_count desc\n" +
            "              ) p2 join post p1 on p2.id = p1.id" , nativeQuery = true)
    List<PostInterface> findPostsByLikewCount();
}
