package com.TK.frioj.dao;

import java.util.List;

import com.TK.frioj.entities.Problem;

public interface ProblemDao {

	public void addProblem(Problem problem);
	
	public Problem getProblem(int problemId);

	public void updateProblem(Problem problem);

	public void updateProblemWithoutPdf(Problem problem);
	
	public List<Problem> getAllVisibleProblems(int tagId);

	public List<String[]> getAllProblemNamesAndIds();
	
	public List<Problem> getAllProblems(int tagId);
	
	public List<Problem> getProblems(int tagId, int start, int count);
	
	public List<Problem> getVisibleProblems(int tagId, int start, int count);

	public boolean existProblem(int problemId);
	
	public int getProblemCount(int tagId);

	public int getVisibleProblemCount(int tagId);
	
	public int getMaxInputFileSizeOfProblem(int problemId);
}
