package com.smarter_transfer.springrest.registration.user.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.smarter_transfer.springrest.registration.merchant.model.Theme;

import common.app.model.AbstractTimestampEntity;
import common.app.model.Location;

@Entity
@Table(name="User")
public class User extends AbstractTimestampEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userId", unique=true, nullable=false)
	private long userId;
	@Column(name="keshId", nullable=false, unique=true)
	private long keshId;
	@Column(name="name", length=45, nullable=false)
	private String name;
	@OneToOne
	@JoinColumn(name="themeId")
	private Theme theme;
	@Column(name="deviceId", length=45, nullable=false)
	private String deviceId;
	@Embedded
	private Location deviceLocation;
	@Column(name="isDeleted", nullable = false)
	private boolean isDeleted;
	
	public User(){}
	
	public long getUserId(){
		return userId;
	}
	public long getKeshId() {
		return keshId;
	}
	public void setKeshId(long keshId) {
		this.keshId = keshId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return deviceLocation;
	}
	public void setLocation(Location location) {
		this.deviceLocation = location;
	}
	public void setLocation(double lon, double lat){
		this.deviceLocation = new Location(lon,lat);
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setIsDeleted(boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	public boolean getIsDeleted(){
		return isDeleted;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Location getDeviceLocation() {
		return deviceLocation;
	}
	public void setDeviceLocation(Location deviceLocation) {
		this.deviceLocation = deviceLocation;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


}
