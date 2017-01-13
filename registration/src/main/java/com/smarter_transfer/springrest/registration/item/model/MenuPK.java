package com.smarter_transfer.springrest.registration.item.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

@SuppressWarnings("serial")
@Embeddable
public class MenuPK implements Serializable {
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menuId", nullable=false)
	private long menuId;
	
	public MenuPK(){}

	public MenuPK(Merchant merchant, long menuId) {
		super();
		this.merchant = merchant;
		this.menuId = menuId;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (menuId ^ (menuId >>> 32));
		result = prime * result + ((merchant == null) ? 0 : merchant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MenuPK))
			return false;
		MenuPK other = (MenuPK) obj;
		if (menuId != other.menuId)
			return false;
		if (merchant == null) {
			if (other.merchant != null)
				return false;
		} else if (!merchant.equals(other.merchant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuPK [merchant=" + merchant.toString() + ", menuId=" + menuId + "]";
	}
	
	
}
