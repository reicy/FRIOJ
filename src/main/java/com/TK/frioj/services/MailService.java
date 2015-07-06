package com.TK.frioj.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.SettingsDao;

@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private SettingsDao settingsDao;
	
	@Autowired
	private MailSender mailSender;
	
	public void sendMail(String content,String subject, String target){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject(subject);
		msg.setTo(target);
		msg.setText(content);
		
		Sender sender = new Sender(msg, mailSender);
		sender.start();
		
	}
	
	public void sendMailToAdmin(String content){
		sendMail(content, "friojContactMe" ,settingsDao.getAdminMailboxAddress());
	}
	
	private class Sender extends Thread{
		
		private SimpleMailMessage msg;
		
		private MailSender mailSender;
		
		public Sender(SimpleMailMessage msg, MailSender mailSender){
			this.msg = msg;
			this.mailSender = mailSender;
		}
		
		public void run(){
			try{
	            this.mailSender.send(msg);
	        }
	        catch (MailException ex) {
	        	ex.printStackTrace();
	            System.err.println(ex.getMessage());
	        }
		}
	}
}
