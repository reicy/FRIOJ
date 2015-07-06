package com.TK.frioj.controllers;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.TK.frioj.dao.SettingsDao;

@RequestMapping("/admin/settings")
@Controller	
public class SettingsController {

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	@Autowired
	private SettingsDao settingsDao;
	
	@Autowired
	private DataSource dataSource;
	
	@RequestMapping(method=RequestMethod.POST , value="/changeJuniorPlus")
	public String changeJuniorPlus(@RequestParam("value") boolean enabled,Model model){
		
		settingsDao.setJuniorPlusMode(enabled);
		
		return "blank";
	}
	
	
	@RequestMapping("/")
	public String getSettings(Model model){
		
		model.addAttribute("contestMode", settingsDao.isContestModeEnabled());
		model.addAttribute("juniorPlus", settingsDao.isJuniorPlusEnabled());
		
		model.addAttribute("cMultiplier", settingsDao.getCMultiplier());
		model.addAttribute("cppMultiplier", settingsDao.getCppMultiplier());
		model.addAttribute("javaMultiplier", settingsDao.getJavaMultiplier());
		
		model.addAttribute("friojChrootLocation",settingsDao.getFriojChrootLocation());
		model.addAttribute("friojFilesLocation",settingsDao.getFriojFilesLocation());
		
		model.addAttribute("maxArticles",settingsDao.getMaxArticlesPerPageCount());
		model.addAttribute("maxMessages",settingsDao.getMaxMessagesShoutBoxCount());
		model.addAttribute("maxProblems",settingsDao.getMaxProblemPerPageCount());
		model.addAttribute("maxSubmissions",settingsDao.getMaxSubmissionsPerPageCount());
		
		model.addAttribute("maxFileSize",settingsDao.getStandardMaxInputFileSize());
		model.addAttribute("defaultMemoryLimit",settingsDao.getStandardMemoryLimit());
		model.addAttribute("defaultTimeLimit",settingsDao.getStandardTimeLimit());
		model.addAttribute("email", settingsDao.getAdminMailboxAddress());
		
		model.addAttribute("maxCompileTime", settingsDao.getMaxCompileTime());
		model.addAttribute("maxCompileVirtualMemory", settingsDao.getMaxCompileVirtualMemory());
		
		
		model.addAttribute("penalization", settingsDao.getPenalization());
		model.addAttribute("maxSubmissionQueueCapacity", settingsDao.getMaxSubmissionQueueCapacity());
		
		model.addAttribute("stackMemoryLimitJava", settingsDao.getStackMemoryLimitJava());
		model.addAttribute("maxOutputFileSize", settingsDao.getMaxOutputFileSize());
		model.addAttribute("maxTotalSubmissionRuntime", settingsDao.getMaxTotalSubmissionRuntime());
		
		return "settings";
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/changeContestMode")
	public String changeContestMode(@RequestParam("value") boolean enabled,Model model){
		
		settingsDao.setContestMode(enabled);
		
		return "blank";
	}
	
	@RequestMapping("/resetToDefaults")
	public String resetToDefaults(Model model){
		
		settingsDao.resetToDefaults();
		
		return "redirect:/admin/settings/";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/sourcesLocations")
	public String sourcesLocations(
			@RequestParam(value="filesLocation",defaultValue="/")String filesLocation,
			@RequestParam(value="chrootLocation",defaultValue="/")String chrootLocation,
			Model model){
		
		settingsDao.setFriojFilesLocation(filesLocation);
		settingsDao.setFriojChrootLocation(chrootLocation);
		
		model.addAttribute("msg","sources locations updated");
		return "blank";
	}
	
	
	@RequestMapping(method=RequestMethod.POST, value="/paginationRestrictions")
	public String paginationRestrictions(
			@RequestParam(value="articles",defaultValue="10")int articles,
			@RequestParam(value="messages",defaultValue="10")int messages,
			@RequestParam(value="problems",defaultValue="10")int problems,
			@RequestParam(value="submissions",defaultValue="10")int submissions,
			Model model){
		
		settingsDao.setMaxArticlesPerPageCount((articles<=0)?1:articles);
		settingsDao.setMaxMessagesShoutBoxCount((messages<=0)?1:messages);
		settingsDao.setMaxProblemPerPageCount((problems<=0)?1:problems);
		settingsDao.setMaxSubmissionsPerPageCount((submissions<=0)?1:submissions);
		
		model.addAttribute("msg","pagination restrictions updated");
		return "blank";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/multipliers")
	public String multipliers(
			@RequestParam(value="cMultiplier",defaultValue="1")double cMultiplier,
			@RequestParam(value="cppMultiplier",defaultValue="1")double cppMultiplier,
			@RequestParam(value="javaMultiplier",defaultValue="1")double javaMultiplier,
			Model model){
		
		settingsDao.setCMultiplier(cMultiplier);
		settingsDao.setCppMultiplier(cppMultiplier);
		settingsDao.setJavaMultiplier(javaMultiplier);
		
		model.addAttribute("msg","multipliers updated");
		return "blank";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/problemDefaults")
	public String problemDefaults(
			@RequestParam(value="maxFileSize",defaultValue="1")int maxFileSize,
			@RequestParam(value="defaultMemoryLimit",defaultValue="1")int defaultMemoryLimit,
			@RequestParam(value="defaultTimeLimit",defaultValue="1")int defaultTimeLimit,
			Model model){
		
		settingsDao.setStandardMaxInputFileSize(maxFileSize);
		settingsDao.setStandardMemoryLimit(defaultMemoryLimit);
		settingsDao.setStandardTimeLimit(defaultTimeLimit);
		
		model.addAttribute("msg","problem defaults updated");
		return "blank";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/email")
	public String email(@RequestParam(value="email",defaultValue="")String email, Model model){
		
		settingsDao.setAdminMailboxAddress(email);
		
		model.addAttribute("msg","email address updated");
		return "blank";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/compilationLimits")
	public String compilation(
			@RequestParam(value="compilationTime",defaultValue="600")int time,
			@RequestParam(value="compilationMemory",defaultValue="1000")int memory,
			Model model){
		
		settingsDao.setMaxCompileTime(time);
		settingsDao.setMaxCompileVirtualMemory(memory);
		
		model.addAttribute("msg","compilation limits updated");
		return "blank";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/runtimeRestrictions")
	public String additionalRuntimeRestrictions(
			@RequestParam(value="maxTotalSubmissionRuntime",defaultValue="300")int maxTotalSubmissionRuntime,
			@RequestParam(value="maxOutputFileSize",defaultValue="100")int maxOutputFileSize,
			@RequestParam(value="stackMemoryLimitJava",defaultValue="8")int stackMemoryLimitJava,
			Model model){
		
		settingsDao.setMaxTotalSubmissionRuntime(maxTotalSubmissionRuntime);
		settingsDao.setMaxOutputFileSize(maxOutputFileSize);
		settingsDao.setStackMemoryLimitJava(stackMemoryLimitJava);
		
		model.addAttribute("msg","runtime restrictions updated");
		return "blank";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/additionalRestrictions")
	public String additionalRestriction(
			@RequestParam(value="penalization",defaultValue="20")int penalization,
			@RequestParam(value="maxSubmissionQueueCapacity",defaultValue="10")int maxSubmissionQueueCapacity,
			Model model){
		
		settingsDao.setPenalization(penalization);
		settingsDao.setMaxSubmissionQueueCapacity(maxSubmissionQueueCapacity);
		
		model.addAttribute("msg","additional restrictions updated");
		return "blank";
	}
	
}
