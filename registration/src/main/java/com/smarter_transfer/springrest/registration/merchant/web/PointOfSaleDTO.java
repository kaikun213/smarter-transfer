package com.smarter_transfer.springrest.registration.merchant.web;

import com.smarter_transfer.springrest.registration.merchant.model.PointOfSale;

import common.app.model.Location;

public class PointOfSaleDTO {
	
	private long posId;
	private long merchantId;
	private long menuId;
	private Location location;
	
	public PointOfSaleDTO(){}
	
	public PointOfSaleDTO(PointOfSale pos){
		this.posId = pos.getPosId();
		this.merchantId = pos.getMerchant().getMerchantId();
		this.menuId = pos.getMenu().getMenuId();
		this.location = pos.getLocation();
	}
	
	public long getPosId() {
		return posId;
	}
	public void setPosId(long posId) {
		this.posId = posId;
	}
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

}
