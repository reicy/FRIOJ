package com.TK.frioj.dao;

import java.util.List;
import java.util.Map;

import com.TK.frioj.entities.Session;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.SubmissionStatus;

public interface SubmissionDao {

	public void addSubmission(Submission submission);
	
	public void updateSubmission(Submission submission);
	
	public Submission getSubmission(int submissionId);
	
	public List<Submission> getAllUserSubmissions(int userId);
	
	public List<Integer> getAllUserNotACSubmissions(int userId);
		
	public List<Integer> getAllUserSubmissionNumbersOfStatus(int userId, SubmissionStatus status);
	
	public List<Submission> getAllSessionSubmissions(Session session);
	
	public Map<String,Integer> getAllUserSubmissionsCount();
	
	public List<String[]> getTopNUserACSubmissionsCountDisticntOrdered(int topN);

	public String getFirstSolverNameOfProblem(int problemId);
	
	public List<String[]> getCountOfEachStatusForProblem(int problemId);
	
	public List<String[]> getCountOfEachStatusForUser(int userId);

	public int getUserSolvedProblemsCount(int userId);

	public int getAllUserSubmissionNumbersOfStatusCount(int userId, SubmissionStatus subStat);

	public int getAllUserSubmissionsCount(int userId);

	public double getUserAverageACSubsRunTime(int userId);

	public List<Integer> getAllUserTriedProblems(int userId);

	public List<Integer> getAllUserSolvedProblems(int userId);

	public boolean hasUserSolvedProblem(int problemId, int userId);

	public List<Submission> getAllUserSubmissions(int userId, int startingRow, int count);

	public List<Submission> getAllSessionSubmissionsNamed(Session session);

	public List<Submission> getAllSessionSubmissionsNamedOfUser(Session session, String login);

	public int getAllSessionSubmissionsNamedOfUserCount(Session session, String login);

	public int getAllSessionSubmissionsNamedCount(Session session);

	public List<Submission> getAllSessionSubmissionsNamedOfUser(Session session, String login, int startRow, int count);

	public List<Submission> getAllSessionSubmissionsNamed(Session session, int startRow, int count);
	
}
