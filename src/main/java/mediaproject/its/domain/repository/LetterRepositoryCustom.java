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


    /*
        select *
        from letter
        where recipient_name = "AAA" and active_status = true
        order by created_at desc
     */
    public List<Letter> findAllLetters(String username){

        return jpaQueryFactory
                .selectFrom(letter)
                .where(letter.recipient.eq(username))
                .where(letter.activeStatus.eq(true))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }

    /*
        select *
        from letter
        where recipient_name = "AAA" and active_status = false
        order by created_at desc
    */
    public List<Letter> findTrashcanLetters(String username){

        return jpaQueryFactory
                .selectFrom(letter)
                .where(letter.recipient.eq(username))
                .where(letter.activeStatus.eq(false))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }

    /*
        select *
        from letter
        where recipient_name = "AAA" and read_status = true
        order by created_at desc
     */
    public List<Letter> findReadLetter(String username){

        return jpaQueryFactory
                .selectFrom(letter)
                .where(letter.recipient.eq(username))
                .where(letter.readStatus.eq(true))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }

    /*
        select *
        from letter
        where recipient_name = "AAA" and read_status = false
        order by created_at desc
    */
    public List<Letter> findUnReadLetter(String username){

        return jpaQueryFactory
                .selectFrom(letter)
                .where(letter.recipient.eq(username))
                .where(letter.readStatus.eq(false))
                .orderBy(letter.createdAt.desc())
                .fetch();
    }




}

