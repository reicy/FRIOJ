package com.TK.frioj.services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.runners.SubmissionThreadRunner;
import com.TK.frioj.systemServices.ProblemRunnerService;

@Service
public class SubmissionQueueService {

	private static final Logger logger = LoggerFactory.getLogger(SubmissionQueueService.class);
	
	private BlockingQueue<Submission> queue;
	private Thread runner;
	
	@Autowired
	private SettingsDao settingsDao;
	
	@Autowired
	private ProblemRunnerService problemRunner;
	
	@Autowired
	private SubmissionDao submissionDao;
	
	public SubmissionQueueService(){
		
	}
	
	@PostConstruct
	private void init(){
		
		queue = new LinkedBlockingQueue<>(settingsDao.getMaxSubmissionQueueCapacity());
		startSubmissionRunner();
	}
	
	private void startSubmissionRunner(){
		runner = new SubmissionThreadRunner(queue, problemRunner, submissionDao);
		runner.start();
	}
	
	public boolean addSubmissionToQueue(Submission submission){
		boolean result;
		if(!runner.isAlive()){
			startSubmissionRunner();
		}
		result = queue.offer(submission);
		if(result)	{
			submissionDao.addSubmission(submission);
		}
		return result;
		
	}
	
}
