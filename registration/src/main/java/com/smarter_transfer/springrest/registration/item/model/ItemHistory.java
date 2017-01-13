package com.smarter_transfer.springrest.registration.item.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="ITEM_HISTORY")
public class ItemHistory {
	
	@Id
	private ItemHistoryPK itemHistoryPK;
	@Column(name="name", length=45)
	private String name;
	@Column(name="description" , length=200)
	private String description;
	@Column(name="price")
	private double price;
	
    @Column(name = "created_at", nullable = false)
    private LocalDateTime created;
    @Version
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updated;
	
	public ItemHistory(){}
	
	public ItemHistory(Item item, long revisionNumber){
		this.itemHistoryPK = new ItemHistoryPK(item.getMerchant(), item.getItemId(), revisionNumber);
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
		this.setCreated(item.getCreated());
		this.setUpdated(item.getUpdated());
	}
	
	public ItemHistoryPK getItemHistoryPK() {
		return itemHistoryPK;
	}
	public void setItemHistoryPK(ItemHistoryPK itemHistoryPK) {
		this.itemHistoryPK = itemHistoryPK;
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
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "ItemHistory [itemHistoryPK=" + itemHistoryPK.toString() + ", name=" + name + "]";
	}
	
	

}
