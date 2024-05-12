package mediaproject.its.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Letter;
import org.springframework.stereotype.Repository;


import java.util.List;

import static mediaproject.its.domain.entity.QLetter.letter;

@Repository
@RequiredArgsConstructor
public class LetterRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Letter> findAllLetters(String username){

        return jpaQueryFactory
                .selectFrom(letter)
                .where(letter.recipient.eq(username))
                .where(letter.activeStatus.eq(true))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }
    public List<Letter> findTrashcanLetters(String username){

        return jpaQueryFactory
                .selectFrom(letter)
                .where(letter.recipient.eq(username))
                .where(letter.activeStatus.eq(false))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }


}

