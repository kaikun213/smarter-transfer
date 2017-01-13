package common.app.model;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class LastUpdateListener {
	
	@PreUpdate
	@PrePersist
	public void setLastUpdated(AbstractTimestampEntity a){
		a.setLastModified(LocalDateTime.now());
	}

}
