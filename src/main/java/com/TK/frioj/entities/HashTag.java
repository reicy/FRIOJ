package com.TK.frioj.entities;

public class HashTag {

	private int hashTagId;
	private String hashTagName;

	public HashTag(int hashTagId, String hashTagName) {
		super();
		this.hashTagId = hashTagId;
		this.hashTagName = hashTagName;
	}

	public HashTag() {
		super();
	}

	public int getHashTagId() {
		return hashTagId;
	}

	public void setHashTagId(int hashTagId) {
		this.hashTagId = hashTagId;
	}

	public String getHashTagName() {
		return hashTagName;
	}

	public void setHashTagName(String hashTagName) {
		this.hashTagName = hashTagName;
	}

}
