package com.TK.frioj.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.Problem;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.ProblemStatus;
import com.TK.frioj.systemServices.ProblemRunnerService;

@Service
public class SubmissionService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubmissionService.class);
	
	
	@Autowired
	private ProblemRunnerService problemRunnerService;
	
	@Autowired
	private SubmissionDao submissionDao;
	
	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SubmissionQueueService submissionQueueService;
	
	public Submission createSubmission(String sourceCode, String submissionName, Languages lang, int problemId, String userName){
		
		Problem problem = problemDao.getProblem(problemId);
		if(submissionName==null){
			submissionName = getName(sourceCode, lang);
		}
		
		int userId = userDao.getUserId(userName);
		
		Submission submission = new Submission(sourceCode, problem.getProblemId()+".out", problem.getProblemId()+".in", submissionName, lang, problem);
		submission.setUserId(userId);
		
		return submission;
	}

	private String getName(String sourceCode, Languages lang) {
		
		if(lang==Languages.Java){
			
			int position = sourceCode.indexOf("class ");
			StringBuilder sb = new StringBuilder();
			for (int i = position+5; i < sourceCode.length(); i++) {
				if(sourceCode.charAt(i)=='{')break;
				sb.append(sourceCode.charAt(i));
			}
			return sb.toString().trim().split(" ")[0].trim();
			
		}
		
		return "Main";
	}
	
	private String checkProblemId(int problemId){
		if(problemId<=0)return "incorrect problem id";
		try{
			Problem p = problemDao.getProblem(problemId);
			if(p.getStatus()==ProblemStatus.hidden)return "problem not found";
		}catch(EmptyResultDataAccessException ex){
			return "problem not found";
		}
		return "";
	}
	
	private String checkCode(String code){
		if(code==null || code.equals(""))return "code not entered";
		
		return "";
	}
	
	public String runSubmission(String id, String code, Languages lang, String userName){
		
		String response;
		int problemId = 0; 
		
		try{
			problemId = (id.equals("")?-1:Integer.parseInt(id));
		}catch(NumberFormatException ex){
			return "Not a number.";
		}
		
		int maxInputSize = problemDao.getMaxInputFileSizeOfProblem(problemId);
		if(code.length()>maxInputSize){
			return "Max length of input exceeded.";
		}
		
		
		response = checkProblemId(problemId);
		if(!response.equals(""))return response;
		
		response = checkCode(code);
		if(!response.equals(""))return response;
		
		
		
		Submission submission = createSubmission(code, getName(code, lang), lang, problemId, userName);
		
		if(submissionQueueService.addSubmissionToQueue(submission)){
			return "submission successfully submitted";
		}
		return "server is busy, please submit later";
		
	
	}
	
	
}
