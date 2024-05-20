package mediaproject.its.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(indexes = {
        @Index(columnList = "techstack_type"),
        @Index(columnList = "hiring_type"),
        @Index(columnList = "recuriting_type"),
        @Index(columnList = "process_type"),
        @Index(columnList = "position_type"),
        @Index(columnList = "view_count"),
        @Index(columnList = "likes_count")
        // todo : view_count 인덱스는 모든 게시글 검색에서 index를 태워주기 위해서 쓴건데, 이런식으로 해도 되는걸까?
})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "comments_count")
    private int commentCount;

    @Column(name = "techstack_type", nullable = true)
    // @Enumerated(EnumType.STRING)
    private String techStackType;

    @Column(name = "hiring_type", nullable = true)
    // @Enumerated(EnumType.STRING)
    private String hiringType;

    @Column(name = "recuriting_type", nullable = true)
    // @Enumerated(EnumType.STRING)
    private String recruitingType;

    @Column(name = "process_type", nullable = true)
    // @Enumerated(EnumType.STRING)
    private String processType;

    @Column(name = "position_type", nullable = true)
    // @Enumerated(EnumType.STRING)
    private String positionType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createdAt asc")
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    public void createDate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title,
                       String hiringType,
                       String positionType,
                       String processType,
                       String recruitingType,
                       String techStackType,
                       LocalDateTime updatedAt)
    {
        this.title = title;
        this.hiringType = hiringType;
        this.positionType = positionType;
        this.processType = processType;
        this.recruitingType = recruitingType;
        this.techStackType = techStackType;
        this.updatedAt = updatedAt;
    }

    public void viewCountUp(){
        this.viewCount += 1;
    }
    public void likesCountUp() { this.likesCount += 1; }
    public void updateCommentCount(int num){
        this.commentCount += num;
    }
}
