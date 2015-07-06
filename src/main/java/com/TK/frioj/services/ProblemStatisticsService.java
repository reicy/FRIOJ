package com.TK.frioj.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.ProblemStatistics;


@Service
public class ProblemStatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(ProblemStatisticsService.class);
	
	@Autowired
	private SubmissionDao submissionDao;
	
	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private UserDao userDao;
	
	public String getNameOfTheFirstSolver(int problemId){
		
		try{
			return submissionDao.getFirstSolverNameOfProblem(problemId);
		}catch(EmptyResultDataAccessException ex){
			
		}
		
		return "not solved yet";
	}
	
	public ProblemStatistics getProblemStatistics(int problemId){
		ProblemStatistics ps = new ProblemStatistics();
		List<String[]> counts;
		try{
			counts = submissionDao.getCountOfEachStatusForProblem(problemId);
			for (String[] pair : counts) {
				switch (pair[0].toUpperCase()) {
				case "AC":  ps.setAc(Integer.parseInt(pair[1]));	break;
				case "WA":  ps.setWa(Integer.parseInt(pair[1]));	break;
				case "RTE": ps.setRte(Integer.parseInt(pair[1]));	break;
				case "CE":  ps.setCe(Integer.parseInt(pair[1]));	break;
				case "TLE": ps.setTle(Integer.parseInt(pair[1]));	break;
				case "CRV": ps.setCrv(Integer.parseInt(pair[1]));	break;
				case "UE":  ps.setUe(Integer.parseInt(pair[1]));	break;

				}
			}
			
		}catch(EmptyResultDataAccessException ex){
			
		}
		
		return ps;
	}


	
}
