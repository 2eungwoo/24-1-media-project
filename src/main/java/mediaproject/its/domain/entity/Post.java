package mediaproject.its.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mediaproject.its.domain.entity.PostFilteringCategory.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "techstack_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private TechStackType techStackType;

    @Column(name = "hiring_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private HiringType hiringType;

    @Column(name = "recurit_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private RecruitType recruitType;

    @Column(name = "process_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    @Column(name = "position_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdAt asc")
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    public void createDate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String title, String content,
                       HiringType hiringType,
                       PositionType positionType,
                       ProcessType processType,
                       RecruitType recruitType,
                       TechStackType techStackType,
                       LocalDateTime updatedAt)
    {
        this.title = title;
        this.content = content;
        this.hiringType = hiringType;
        this.positionType = positionType;
        this.processType = processType;
        this.recruitType = recruitType;
        this.techStackType = techStackType;
        this.updatedAt = updatedAt;
    }

    public void viewCountUp(){
        this.viewCount += 1;
    }
    public void likesCountUp() { this.likesCount += 1; }
}
