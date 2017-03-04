package com.smarter_transfer.springrest.registration.item.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

import common.app.model.AbstractTimestampEntity;

@Entity
@Table(name="ITEM")
public class Item extends AbstractTimestampEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="itemId", nullable=false)
	private long itemId;
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@Column(name="name", length=45)
	private String name;
	@Column(name="description" , length=200)
	private String description;
	@Column(name="price")
	private double price;
	
	public Item(){}

	public Item(Merchant merchant, String name, String description, double price) {
		super();
		this.merchant = merchant;
		this.name = name;
		this.description = description;
		this.price = price;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemId ^ (itemId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (itemId != other.itemId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", merchant=" + merchant + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", created=" + this.getCreated() + ", updated=" + this.getUpdated() +"]";
	}
	
}
