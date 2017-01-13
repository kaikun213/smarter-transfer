package common.app.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@EntityListeners(LastUpdateListener.class)
public abstract class AbstractTimestampEntity {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Version
    private LocalDateTime updated;


	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}
	
	public void setLastModified(LocalDateTime date) {
		updated = date;
	}
}
