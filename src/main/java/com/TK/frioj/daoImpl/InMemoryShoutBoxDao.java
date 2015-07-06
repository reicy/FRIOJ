package com.TK.frioj.daoImpl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.ShoutBoxDao;
import com.TK.frioj.entities.ShoutBoxMessage;

@Repository
public class InMemoryShoutBoxDao implements ShoutBoxDao {

	private LinkedList<ShoutBoxMessage> messages;
	private int msgIdCounter;

	@Autowired
	private SettingsDao settingsDao;

	public InMemoryShoutBoxDao() {
		messages = new LinkedList<ShoutBoxMessage>();
		msgIdCounter = 0;
	}

	@Override
	public synchronized void addMessage(String sender, String content,
			boolean visibility) {
		ShoutBoxMessage msg = new ShoutBoxMessage(msgIdCounter++, sender,
				content, visibility);
		messages.addFirst(msg);
		if (messages.size() > settingsDao.getMaxMessagesShoutBoxCount())
			messages.removeLast();
	}

	@Override
	public List<ShoutBoxMessage> getMessages() {
		return messages;
	}

	@Override
	public ShoutBoxMessage getMessage(int shoutBoxMessageId) {
		for (ShoutBoxMessage msg : messages) {
			if (msg.getShoutBoxMessageId() == shoutBoxMessageId)
				return msg;
		}
		return null;
	}

	@Override
	public void deleteMessage(int shoutBoxMessageId) {
		ShoutBoxMessage msg = getMessage(shoutBoxMessageId);
		if (msg != null)
			messages.remove(msg);

	}

	@Override
	public void deleteAllMessages() {
		msgIdCounter = 0;
		messages.clear();

	}

}
