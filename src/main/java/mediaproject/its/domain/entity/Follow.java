package mediaproject.its.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="follow_unique_key",
                        columnNames = {"from_user","to_user"}
                )
        }
)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "from_user")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User fromUser;

    @JoinColumn(name = "to_user")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private User toUser;

}
