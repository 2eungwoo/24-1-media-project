package mediaproject.its.domain.repository;

import mediaproject.its.domain.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter,Integer> {
}
