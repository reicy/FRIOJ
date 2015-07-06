package com.TK.frioj.services;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.ShoutBoxDao;
import com.TK.frioj.entities.ShoutBoxMessage;
import com.TK.frioj.entities.User;
import com.TK.frioj.enums.Roles;

import org.slf4j.Logger;

@Service
public class ShoutBoxService {

	private static final Logger logger = LoggerFactory.getLogger(ShoutBoxService.class);
	
	@Autowired
	private SettingsDao settingsDao;
	
	@Autowired
	private ShoutBoxDao shoutBoxDao;
	
	@PreAuthorize("hasAnyRole('Teacher,Admin')")
	public void deleteAllMessages(){
		shoutBoxDao.deleteAllMessages();
	}
	
	@PreAuthorize("hasAnyRole('Teacher,Admin')")
	public void deleteMessage(int shoutBoxMessageId){
		shoutBoxDao.deleteMessage(shoutBoxMessageId);
	}
	/**
	 * if the contest mode is enabled - messages written by (Junior, Senior) users are invisible to other teams
	 * otherwise all inserted messages are visible
	 * @param user
	 * @param content
	 */
	public void addMessage(User user, String content){
		if(settingsDao.isContestModeEnabled()){
			if(Roles.isTeacherOrAdmin(user)){
				shoutBoxDao.addMessage(user.getLogin(), content, true);
				
			}else{
				shoutBoxDao.addMessage(user.getLogin(), content, false);
			}
		}else{
			shoutBoxDao.addMessage(user.getLogin(), content, true);
		}
	}
	
	@PreAuthorize("hasAnyRole('Teacher,Admin')")
	public void setVisibility(int shoutBoxMessageId, boolean visibility){
		ShoutBoxMessage msg = shoutBoxDao.getMessage(shoutBoxMessageId);
		if(msg!=null)msg.setVisibility(visibility);
	}
	
	/**
	 * returns messages, which are visible or written by user (junior, senior)
	 * returns all messages (teacher, admin)
	 * @param user - who requested messages
	 * @return list of messages
	 */
	public List<ShoutBoxMessage> getAllShoutBoxMessages(User user){
		List<ShoutBoxMessage> list;
	
		if(Roles.isTeacherOrAdmin(user)){
			return shoutBoxDao.getMessages();
		}else{
			list = new LinkedList<ShoutBoxMessage>();
			for (ShoutBoxMessage msg : shoutBoxDao.getMessages()) {
				if(msg.isVisible() || msg.getSender().equals(user.getLogin())){
					list.add(msg);
				}
			}
			return list;
		}
	}
	
}
