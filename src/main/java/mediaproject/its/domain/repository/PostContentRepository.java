package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostContentRepository extends JpaRepository<PostContent, Integer> {

}
