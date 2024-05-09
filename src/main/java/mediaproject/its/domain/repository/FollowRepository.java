package mediaproject.its.domain.repository;

import mediaproject.its.domain.dto.FollowDto;
import mediaproject.its.domain.dto.FollowInterface;
import mediaproject.its.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Integer> {

//    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
//    int mSubscribeState(@Param("principalId") int principalId, @Param("pageUserId") int pageUserId);
//
//    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
//    int mSubscribeCount(@Param("pageUserId") int pageUserId);


    @Modifying
    @Query(value = "INSERT INTO follow(from_user, to_user) VALUES(:fromUserId, :toUserId)",nativeQuery = true)
    void customAddFollow(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "DELETE FROM follow WHERE from_user =:fromUserId AND to_user =:toUserId", nativeQuery = true)
    void customDeleteFollow(int fromUserId, int toUserId);

    @Query(value = "SELECT id FROM follow WHERE from_user =:fromUserId AND to_user =:toUserId ",nativeQuery = true)
    int findIdByFromUserAndToUser(int fromUserId, int toUserId);

    @Query(value ="SELECT COUNT(*) FROM follow WHERE to_user = :userId",nativeQuery = true)
    int customCountFollowers(int userId);

    /* SELECT u.id,u.username FROM follow f INNER JOIN user u ON f.from_user = u.id WHERE f.to_user =:userId */
    @Query(value = "SELECT u.id,u.user_name FROM follow f INNER JOIN user u ON f.from_user = u.id WHERE f.to_user =:userId",nativeQuery = true)
    List<FollowInterface> findFollowersByToUserId(int userId);

}
