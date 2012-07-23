package com.feedgeorge.android;

public class Comment {

	String commenterID, comment_id, comment,lastUpdate, commenterName;

	public String getCommentID() {
		return commenterID;
	}

	public void setCommentID(String commentID) {
		this.commenterID = commentID;
	}

	public String getId() {
		return comment_id;
	}

	public void setId(String id) {
		this.comment_id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}
}
