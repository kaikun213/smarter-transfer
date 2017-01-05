package com.smarter_transfer.springrest.registration.user.web;

import com.smarter_transfer.springrest.registration.user.model.User;

/**
 * Data Transfer Object (DTO) for {@link User}
 * @author kaikun
 *
 */
public class UserDTO {
	
	private long userId = 0;
	private long keshId;
	private String name;
	private double lon;
	private double lat;
	private String deviceId;
	private long themeId;
	
	public UserDTO(){};
	
	public UserDTO(User user){
		this.userId = user.getUserId();
		this.keshId = user.getKeshId();
		this.name = user.getName();
		this.deviceId = user.getDeviceId();
		if (user.getLocation() != null){
			this.lon = user.getLocation().getLon();
			this.lat = user.getLocation().getLat();
		}
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

	public double getLon() {
		return lon;
	}
	
	public double getLat() {
		return lat;
	}

	public long getThemeId() {
		return themeId;
	}

	public String getDeviceId() {
		return deviceId;
	}

}
