package com.smarter_transfer.springrest.registration.pos.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smarter_transfer.springrest.registration.item.model.Menu;

import common.app.model.Location;

@Entity
@Table(name="POS")
public class PointOfSale {
	@Id
	private PointOfSalePK posPK;
	@ManyToOne
    @JoinColumn(name="menuId", nullable=false)
	private Menu menu;
	@Embedded
	private Location location;
	@Column(name="isDeleted", nullable = false)
	private boolean isDeleted;
	
	public PointOfSalePK getPosPK() {
		return posPK;
	}
	public void setPosPK(PointOfSalePK posPK) {
		this.posPK = posPK;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public String toString() {
		return "PointOfSale [posPK=" + posPK.toString() + "]";
	}
	
	
	

}
