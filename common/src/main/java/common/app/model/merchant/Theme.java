package common.app.model.merchant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity bean with JPA annotations
 * Theme IDs automatically generated (incremental)
 * @author kaikun
 *
 */

@Entity
@Table(name="THEME")
public class Theme {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="themeId", unique=true, nullable=false)
	private long themeId;
	@ManyToOne
    @JoinColumn(name="merchantId")
	private Merchant merchant;
	
	public Theme(){}
	
	public Theme(long themeId){
		this.themeId = themeId;
	}

	public long getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
	}


}
