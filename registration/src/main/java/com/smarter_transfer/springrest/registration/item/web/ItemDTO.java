package com.smarter_transfer.springrest.registration.item.web;

import com.smarter_transfer.springrest.registration.item.model.Item;

public class ItemDTO {
	
	private long merchantId;
	private long itemId;
	private String name;
	private String description;
	private double price;
	
	public ItemDTO(){}
	
	public ItemDTO(Item item){
		this.merchantId = item.getMerchant().getMerchantId();
		this.itemId = item.getItemId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.price = item.getPrice();
	}
	
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
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
	
	

}
