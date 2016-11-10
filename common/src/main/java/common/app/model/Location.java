package common.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location {
	@Column(name="longitude", insertable=false, updatable=false)
	private double lon;	
	@Column(name="latitude", insertable=false, updatable=false)
	private double lat;
	
	public Location(){}
	
	public Location(double lon, double lat){
		this.lon = lon;
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}		
	
	public double distanceTo(Location loc) {
		double theta = lon - loc.getLon();
		double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(loc.getLat())) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(loc.getLat())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		return (dist);
	}
	
	public double distanceTo(Location loc, unitType unit) {
		double theta = lon - loc.getLon();
		double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(loc.getLat())) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(loc.getLat())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == unitType.kilometers) {
			dist = dist * 1.609344;
		} else if (unit == unitType.nauticalMiles) {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians			:*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees			:*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::				Enum of the different units						:*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public enum unitType {
		meter,
		kilometers,
		nauticalMiles
	}


}
