package uz.pdp.online.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthUser extends  Auditable {
    @Email(message = "Email formati noto‘g‘ri")
    @NotBlank(message = "Email bo‘sh bo‘lishi mumkin emas")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Parol bo‘sh bo‘lishi mumkin emas")
    @Size(min = 3, max = 100, message = "Parol uzunligi 3 dan 100 gacha bo‘lishi kerak")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Foydalanuvchi roli bo‘lishi shart")
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Foydalanuvchi statusi bo‘lishi shart")
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
        MODERATOR;
    }
}
