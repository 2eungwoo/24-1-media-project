package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostContentRepository extends JpaRepository<PostContent, Integer> {

    /*
        select pc.id, pc.post_id, pc.content
        from post_content pc
        inner join post p on pc.post_id = p.id
        where pc.post_id=2
     */

    @Query(value = "select distinct * from post_content pc inner join post p on pc.post_id = p.id where pc.post_id =:postId ",nativeQuery = true)
    Post getPostWithContent(int postId);
}
