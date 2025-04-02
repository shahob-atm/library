package uz.pdp.online.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Upload extends Auditable {
    @Column(nullable = false)
    @NotBlank(message = "Generated name cannot be blank.")
    @Size(max = 255, message = "Generated name must be at most 255 characters long.")
    private String generatedName;

    @Column(nullable = false)
    @NotBlank(message = "Original name cannot be blank.")
    @Size(max = 255, message = "Original name must be at most 255 characters long.")
    private String originalName;

    @Column(nullable = false)
    @NotBlank(message = "MIME type cannot be blank.")
    @Size(max = 100, message = "MIME type must be at most 100 characters long.")
    private String mimeType;

    @Positive(message = "Size must be a positive number.")
    private long size;

    @Column(nullable = false)
    @NotBlank(message = "Extension cannot be blank.")
    @Size(max = 10, message = "Extension must be at most 10 characters long.")
    private String extension;

    @Builder(builderMethodName = "childBuilder")
    public Upload(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String generatedName, String originalName, String mimeType, long size, String extension) {
        super(id, createdAt, updatedAt);
        this.generatedName = generatedName;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.size = size;
        this.extension = extension;
    }
}
