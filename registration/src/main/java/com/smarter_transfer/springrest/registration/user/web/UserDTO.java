package com.smarter_transfer.springrest.registration.user.web;

import com.smarter_transfer.springrest.registration.user.model.User;

import common.app.model.Location;

/**
 * Data Transfer Object (DTO) for {@link User}
 * @author kaikun
 *
 */
public class UserDTO {
	
	private long userId = 0;
	private long keshId;
	private String name;
	private Location location;
	private String deviceId;
	private long themeId;
	
	public UserDTO(){};
	
	public UserDTO(User user){
		this.userId = user.getUserId();
		this.keshId = user.getKeshId();
		this.name = user.getName();
		this.deviceId = user.getDeviceId();
		this.setLocation(user.getLocation());
		if (user.getTheme() != null){
			this.themeId = user.getTheme().getThemeId();
		}
	}
	
	public long getUserId(){
		return userId;
	}
	
	public long getKeshId() {
		return keshId;
	}

	public String getName() {
		return name;
	}

	public long getThemeId() {
		return themeId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
