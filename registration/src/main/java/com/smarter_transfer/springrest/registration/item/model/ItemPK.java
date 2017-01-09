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
public class ItemPK implements Serializable{
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="itemId", nullable=false)
	private long itemId;
	
	public ItemPK(){}
	
	public ItemPK(Merchant merchant, long itemId){
		this.merchant = merchant;
		this.itemId = itemId;
	}


	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		result = prime * result + ((merchant == null) ? 0 : merchant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemPK))
			return false;
		ItemPK other = (ItemPK) obj;
		if (itemId != other.itemId)
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
		return "ItemPK [merchant=" + merchant.toString() + ", itemId=" + itemId + "]";
	}

	

}
