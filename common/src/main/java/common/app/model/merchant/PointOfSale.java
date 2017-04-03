package common.app.model.merchant;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import common.app.model.Location;
import common.app.model.item.Menu;

@Entity
@Table(name="POS")
public class PointOfSale {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="posId", nullable=false)
	private long posId;
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@ManyToOne
    @JoinColumn(name="menuId", nullable=false)
	private Menu menu;
	@Embedded
	private Location location;
	@Column(name="isDeleted", nullable = false)
	private boolean isDeleted;
	
	public PointOfSale(){}
	
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public long getPosId() {
		return posId;
	}
	public void setPosId(long posId) {
		this.posId = posId;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return "PointOfSale [posId=" + posId + ", merchant=" + merchant.toString() + ", isDeleted=" + isDeleted + "]";
	}
	
	

}
