package uz.pdp.online.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthUser extends  Auditable {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_ACTIVE;

    @Builder(builderMethodName = "childBuilder")
    public AuthUser(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String password, Role role, Status status) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public enum Status {
        IN_ACTIVE,
        ACTIVE,
        BLOCKED
    }

    public enum Role {
        USER,
        ADMIN,
        MODERATOR
    }
}
