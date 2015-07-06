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
	
}
