package mediaproject.its.for_test;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.Likes;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class BatchInsertRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAllUsers(List<User> users) {
        String query = "INSERT INTO user (user_name, password, role, user_description, user_email, followers_count, followings_count, active_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(query,
                users,
                users.size(),
                (PreparedStatement u, User user) -> {
                    u.setString(1, user.getUsername());
                    u.setString(2, user.getPassword());
                    u.setString(3, user.getRole());
                    u.setString(4, user.getDescription());
                    u.setString(5, user.getEmail());
                    u.setInt(6, user.getFollowersCount());
                    u.setInt(7, user.getFollowingsCount());
                    u.setBoolean(8, user.getActiveStatus());
                });
    }

    @Transactional
    public void saveAllPosts(List<Post> posts){
        String query = "INSERT INTO post (title, view_count, likes_count, comments_count, " +
                "techstack_type, hiring_type, recruiting_type, process_type, position_type, " +
                "created_at, updated_at, user_id, content) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(query,
                posts,
                posts.size(),
                (PreparedStatement p, Post post) -> {
                    p.setString(1, post.getTitle());
                    p.setInt(2, post.getViewCount());
                    p.setInt(3, post.getLikesCount());
                    p.setInt(4, post.getCommentCount());
                    p.setString(5, post.getTechStackType());
                    p.setString(6, post.getHiringType());
                    p.setString(7, post.getRecruitingType());
                    p.setString(8, post.getProcessType());
                    p.setString(9, post.getPositionType());
                    p.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
                    p.setDate(11, java.sql.Date.valueOf(java.time.LocalDate.now()));
                    p.setInt(12, post.getUser().getId());
                    p.setString(13, post.getContent());
                });
    }

    @Transactional
    public void saveAllLetters(List<Letter> letters) {
        String query = "INSERT INTO letter (content, sender_name, recipient_name, read_status, active_status, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(query,
                letters,
                letters.size(),
                (PreparedStatement l, Letter letter) -> {
                    l.setString(1, letter.getContent());
                    l.setString(2, letter.getSender());
                    l.setString(3, letter.getRecipient());
                    l.setBoolean(4, letter.getReadStatus());
                    l.setBoolean(5, letter.getActiveStatus());
                    l.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
                });
    }

}
