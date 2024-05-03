package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.Likes;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes,Integer> {

    @Modifying
    @Query(value = "insert into likes(user_id, post_id) values(:userId, :postId)", nativeQuery = true)
    void customSave(int userId, int postId);

    /*
    SELECT p.*
    FROM Likes l
    JOIN Posts p ON l.post_id = p.id
    WHERE l.user_id = {userId};  -- 좋아요를 누른 유저의 ID
     */
    @Query(value = "SELECT p.* " +
            "FROM likes l " +
            "JOIN post p ON l.post_id = p.id " +
            "WHERE l.user_id = :userId", nativeQuery = true)
    List<PostInterface> findPostsLikedByUser(int userId);
}
