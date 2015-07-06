package com.TK.frioj.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.User;
import com.TK.frioj.services.ShoutBoxService;

@Controller
@RequestMapping("/shoutbox")
public class ShoutBoxController {

	private final Logger logger = LoggerFactory.getLogger(ShoutBoxController.class);
	
	@Autowired 
	private UserDao userDao;
	
	@Autowired
	private ShoutBoxService shoutBoxService;
	
	
	
	@RequestMapping(value = "/addMessage", method = RequestMethod.POST)
	public String addMessage(@RequestParam(defaultValue="") String msg ,Model model){
		User user = userDao.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(msg.trim().equals(""))return "redirect:/shoutbox/";
		if(msg.length()>810)msg = msg.substring(0,810);
		
		shoutBoxService.addMessage(user, msg);
		
		return "redirect:/shoutbox/";
	
	}
	
	@RequestMapping(value = "/")
	public String shoutbox(Model model){
		User user = userDao.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("messages",shoutBoxService.getAllShoutBoxMessages(user));
		return "shoutbox";
	}
	
	@RequestMapping(value = "/deleteAllMessages")
	public String deleteAllMessages(){
		
		shoutBoxService.deleteAllMessages();
		return "redirect:/shoutbox/";
	}
	
	@RequestMapping(value = "/deleteMessage/{shoutBoxMessageId}")
	public String deleteMessage(@PathVariable int shoutBoxMessageId){
		
		shoutBoxService.deleteMessage(shoutBoxMessageId);
		return "redirect:/shoutbox/";
	}
	
	/**
	 * changes visibility of shoutbox message
	 * @param shoutBoxMessageId
	 * @param visibility
	 * @return
	 */
	@RequestMapping(value = "/changeMessageVisibility/{shoutBoxMessageId}")
	public String changeMessageVisibility(@PathVariable int shoutBoxMessageId, @RequestParam("value") boolean visibility){
		
		shoutBoxService.setVisibility(shoutBoxMessageId, visibility);
		return "blank";
	}
	
}
