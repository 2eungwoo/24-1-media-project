package mediaproject.its.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(indexes = {
        @Index(name = "idx_", columnList = "recipient_name,created_at DESC,read_status,active_status"),
})
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "sender_name")
    private String sender;

    @Column(name = "recipient_name")
    private String recipient;

    @Column(name = "read_status")
    private Boolean readStatus;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public void softDelete(){
        this.activeStatus = false;
    }

    public void readLetter(){
        this.readStatus = true;
    }

}
