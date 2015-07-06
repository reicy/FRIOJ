package com.TK.frioj.runners;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.systemServices.ProblemRunnerService;

public class SubmissionThreadRunner extends Thread{

	private static final Logger logger = LoggerFactory.getLogger(SubmissionThreadRunner.class);
	
	private ProblemRunnerService problemRunner;
	private SubmissionDao submissionDao;
	
	private BlockingQueue<Submission> queue;
	
	
	public SubmissionThreadRunner(BlockingQueue<Submission> queue, ProblemRunnerService problemRunner, SubmissionDao submissionDao) {
		this.queue = queue;
		this.problemRunner = problemRunner;
		this.submissionDao = submissionDao;
	}
	
	public void run(){
		Submission submission = null;
		while(true){
			try {
				
				submission = queue.take();
				Thread.sleep(1000);
				problemRunner.runSubmission(submission);
						
				submissionDao.updateSubmission(submission);
				
				
			} catch (InterruptedException e) {
				logger.info(e.getMessage());
			}
			
		}
	}
}
