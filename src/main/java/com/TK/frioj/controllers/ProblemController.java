package com.TK.frioj.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.TagsDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.Problem;
import com.TK.frioj.enums.ProblemStatus;
import com.TK.frioj.enums.SubmissionStatus;
import com.TK.frioj.helpers.AuthorizationHelper;
import com.TK.frioj.services.ProblemStatisticsService;

@Controller
public class ProblemController {

	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProblemController.class);
	
	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private TagsDao tagsDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProblemStatisticsService problemStatisticsService;
	
	@Autowired
	private SettingsDao settingsDao;
	
	@Autowired
	private SubmissionDao submissionDao;
	
	@RequestMapping("/addTagToProblem/{problemId}/{tagId}")
	public String addTagToProblem(
			@PathVariable("problemId") int problemId,
			@PathVariable("tagId") int tagId, 
			Model model){
		
		if(problemDao.existProblem(problemId) && tagsDao.existTag(tagId)){
			tagsDao.tagProblem(problemId, tagId, userDao.getUserId(AuthorizationHelper.getCurrentUserName()) );
			return "redirect:/problem/"+problemId;
		}
		
		return "redirect:/";
		
	}
	
	@RequestMapping("/removeTagFromProblem/{problemId}/{tagId}")
	public String removeTagFromProblem(
			@PathVariable("problemId") int problemId,
			@PathVariable("tagId") int tagId, 
			Model model){
		
		tagsDao.untagProblem(problemId, tagId);
		
		return "redirect:/problem/"+problemId;
	}
	
	@RequestMapping("/problem/{idProblemu}")
	public String displayProblem(@PathVariable("idProblemu")int idProblemu, Model model){
		Problem problem;
		
		try{
			problem = problemDao.getProblem(idProblemu);
		}catch(EmptyResultDataAccessException ex){
			return "redirect:/";
		}
		
		if(problem.getStatus()==ProblemStatus.hidden && AuthorizationHelper.isJuniorOrSenior() )return "redirect:/";
		
		String pdfUrl = null;
		if(problem.getPdf()!=null)pdfUrl = "/frioj/getPdf/"+problem.getProblemId();
		
		model.addAttribute("problemTags",tagsDao.problemTags(idProblemu));
		model.addAttribute("tags", tagsDao.getAllHashTags());
		model.addAttribute("isPdfIncluded", problem.getPdf()!=null);
		model.addAttribute("pdfUrl", pdfUrl);
		model.addAttribute("problem", problem);
		model.addAttribute("firstSolverName",problemStatisticsService.getNameOfTheFirstSolver(idProblemu));
		model.addAttribute("problemStatistics", problemStatisticsService.getProblemStatistics(idProblemu));
		
		return "problem";
	}
	
	/**
	 * displays problems pages, max number of problems per page is set in settings
	 * if user is Teacher or Admin displays also hidden problems
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/allProblems")
	public String displayAllProblems(
			@RequestParam(defaultValue="0")int page, 
			@RequestParam(defaultValue="0", value="tagId")int tagId,  
			Model model){
		
		int problemCount=problemDao.getProblemCount(tagId);
		int ppp = settingsDao.getMaxProblemPerPageCount();
		int maxPageNum = problemCount/ppp;
		if(maxPageNum*ppp<problemCount)maxPageNum++;
		
		page = (page>0)?page:1;
		
		ArrayList<Problem> problemList;
		
		if(AuthorizationHelper.isTeacherOrAdmin()){
			
			problemList = new ArrayList<Problem>(problemDao.getProblems(tagId, (page-1)*ppp, ppp));
			
		}else{
			
			problemList = new ArrayList<Problem>(problemDao.getVisibleProblems(tagId, (page-1)*ppp, ppp));
			
		}
		
		
		
		if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
				
				int userId = userDao.getUserId(AuthorizationHelper.getCurrentUserName());
				ArrayList<Integer> accepted = new ArrayList<Integer>(submissionDao.getAllUserSubmissionNumbersOfStatus(userId, SubmissionStatus.AC));
				ArrayList<Integer> notSolved = new ArrayList<Integer>(submissionDao.getAllUserNotACSubmissions(userId));
				for (Problem problem : problemList) {
					if(notSolved.contains(problem.getProblemId()))problem.setIdColor("red");
					if(accepted.contains(problem.getProblemId()))problem.setIdColor("green");
					
					
				}
			
		}
		
		
		model.addAttribute("tags",tagsDao.getAllHashTags());
		model.addAttribute("currentTag",tagId);
		model.addAttribute("currentPage", page);
		model.addAttribute("pages", maxPageNum);
		model.addAttribute("problemList", problemList);
		return "displayAllProblems";
	}
	
	
}