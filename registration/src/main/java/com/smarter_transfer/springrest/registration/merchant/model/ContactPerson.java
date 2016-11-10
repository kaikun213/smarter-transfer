package com.smarter_transfer.springrest.registration.merchant.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Contact")
public class ContactPerson implements Serializable{

	/**
	 * Eclipse generated serialVersionUID
	 */
	private static final long serialVersionUID = -176066348431940464L;

	@Id
	private long merchantId;
    @OneToOne
    @MapsId
    @JoinColumn(name="merchantId", updatable = false, insertable = false)
	private Merchant merchant;
    @Column(name="forename")
	private String forename;
	@Column(name="surname")
	private String surname;
	@Column(name="email")
	private String email;
	
	public ContactPerson(){}
	
	public ContactPerson(String forename, String surname, String email){
		this.forename = forename;
		this.surname = surname;
		this.email = email;
	}
	
	public Merchant getMerchant(){
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public String getForename() {
		return forename;
	}
	public void setForename(String forename) {
		this.forename = forename;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
