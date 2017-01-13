package com.smarter_transfer.springrest.registration.item.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

@SuppressWarnings("serial")
@Embeddable
public class ItemHistoryPK implements Serializable {
	
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@Column(name="itemId", nullable=false)
	private long itemId;
	@Column(name="revisionNumber", nullable=false)
	private long revisionNumber;
	
	public ItemHistoryPK(){}
	
	public ItemHistoryPK(Merchant merchant, long itemId, long revisionNumber){
		this.merchant = merchant;
		this.itemId = itemId;
		this.revisionNumber = revisionNumber;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		result = prime * result + ((merchant == null) ? 0 : merchant.hashCode());
		result = prime * result + (int) (revisionNumber ^ (revisionNumber >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemHistoryPK))
			return false;
		ItemHistoryPK other = (ItemHistoryPK) obj;
		if (itemId != other.itemId)
			return false;
		if (merchant == null) {
			if (other.merchant != null)
				return false;
		} else if (!merchant.equals(other.merchant))
			return false;
		if (revisionNumber != other.revisionNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemHistoryPK [merchant=" + merchant.getMerchantId() + ", itemId=" + itemId + ", revisionNumber=" + revisionNumber
				+ "]";
	}

	
	
	

}
