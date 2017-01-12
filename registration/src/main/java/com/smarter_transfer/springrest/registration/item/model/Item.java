package com.smarter_transfer.springrest.registration.item.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import common.app.model.AbstractTimestampEntity;

@Entity
@Table(name="ITEM")
public class Item extends AbstractTimestampEntity{
	@Id
	private ItemPK itemPK;
	@Column(name="name", length=45)
	private String name;
	@Column(name="description" , length=200)
	private String description;
	@Column(name="price")
	private double price;
	
	public Item(){}
	
	public Item(ItemPK itemPK, String name, String description, double price) {
		super();
		this.itemPK = itemPK;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public ItemPK getItemPK() {
		return itemPK;
	}

	public void setItemPK(ItemPK id) {
		this.itemPK = id;
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
		return "Item [itemPK=" + itemPK.toString() + ", name=" + name + "]";
	}
	
	

}
