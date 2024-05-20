package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);

    @Modifying
    @Query(value = "delete from user where active_status =:activeStatus",nativeQuery = true)
    void deleteMembersByActiveStatus(Boolean activeStatus);

    @Query(value = "select * from user where active_status =:activeStatus",nativeQuery = true)
    List<User> findMembersByActiveStatus(Boolean activeStatus);

    @Query(value = "select * from user where active_status =:activeStatus",nativeQuery = true)
    User findMemberByActiveStatus(Boolean activeStatus);

    /*

    SELECT f.from_user_id, f.to_user_id
    FROM follow f
    WHERE f.from_user_id IN (
        SELECT id
        FROM user
        WHERE active_status = false
     )
    OR f.to_user_id IN (
        SELECT id
        FROM user
        WHERE active_status = false
    );

     */
    @Query(value ="select f.from_user, f.to_user from follow f where f.from_user in ( select id from user where active_status =:activeStatus) or f.to_user in (select id from user where active_status =:activeStatus)", nativeQuery = true)
    User findFollowUserIdWithActiveStatusIsFalse(Boolean activeStatus);

}
