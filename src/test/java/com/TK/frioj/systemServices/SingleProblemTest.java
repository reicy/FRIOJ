package com.TK.frioj.systemServices;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.Test;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.daoImpl.FileSettingsDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.SubmissionStatus;
import com.TK.frioj.services.SubmissionService;

@Test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class SingleProblemTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SingleProblemTest.class);

	
	private SettingsDao settingsDao;
	private Submission sub;
	private SubmissionStatus result;
	private ProblemRunnerService problemRunner;
	private SubmissionService submissionService;


	public SingleProblemTest(int id, Languages lang, SubmissionStatus result, ApplicationContext a) {
		settingsDao = new FileSettingsDao();
		
		ApplicationContext ac = a;
		submissionService = (SubmissionService) ac.getBean("submissionService");
		problemRunner = (ProblemRunnerService) ac.getBean("problemRunnerService");
		
		this.result = result;
		sub = submissionService.createSubmission("", "Main",lang, id, "test");
		if(lang == Languages.C){
			sub.setSourceCode(getSourceCode(findNameOfSourceCodeOfProblemC(id)));
			sub.setName(findNameOfSourceCodeOfProblemC(id).replace(".C", "").replace(".c", ""));
		}
		if(lang==Languages.Java)sub.setSourceCode(getSourceCode(id + "SC.java"));
		
	}
	
	private String findNameOfSourceCodeOfProblemC(int problemId){
		File file = new File(settingsDao.getSourceCodeLocation());
		
		for (File scFile : file.listFiles()) {
			if(scFile.getName().matches(problemId+"_[a-zA-Z0-9-]{1,}.[cC]")){
				return scFile.getName();
			};
			
		}
		
		return null;
		
	}
	
	public SingleProblemTest(String sourceCode, int id, Languages lang, SubmissionStatus result, ApplicationContext ac) {
		
		submissionService = (SubmissionService) ac.getBean("submissionService");
		problemRunner = (ProblemRunnerService) ac.getBean("problemRunnerService");
		
		this.result = result;
		sub = submissionService.createSubmission("", "Main",lang, id, "test");
		sub.setSourceCode(getSourceCode(sourceCode+".java"));
		
		
	}
	
	public void testSubmission(){
		//arrange
		
		//act
		problemRunner.runSubmission(sub);

		// assert
		logger.info("problem id: "+sub.getProblem().getProblemId()+" runtime: "+sub.getRunTime());
		logger.info("err:  "+sub.getErr());
		logger.info("status: "+sub.getStatus().toString());
		assertEquals(sub.getStatus(), result);
		
		
	}
	
	private String getSourceCode(String source) {
		try {
			StringBuilder sb = new StringBuilder();
			Scanner input = new Scanner(new File(settingsDao.getSourceCodeLocation()
					+ source));
			while (input.hasNextLine()) {
				sb.append(input.nextLine() + "\n");
			}
			input.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
		}
		return null;
	}
	
	

	
}
