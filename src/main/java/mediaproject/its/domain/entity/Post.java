package mediaproject.its.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER) // todo : LAZT로딩을 바꿔야하는데, serialize 에러나서 이거 해결하려고 dto로 보내주는 작업중.
    private User user;

    @PrePersist
    public void createDate(){
        this.createdAt = LocalDateTime.now();
    }
}
