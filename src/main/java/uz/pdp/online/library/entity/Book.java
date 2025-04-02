package uz.pdp.online.library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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
public class Book extends Auditable {
    @NotBlank(message = "Title cannot be blank.")
    @Size(max = 255, message = "Title must be at most 255 characters long.")
    private String title;

    @NotBlank(message = "Description cannot be blank.")
    @Size(max = 1000, message = "Description must be at most 1000 characters long.")
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @NotNull(message = "File cannot be null.")
    private Upload file;

    @Builder(builderMethodName = "childBuilder")
    public Book(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String description, Upload file) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.description = description;
        this.file = file;
    }
}
