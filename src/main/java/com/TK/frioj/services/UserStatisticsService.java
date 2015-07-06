package com.TK.frioj.services;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.UserStatistics;
import com.TK.frioj.enums.SubmissionStatus;

@Service
public class UserStatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(ProblemStatisticsService.class);
	
	@Autowired
	private SubmissionDao submissionDao;
	
	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private UserDao userDao;
	
	public UserStatistics getUserStatistics(int userId){
		UserStatistics uStats = new UserStatistics();
		
		List<String[]> counts;
		try{
			counts = submissionDao.getCountOfEachStatusForUser(userId);
			for (String[] pair : counts) {
				switch (pair[0].toUpperCase()) {
				case "AC":  uStats.setAc(Integer.parseInt(pair[1]));	break;
				case "WA":  uStats.setWa(Integer.parseInt(pair[1]));	break;
				case "RTE": uStats.setRte(Integer.parseInt(pair[1]));	break;
				case "CE":  uStats.setCe(Integer.parseInt(pair[1]));	break;
				case "TLE": uStats.setTle(Integer.parseInt(pair[1]));	break;
				case "CRV": uStats.setCrv(Integer.parseInt(pair[1]));	break;
				case "UE":  uStats.setUe(Integer.parseInt(pair[1]));	break;

				}
			}
			
		}catch(EmptyResultDataAccessException ex){
			
		}
		
		uStats.setACChance(getACChance(userId));
		uStats.setAverageACTime(getAvarateACTime(userId));
		addAllSolved(uStats, userId);
		addAllTriedNotSolved(uStats, userId);
		
		return uStats;
	}

	private void addAllTriedNotSolved(UserStatistics uStats, int userId) {
		LinkedList<Integer> allProblems = new LinkedList<Integer>( submissionDao.getAllUserTriedProblems(userId));
		LinkedList<Integer> solvedProblems = new LinkedList<Integer>( submissionDao.getAllUserSolvedProblems(userId));
		allProblems.removeAll(solvedProblems);
		uStats.setTriedNotSolved(allProblems);
	}

	private void addAllSolved(UserStatistics uStats, int userId) {
		LinkedList<Integer> solvedProblems = new LinkedList<Integer>( submissionDao.getAllUserSolvedProblems(userId));
		
		uStats.setSolved(solvedProblems);
	}
	


	private double getAvarateACTime(int userId) {
		return submissionDao.getUserAverageACSubsRunTime(userId);
	}

	private double getACChance(int userId) {
		double userSolvedProblemsCount = submissionDao.getUserSolvedProblemsCount(userId);
		double userACCount = submissionDao.getAllUserSubmissionNumbersOfStatusCount(userId, SubmissionStatus.AC);
		double userAllSubmissionsCount = submissionDao.getAllUserSubmissionsCount(userId);
		return userSolvedProblemsCount/(userAllSubmissionsCount-userACCount+userSolvedProblemsCount);
	}
}
