package com.TK.frioj.systemServices;

import java.util.ArrayList;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Factory;
import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.daoImpl.FileSettingsDao;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.SubmissionStatus;
import com.TK.frioj.services.SubmissionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class ProblemRunnerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ProblemRunnerServiceTest.class);
	
	private ProblemRunnerService problemRunner;

	private SettingsDao settingsDao;
	
	private SubmissionService submissionService;

	
	
	@BeforeTest
	public void initializeAppContext() {
		settingsDao = new FileSettingsDao();
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-test-config.xml");
		submissionService = (SubmissionService) ac.getBean("submissionService");
		problemRunner = (ProblemRunnerService) ac.getBean("problemRunnerService");
	}

/*
	@SuppressWarnings("unchecked")
	@Factory
	public Object[] allProblemsC(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-test-config.xml");
		
			SingleProblemTest single;
			
			ArrayList<SingleProblemTest> arr = new ArrayList<SingleProblemTest>();
			//arr.add(single);
			
			for (int i = 1; i < 50; i++) {
				
				single = new SingleProblemTest(i, Languages.C, SubmissionStatus.AC, ac);
				arr.add(single);
			}
			return arr.toArray();
	}
	*/

		
	
	@Factory
	public Object[] cppTest(){
		ArrayList<SingleProblemTestFromResources> arr = new ArrayList<SingleProblemTestFromResources>();
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-test-config.xml");
		
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "AC", SubmissionStatus.AC , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "bashCmdSystem", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "compilationError", SubmissionStatus.CE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "createFile", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "dynamicMemory", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "endlessInput", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "endlessOutput", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "noMainMethod", SubmissionStatus.CE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "noOutput", SubmissionStatus.WA , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "runtimeErr", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "sleep", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "stackOverflow", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "staticMemory", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "TLE", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "WA", SubmissionStatus.WA , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "writeToFile", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "threadCLang", SubmissionStatus.CRV , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.CPP, "threadClassThread", SubmissionStatus.CRV , 5000, 1000));
		
			
		return arr.toArray();
	}
	
	
	@Factory
	public Object[] cTest(){
		ArrayList<SingleProblemTestFromResources> arr = new ArrayList<SingleProblemTestFromResources>();
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-test-config.xml");
		
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "AC", SubmissionStatus.AC , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "bashCmdSystem", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "compilationError", SubmissionStatus.CE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "createFile", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "dynamicMemory", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "endlessInput", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "endlessOutput", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "newProcess", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "noMainMethod", SubmissionStatus.CE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "noOutput", SubmissionStatus.WA , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "runtimeErr", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "sleep", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "stackOverflow", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "staticMemory", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "TLE", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "WA", SubmissionStatus.WA , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "writeToFile", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.C, "thread", SubmissionStatus.CRV , 5000, 1000));
		
			
		return arr.toArray();
	}
	
	
	@Factory
	public Object[] javaTest(){
		ArrayList<SingleProblemTestFromResources> arr = new ArrayList<SingleProblemTestFromResources>();
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-test-config.xml");
		
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "AC", SubmissionStatus.AC , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "bashCmdProcessBuilder", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "bashCmdRuntime", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "compilationError", SubmissionStatus.CE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "createFile", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "dynamicMemory", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "endlessInput", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "endlessOutput", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "noMainMethod", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "noOutput", SubmissionStatus.WA , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "runtimeErr", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "sleep", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "stackOverflow", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "TLE", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "WA", SubmissionStatus.WA , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "writeToFile", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "staticMemory", SubmissionStatus.RTE , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "threadET", SubmissionStatus.CRV , 5000, 1000));
		arr.add(new SingleProblemTestFromResources(ac, Languages.Java, "threadIR", SubmissionStatus.CRV , 5000, 1000));
		
			
		return arr.toArray();
	}
	
	
}
