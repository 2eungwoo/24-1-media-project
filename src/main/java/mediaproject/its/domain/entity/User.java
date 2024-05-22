package mediaproject.its.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(indexes = {
        @Index(name = "idx_id_active_status", columnList = "id,active_status")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "user_description")
    private String description;

    @Column(name = "user_email")
    private String email;

    @Column(name = "followers_count")
    private int followersCount;

    @Column(name = "followings_count")
    private int followingsCount;

    @Column(name = "active_status")
    private Boolean activeStatus;

    public void updateProfile(String description, String email){
        this.description = description;
        this.email = email;
    }

    public void updateActiveStatus(){
        this.activeStatus = !activeStatus;
    }
    public void updateFollowersCount(int num){this.followersCount += num; }
    public void updateFollowingsCount(int num){this.followingsCount += num; }
}