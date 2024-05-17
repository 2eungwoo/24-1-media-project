package mediaproject.its.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
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

    @Column(name = "active_status")
    private Boolean activeStatus;

    public void updateProfile(String description, String email){
        this.description = description;
        this.email = email;
    }

    public void updateActiveStatus(){
        this.activeStatus = !activeStatus;
    }
}