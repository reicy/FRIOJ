package com.TK.frioj.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SessionDao;
import com.TK.frioj.entities.Problem;
import com.TK.frioj.helpers.AuthorizationHelper;


@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private SessionDao sessionDao;
	
	
	/**
	 * returns pdf of selected problem or nothing otherwise
	 * 
	 * @param pdfId
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getPdf/{pdfId}")
	public String getFile(@PathVariable("pdfId") int pdfId,Model model ,HttpServletResponse response ){

		
		byte[] pdf = null;
		
		try{
			pdf = problemDao.getProblem(pdfId).getPdf();
		}catch(EmptyResultDataAccessException ex){
			return "";
		}
		
		if(pdf==null)return "";
		
		
        response.setContentType("application/pdf");
        response.setContentLength(pdf.length);				
        try {
			response.getOutputStream().write(pdf, 0, pdf.length);
		} catch (IOException e) {
			e.printStackTrace();
		}

        return "";
	}
	
	
	/**
	 * returns json of problem names & ids which contain requested string sequence
	 * 
	 * @param str
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getProblemsContainingString")
	public String getProblemsWhichContainsString(@RequestParam(defaultValue="")String str,Model model){
		
		ArrayList<Problem> problemList;
		
		if(AuthorizationHelper.isTeacherOrAdmin()){
			
			problemList = new ArrayList<Problem>(problemDao.getAllProblems(0));
			
		}else{
			
			problemList = new ArrayList<Problem>(problemDao.getAllVisibleProblems(0));
			
		}
		
		JSONArray problemIdAndNamePair;
		JSONArray problemNamesAndIdsList = new JSONArray();
		for (Problem problem : problemList) {
			if(problem.getName().contains(str) || (""+problem.getProblemId()).contains(str)){
				problemIdAndNamePair = new JSONArray();
				problemIdAndNamePair.put(problem.getProblemId());
				problemIdAndNamePair.put(problem.getName());
				problemNamesAndIdsList.put(problemIdAndNamePair);
			}
		}
		
		return problemNamesAndIdsList.toString();
	}
}
		
	
