/**
 * 
 */
package com.feedgeorge.android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author eechia
 *
 */
public class Group implements Parcelable{
	
	String id, name, description, role;
	String lat, lng, polygon, icon;
	
	public Group(){
		
	}
	
	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getPolygon() {
		return polygon;
	}

	public void setPolygon(String polygon) {
		this.polygon = polygon;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
