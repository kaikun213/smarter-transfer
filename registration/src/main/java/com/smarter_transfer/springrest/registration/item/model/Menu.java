package com.smarter_transfer.springrest.registration.item.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="MENU")
public class Menu {
	@Id
	private MenuPK menuPK;
	@Column(name="name", length=45)
	private String name;
	@ManyToMany
	private List<Item> items;
	
	public Menu(){}
	
	public MenuPK getMenuPK() {
		return menuPK;
	}
	public void setMenuPK(MenuPK menuPK) {
		this.menuPK = menuPK;
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
	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Menu [menuPK=" + menuPK.toString() + ", name=" + name + "]";
	}

	
}
