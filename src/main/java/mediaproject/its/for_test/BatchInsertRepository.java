package mediaproject.its.for_test;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.entity.Letter;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.PostContent;
import mediaproject.its.domain.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class BatchInsertRepository {

    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void saveAllUsers(List<User> users) {
        String query = "INSERT INTO user (user_name, password, role, user_description, user_email, followers_count, followings_count, active_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                for (User user : users) {
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getRole());
                    ps.setString(4, user.getDescription());
                    ps.setString(5, user.getEmail());
                    ps.setInt(6, user.getFollowersCount());
                    ps.setInt(7, user.getFollowingsCount());
                    ps.setBoolean(8, user.getActiveStatus());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Transactional
    public void saveAllPosts(List<Post> posts){
        String query = "INSERT INTO post (title, view_count, likes_count, comments_count, " +
                "techstack_type, hiring_type, recruiting_type, process_type, position_type, " +
                "created_at, updated_at, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                for (Post post : posts) {
                    ps.setString(1, post.getTitle());
                    ps.setInt(2, post.getViewCount());
                    ps.setInt(3, post.getLikesCount());
                    ps.setInt(4, post.getCommentCount());
                    ps.setString(5, post.getTechStackType());
                    ps.setString(6, post.getHiringType());
                    ps.setString(7, post.getRecruitingType());
                    ps.setString(8, post.getProcessType());
                    ps.setString(9, post.getPositionType());
                    ps.setTimestamp(10, java.sql.Timestamp.valueOf(post.getCreatedAt()));
                    ps.setTimestamp(11, java.sql.Timestamp.valueOf(post.getCreatedAt()));
                    ps.setInt(12, post.getUser().getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }
    @Transactional
    public void saveAllPostContents(List<PostContent> postContents){
        String query = "INSERT INTO post_content (post_id,content) values (?,?)";

        jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                for (PostContent postContent : postContents) {
                    ps.setInt(1, postContent.getPostId());
                    ps.setString(2, postContent.getContent());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }


    @Transactional
    public void saveAllLetters(List<Letter> letters) {
        String query = "INSERT INTO letter (content, sender_name, recipient_name, read_status, active_status, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                for (Letter letter : letters) {
                    ps.setString(1, letter.getContent());
                    ps.setString(2, letter.getSender());
                    ps.setString(3, letter.getRecipient());
                    ps.setBoolean(4, letter.getReadStatus());
                    ps.setBoolean(5, letter.getActiveStatus());
                    ps.setTimestamp(6, java.sql.Timestamp.valueOf(letter.getCreatedAt()));
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }


}

