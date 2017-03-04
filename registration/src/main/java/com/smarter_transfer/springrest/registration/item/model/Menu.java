package com.smarter_transfer.springrest.registration.item.model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smarter_transfer.springrest.registration.merchant.model.Merchant;

@Entity
@Table(name="MENU")
public class Menu {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="menuId", nullable=false)
	private long menuId;
	@ManyToOne
    @JoinColumn(name="merchantId", nullable=false)
	private Merchant merchant;
	@Column(name="name", length=45)
	private String name;
	@ManyToMany
	@JoinTable(name="MENU_ITEM", joinColumns = {
		@JoinColumn(name="menuId", nullable=false, updatable=false)},
		inverseJoinColumns = { @JoinColumn(name="itemId", nullable=false, updatable=false)})
	private List<Item> items = new ItemList();
	
	public Menu(){}
	
	public Menu(Merchant merchant, long menuId) {
		super();
		this.merchant = merchant;
		this.menuId = menuId;
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

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (menuId ^ (menuId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (menuId != other.menuId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", merchant=" + merchant + ", name=" + name + "]";
	}
	
	/* Subclass implementation which throws an error if Item and Menu do not have the same Merchant owner => Double CHECK */
	protected class ItemList extends AbstractList<Item>
    {
        protected ArrayList<Item> list;

        public ItemList()
        {
            super();
            list = new ArrayList<>();
        }

        public ItemList(Collection<? extends Item> c)
        {
            super();
            list = new ArrayList<>(c.size());
            addAll(c);
        }

        @Override
        public boolean add(Item item)
        {
            if(!Objects.equals(merchant, item.getMerchant()))
            {
                throw new IllegalArgumentException("You can not add an item to a menu when the merchants do not match!");
                // or return false;
            }

            return list.add(item);
        }

        @Override
        public Item get(int index)
        {
            return list.get(index);
        }

        @Override
        public int size()
        {
            return list.size();
        }
    }

	
}
