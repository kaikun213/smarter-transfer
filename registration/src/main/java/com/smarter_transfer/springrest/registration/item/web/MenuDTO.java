package com.smarter_transfer.springrest.registration.item.web;

import java.util.ArrayList;
import java.util.List;

import com.smarter_transfer.springrest.registration.item.model.Item;
import com.smarter_transfer.springrest.registration.item.model.Menu;

public class MenuDTO {
	
	private long menuId;
	private long merchantId;
	private String name;
	private List<Item> items = new ArrayList<Item>();
	
	public MenuDTO(){}
	
	public MenuDTO(Menu menu){
		this.menuId = menu.getMenuId();
		this.merchantId = menu.getMerchant().getMerchantId();
		this.name = menu.getName();
		// Lazily fetch from DB? => To get Items ItemResource.getItems(merchantId, menuId)
//		for(Item i : menu.getItems()){
//			this.items.add(i);
//		}
	}
	
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> itemIds) {
		this.items = itemIds;
	}

}
