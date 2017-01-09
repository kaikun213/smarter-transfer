package com.smarter_transfer.springrest.registration.pos.model;

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
public class PointOfSalePK implements Serializable {
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="posId", nullable=false)
	private long posId;
	
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public long getPosId() {
		return posId;
	}
	public void setPosId(long posId) {
		this.posId = posId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((merchant == null) ? 0 : merchant.hashCode());
		result = prime * result + (int) (posId ^ (posId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PointOfSalePK))
			return false;
		PointOfSalePK other = (PointOfSalePK) obj;
		if (merchant == null) {
			if (other.merchant != null)
				return false;
		} else if (!merchant.equals(other.merchant))
			return false;
		if (posId != other.posId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PointOfSalePK [merchant=" + merchant.toString() + ", posId=" + posId + "]";
	}
	
	

}
