package com.TK.frioj.runners;

import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.systemServices.ProblemRunnerService;

public class SubmissionRunner implements Runnable{

	private ProblemRunnerService problemRunner;
	private SubmissionDao submissionDao;
	
	private Submission submission;
	
	
	public SubmissionRunner(Submission submission, ProblemRunnerService problemRunner, SubmissionDao submissionDao) {
		this.submission = submission;
		this.problemRunner = problemRunner;
		this.submissionDao = submissionDao;
	}
	
	@Override
	public void run() {
		
		submissionDao.addSubmission(submission);
		
		problemRunner.runSubmission(submission);
				
		submissionDao.updateSubmission(submission);
		
	}

}
