package com.TK.frioj.entities;

import org.joda.time.DateTime;

public class ShoutBoxMessage {

	private int shoutBoxMessageId;
	private String sender;
	private String content;
	private DateTime date;
	private boolean visibility;

	public ShoutBoxMessage(int shoutBoxMessageId, String sender,
			String content, boolean visibility) {
		super();
		this.shoutBoxMessageId = shoutBoxMessageId;
		this.sender = sender;
		this.content = content;
		this.date = DateTime.now();
		this.visibility = visibility;
	}

	public int getShoutBoxMessageId() {
		return shoutBoxMessageId;
	}

	public void setShoutBoxMessageId(int shoutBoxMessageId) {
		this.shoutBoxMessageId = shoutBoxMessageId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public boolean isVisible() {
		return visibility;
	}

	public void setVisibility(boolean visible) {
		this.visibility = visible;
	}

	public boolean getVisibility() {
		return visibility;
	}

}
