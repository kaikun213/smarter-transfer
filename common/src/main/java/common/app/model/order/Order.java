package common.app.model.order;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import common.app.model.AbstractTimestampEntity;
import common.app.model.item.Item;
import common.app.model.merchant.Merchant;
import common.app.model.merchant.PointOfSale;
import common.app.model.user.User;

// ORDER(orderId,merchantId,POSId,userId,totalAmount,timestamp, status)

public class Order extends AbstractTimestampEntity{
	
	enum Status {
		ACTIVE,			// transaction is initialized and active in the system
		PENDING,		// transaction is processed and partially complete
		COMPLETE,		// successfully completed all transaction steps
		ABORTED			// if any check fails the transaction is rolled back and not processed
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="orderId", nullable=false)
	long orderId;
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	Merchant merchant;
	@ManyToOne
	@JoinColumn(name="posId", nullable=false)
	PointOfSale pos;
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	User user;
	@Column(name="status")
	Status state;
	@ManyToMany
	@JoinTable(name="ORDER_ITEM", joinColumns = {
		@JoinColumn(name="orderId", nullable=false, updatable=false)},
		inverseJoinColumns = { @JoinColumn(name="itemId", nullable=false, updatable=false)})
	List<Item> items;
	@Column(name="total")
	int total;	// total amount of item prices
	
	public Order(){}
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public PointOfSale getPos() {
		return pos;
	}
	public void setPos(PointOfSale pos) {
		this.pos = pos;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Status getState() {
		return state;
	}
	public void setState(Status state) {
		this.state = state;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	
	

}
