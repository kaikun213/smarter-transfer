package com.smarter_transfer.springrest.transaction.web;

import java.util.ArrayList;
import java.util.List;

import common.app.model.item.Item;
import common.app.model.order.Order;
import common.app.model.order.Order.Status;

public class OrderDTO {
	
	long orderId;		// not defined
	long merchantId;	// seller
	long userId;		// customer
	long posId;			// WHERE
	int total;			// total amount 
	Status state;		// state of transaction = usually complete!
	List<Long> items = new ArrayList<Long>();	// Items bought
	
	public OrderDTO(){
		
	}
	
	public OrderDTO(Order o){
		super();
		this.orderId = o.getOrderId();
		this.merchantId = o.getMerchant().getMerchantId();
		this.userId = o.getUser().getUserId();
		this.posId = o.getPos().getPosId();
		this.total = o.getTotal();
		this.state = o.getState();
		for (Item i : o.getItems()){
			this.items.add(i.getItemId());
		}
	}
	
	public OrderDTO(long orderId, long merchantId, long userId, long posId, int total, Status state, List<Long> items) {
		super();
		this.orderId = orderId;
		this.merchantId = merchantId;
		this.userId = userId;
		this.posId = posId;
		this.total = total;
		this.state = state;
		this.items = items;
	}

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getPosId() {
		return posId;
	}
	public void setPosId(long posId) {
		this.posId = posId;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Status getState() {
		return state;
	}
	public void setState(Status state) {
		this.state = state;
	}
	public List<Long> getItems() {
		return items;
	}
	public void setItems(List<Long> items) {
		this.items = items;
	}
	

}
