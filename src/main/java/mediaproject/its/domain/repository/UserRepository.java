package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query(value = "select * from user where active_status =:activeStatus",nativeQuery = true)
    User findMemberByActiveStatus(Boolean activeStatus);
}
