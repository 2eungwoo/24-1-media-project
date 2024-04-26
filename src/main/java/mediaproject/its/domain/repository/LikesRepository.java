package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes,Integer> {

    @Modifying
    @Query(value = "insert into likes(user_id, post_id) values(:userId, :postId)", nativeQuery = true)
    void customSave(int userId, int postId);


}
