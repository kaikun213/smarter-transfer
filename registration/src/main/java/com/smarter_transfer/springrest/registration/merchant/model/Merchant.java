package com.smarter_transfer.springrest.registration.merchant.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import common.app.model.Address;

@Entity
@Table(name="Merchant")
public class Merchant {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="merchantId", unique = true, nullable = false)
	private long merchantId;
	@Column(name="keshId", nullable = false, unique = true)
	private long keshId;
	@Column(name="companyName", nullable = false, length = 128)
	private String companyName;
	@Column(name="phoneNumber",length = 45)
	private String phoneNumber;
	@Column(name="email",length = 45)
	private String email;
	@OneToOne(mappedBy="merchant" , cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name="merchantId")
	private ContactPerson contact;
	@OneToOne(cascade = {CascadeType.ALL} , orphanRemoval = true)
	@JoinColumn(name="addressId")
	private Address address;
	@Column(name="ustId", length = 45)
	private String ustId;		//merchant tax id
	@Column(name="logoId")
	private long logoId;
	@OneToMany(mappedBy="merchant", fetch=FetchType.EAGER)
	private List<Theme> themes;
	@Column(name="websiteURL")
	private String websiteURL;
	@Column(name="shopURL")
	private String shopURL;
	@Column(name="ticketURL")
	private String ticketURL;
	@Column(name="isDeleted")
	private boolean isDeleted;
	@Column(name="created_at")
	private Date createdAt;
	@Column(name="updated_at")
	private Date updatedAt;
	
	public Merchant(){		
	}
	
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public long getKeshId() {
		return keshId;
	}
	public void setKeshId(long keshId) {
		this.keshId = keshId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ContactPerson getContact() {
		return contact;
	}
	public void setContact(ContactPerson contact) {
		this.contact = contact;
	}
	public String getUstId() {
		return ustId;
	}
	public void setUstId(String ustId) {
		this.ustId = ustId;
	}
	public long getLogoId() {
		return logoId;
	}
	public void setLogoId(long logoId) {
		this.logoId = logoId;
	}
	public List<Theme> getThemes() {
		return themes;
	}
	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public void addTheme(Theme theme){
		this.themes.add(theme);
	}
	public String getWebsiteURL() {
		return websiteURL;
	}
	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}
	public String getShopURL() {
		return shopURL;
	}
	public void setShopURL(String shopURL) {
		this.shopURL = shopURL;
	}
	public String getTicketURL() {
		return ticketURL;
	}
	public void setTicketURL(String ticketURL) {
		this.ticketURL = ticketURL;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
}