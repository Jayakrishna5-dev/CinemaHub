package cinema.common.auditVariables;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AuditVariables {
	@Column(name = "created_at")
    @JsonFormat(pattern = "dd MMM yyyy h:mm:ss a")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd MMM yyyy h:mm:ss a")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
