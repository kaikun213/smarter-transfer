package common.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="addressId", unique = true, nullable = false)
	private long addressId;
	@Column(name="country",length = 45)
	private String country;
	@Column(name="state", length = 45)
	private String state;
	@Column(name="city",length = 45)
	private String city;
	@Column(name="zip",length = 45)
	private String zip;
	@Column(name="addressLine1",length = 45)
	private String addessLine1;
	@Column(name="addressLine2",length = 45)
	private String addessLine2;
	
	public Address(){}
	
	public Address(String country, String state, String city, String zip, String addessLine1, String addessLine2) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.zip = zip;
		this.addessLine1 = addessLine1;
		this.addessLine2 = addessLine2;
	}
		
	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getAddessLine1() {
		return addessLine1;
	}
	public void setAddessLine1(String addessLine1) {
		this.addessLine1 = addessLine1;
	}
	public String getAddessLine2() {
		return addessLine2;
	}
	public void setAddessLine2(String addessLine2) {
		this.addessLine2 = addessLine2;
	}

}
