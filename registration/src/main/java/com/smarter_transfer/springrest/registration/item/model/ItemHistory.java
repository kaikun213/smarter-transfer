package com.smarter_transfer.springrest.registration.item.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import common.app.model.AbstractTimestampEntity;

@Entity
@Table(name="ITEM_HISTORY")
public class ItemHistory extends AbstractTimestampEntity {
	
	@Id
	private ItemHistoryPK itemHistoryPK;
	@Column(name="name", length=45)
	private String name;
	@Column(name="description" , length=200)
	private String description;
	@Column(name="price")
	private double price;
	
	public ItemHistory(){}
	
	public ItemHistory(Item item){
		this.itemHistoryPK = new ItemHistoryPK(item.getItemPK());
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
	@Override
	public String toString() {
		return "ItemHistory [itemHistoryPK=" + itemHistoryPK.toString() + ", name=" + name + "]";
	}
	
	

}
