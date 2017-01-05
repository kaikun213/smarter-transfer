package com.smarter_transfer.springrest.registration.merchant.web;

import java.util.ArrayList;
import java.util.List;

import com.smarter_transfer.springrest.registration.merchant.model.Merchant;
import com.smarter_transfer.springrest.registration.merchant.model.Theme;

public class MerchantDTO {
	
	private long merchantId = 0;
	private long keshId;
	private String companyName;
	private String phoneNumber;
	private String email;
	private String contactForename;
	private String contactSurname;
	private String contactEmail;
	private String country;
	private String state;
	private String city;
	private String zip;
	private String addressLine1;
	private String addressLine2;
	private String ustId;		//merchant tax id
	private long logoId;
	private List<Long> themeIds;
	private String websiteURL;
	private String shopURL;
	private String ticketURL;
	
	public MerchantDTO(){
		themeIds = new ArrayList<Long>();
	}
	
	public MerchantDTO(Merchant merchant){
		this.merchantId = merchant.getMerchantId();
		this.keshId = merchant.getKeshId();
		this.companyName = merchant.getCompanyName();
		this.phoneNumber = merchant.getPhoneNumber();
		this.email = merchant.getEmail();
		if (merchant.getContact() != null){
			this.setContactForename(merchant.getContact().getForename());
			this.setContactSurname(merchant.getContact().getSurname());
			this.setContactEmail(merchant.getContact().getEmail());
		}
		if (merchant.getAddress() != null){
			this.country = merchant.getAddress().getCountry();
			this.state = merchant.getAddress().getState();
			this.city = merchant.getAddress().getCity();
			this.zip = merchant.getAddress().getZip();
			this.addressLine1 = merchant.getAddress().getAddessLine1();
			this.addressLine2 = merchant.getAddress().getAddessLine2();
		}
		this.ustId = merchant.getUstId();
		this.logoId = merchant.getLogoId();
		if (merchant.getThemes() != null){
			themeIds = new ArrayList<Long>();
			for (Theme t : merchant.getThemes()) themeIds.add(t.getThemeId());
		}
		this.websiteURL = merchant.getWebsiteURL();
		this.shopURL = merchant.getShopURL();
		this.ticketURL = merchant.getTicketURL();
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
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
	public List<Long> getThemeIds() {
		return themeIds;
	}
	public void setThemes(List<Long> themeIds) {
		this.themeIds = themeIds;
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

	public String getContactForename() {
		return contactForename;
	}

	public void setContactForename(String contactForename) {
		this.contactForename = contactForename;
	}

	public String getContactSurname() {
		return contactSurname;
	}

	public void setContactSurname(String contactSurname) {
		this.contactSurname = contactSurname;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	

}
