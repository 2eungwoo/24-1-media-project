package mediaproject.its.domain.repository;

import mediaproject.its.domain.dto.PostInterface;
import mediaproject.its.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

}
