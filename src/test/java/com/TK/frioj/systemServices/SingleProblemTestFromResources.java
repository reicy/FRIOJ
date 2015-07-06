package com.TK.frioj.systemServices;

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.Test;

import com.TK.frioj.entities.Problem;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.ProblemStatus;
import com.TK.frioj.enums.SubmissionStatus;


@Test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class SingleProblemTestFromResources {
/*
	private static final Logger logger = LoggerFactory.getLogger(SingleProblemTestFromResources.class);
	
	private Submission sub;
	private SubmissionStatus result;
	private ProblemRunnerService problemRunner;
	//private SubmissionService submissionService;
	
	public SingleProblemTestFromResources(ApplicationContext ac, Languages lang, String name, SubmissionStatus expectedResult, int timeLimit, int memLimit){
		//submissionService = (SubmissionService) ac.getBean("submissionService");
		problemRunner = (ProblemRunnerService) ac.getBean("problemRunnerService");
		result = expectedResult;
		
		Problem problem = new Problem(0, 0, "test"+name, "", "", "", ProblemStatus.visible, timeLimit, memLimit, 0);
		
		sub = new Submission(getSourceCode(lang.toString().toLowerCase()+"/"+name+getName(lang)),"testOut.out","testIn.in","Main", lang, problem);
		
	}
	
	public void testSubmission(){
		//arrange
		
		//act
		problemRunner.runSubmission(sub);

		// assert
		logger.info("");
		logger.info("problem id: "+sub.getProblem().getProblemId()+" runtime: "+sub.getRunTime()+ " name: "+sub.getProblem().getName());
		logger.info("err:  "+sub.getErr());
		logger.info("status: "+sub.getStatus().toString() +" expected "+result.toString());
		assertEquals(sub.getStatus(), result);
		
		
	}
	
	private String getName(Languages lang){
		switch (lang) {
			case C: return ".c";
			case CPP: return ".cpp";
			case Java: return "";
		}
		return "";
	}
	
	
	
	private String getSourceCode(String src) {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(src);
		StringBuilder sb = new StringBuilder();
		Scanner input = new Scanner(stream);
		while (input.hasNextLine()) {
			sb.append(input.nextLine() + "\n");
		}
		input.close();
		return sb.toString();
		
	}*/
}
