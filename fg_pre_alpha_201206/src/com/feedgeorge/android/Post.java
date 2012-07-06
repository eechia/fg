package com.feedgeorge.android;

/*
{"content":[
			{"id":"46",
			"type":"1",
			"text":"jln sultan",
			"groupId":"2",
			"authorId":"6",
			"authorName":"chfoo",
			"image":"http:\/\/assets.feedgeorge.com\/photos\/t\/7\/38f82706a95b41cbbd3ada3075612a19.jpg",
			"lat":"3.128668",
			"lng":"101.678940",
			"lastUpdate":"2012-06-05 17:08:35"},
		 */

public class Post {
	
	String id, type, text, groupId, authorId;
	String authorName, image, lat, lng, lastUpdate, commentCount;
	
	
	
	
	
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	

}
