package common.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractTimestampEntity {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Version	// updates the timestamp each time
    private LocalDateTime updated;


	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}
	
}
