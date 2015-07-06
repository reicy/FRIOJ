package com.TK.frioj.dao;

import java.util.List;

import com.TK.frioj.entities.ShoutBoxMessage;

public interface ShoutBoxDao {
	
	void addMessage(String sender, String content, boolean visibility);
	
	ShoutBoxMessage getMessage(int shoutBoxMessageId);
	
	List<ShoutBoxMessage> getMessages();
	
	void deleteMessage(int shoutBoxMessageId);
	
	void deleteAllMessages();
	
}
