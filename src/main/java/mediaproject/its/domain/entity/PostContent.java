package mediaproject.its.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(indexes = {
        @Index(columnList = "post_id")
})
public class PostContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "content")
    private String content;

    public void updateContent(String newContent){
        this.content = newContent;
    }
}