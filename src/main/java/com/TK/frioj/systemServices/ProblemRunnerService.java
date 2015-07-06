package com.TK.frioj.systemServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.SubmissionStatus;
import com.TK.frioj.exceptions.CompilationException;
import com.TK.frioj.helpers.StringHelper;


@Service
public class ProblemRunnerService {
	
	@Autowired
	private JavaSourceCodePreprocessor javaSCPreprocessor;
	
	@Autowired
	private CppSourceCodePreprocessor cppSCPreprocessor;
	
	@Autowired
	private CSourceCodePreprocessor cSCPreprocessor;
	
	@Autowired
	private SystemHelper sysHelper;
	
	@Autowired
	private SettingsDao settingsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ProblemRunnerService.class);
	
	
	public synchronized void runSubmission(Submission submission){
		
		
		if(checkSourceCode(submission)){
			
			try {
				prepareEnviroment(submission);
				executeSubmission(submission);
				interpretResults(submission);
				
				
			} catch (InterruptedException | IOException e) {
				//possible unknown environment problem
				if(submission.getStatus().equals(SubmissionStatus.NE)){
					submission.setStatus(SubmissionStatus.UE);
					submission.setErr("unknown problem with enviroment, please contant administrator");
					submission.setErr(e.getMessage());
				}
			}
			catch (CompilationException e) {
				//compilaton exception
				submission.setStatus(SubmissionStatus.CE);
				File ce = new File(settingsDao.getChrootMainFolderLocation()+"compilationError.txt");
				StringBuilder sb = new StringBuilder();
				try {
					Scanner ceInput = new Scanner(ce);
					while(ceInput.hasNextLine()){
						sb.append(ceInput.nextLine());
						
					}
					ceInput.close();
					submission.setErr(sb.toString());
					
				} catch (FileNotFoundException e1) {
					
					submission.setErr("unknown problem with compilation, please contact administrator");
					submission.setStatus(SubmissionStatus.UE);
				}
			}
			
			
			
		}else{
			//contest rules violation
			submission.setStatus(SubmissionStatus.CRV);
		}
	}

	

	


	/**
	 * calls sourceCodePreprocessor based on submission language to check the source code
	 * @param submission
	 * @return true if all checks pass
	 */
	private boolean checkSourceCode(Submission submission) {
		
		switch (submission.getLanguage()) {
		case Java:	return javaSCPreprocessor.checkCode(submission) && javaSCPreprocessor.checkLibraries(submission);
		case CPP :  return cppSCPreprocessor.checkCode(submission) && cppSCPreprocessor.checkLibraries(submission);
		case C :  	return cSCPreprocessor.checkCode(submission) && cSCPreprocessor.checkLibraries(submission);
		}
		
		return true;
	}







	private void prepareEnviroment(Submission submission) throws InterruptedException, IOException, CompilationException {
		
		
		sysHelper.removeFolder(settingsDao.getChrootMainFolderLocation());
		sysHelper.createFolder(settingsDao.getChrootMainFolderLocation());
		
		switch (submission.getLanguage()) {
		case C:			
			
					sysHelper.createFile(submission.getName()+".c", settingsDao.getChrootMainFolderLocation(), submission.getSourceCode());
					sysHelper.copyFile("cRunner","runner",settingsDao.getRunnersLocation(),settingsDao.getChrootMainFolderLocation());
					sysHelper.compileC(settingsDao.getChrootMainFolderLocation(), submission.getName());
			
			break;
		case CPP:		
					sysHelper.createFile(submission.getName()+".cpp", settingsDao.getChrootMainFolderLocation(), submission.getSourceCode());
					sysHelper.copyFile("cppRunner","runner",settingsDao.getRunnersLocation(),settingsDao.getChrootMainFolderLocation());
					sysHelper.compileCpp(settingsDao.getChrootMainFolderLocation(),submission.getName());
			
			
			break;
		case Java:	
					sysHelper.createFile(submission.getName()+".java", settingsDao.getChrootMainFolderLocation(), submission.getSourceCode());
					sysHelper.copyFile("javaRunner","runner",settingsDao.getRunnersLocation(),settingsDao.getChrootMainFolderLocation());
					sysHelper.copyFile("policy","policy",settingsDao.getRunnersLocation(),settingsDao.getChrootMainFolderLocation());
					sysHelper.compileJava(settingsDao.getChrootMainFolderLocation(),submission.getName());
			break;

		default:
			break;
		}
	}
	
	private void executeSubmission(Submission submission) throws IOException, InterruptedException {

		sysHelper.copyFile(submission.getIn(),"in.txt",settingsDao.getInLocation(),settingsDao.getChrootMainFolderLocation());
	
		
		switch (submission.getLanguage()) {
		case C:			
					sysHelper.runCSubmission(submission,settingsDao.getChrootMainFolderLocation());
			
			break;
		case CPP:		
					sysHelper.runCppSubmission(submission,settingsDao.getChrootMainFolderLocation());
			
					
			break;
		case Java:	
					sysHelper.runJavaSubmission(submission,settingsDao.getChrootMainFolderLocation());
			break;

		default:
			break;
		}
	}
	
	private void interpretResults(Submission submission) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		
		File out = new File(settingsDao.getOutLocation()+submission.getOut());
		File submissionOutput = new File(settingsDao.getChrootMainFolderLocation()+"out.txt");
		File state = new File(settingsDao.getChrootMainFolderLocation()+"state.txt");
		File err = new File(settingsDao.getChrootMainFolderLocation()+"err.txt");
		
		Scanner errInput = new Scanner(err);
		Scanner stateInput = new Scanner(state);
		
		
		//read err
		while(errInput.hasNextLine()){
			sb.append(errInput.nextLine());
			sb.append("\n");
		}
		errInput.close();
		
		submission.setErr(sb.toString());
		
		//read state
		sb.setLength(0);
		submission.setRunTime(0);
		
		int lineNum=0;
		
		while(stateInput.hasNextLine()){
			
			line = stateInput.nextLine();
			if(line != null && line.length()>0 && line.contains("RUNTIME")){
				String temp;
				temp = line.split(" ")[1];
				submission.setRunTime((int)Double.parseDouble(temp));
			}
			sb.append(line);
			sb.append("\n");
		}
		stateInput.close();
		
		//TLE
		if(submission.getErr().contains("TLE")){
			submission.setStatus(SubmissionStatus.TLE);
			return;
		}
		
		//RTE
		if(!submission.getErr().isEmpty()){
			submission.setStatus(SubmissionStatus.RTE);
			return;
		}
		
		//WA
		BufferedReader outInput = new BufferedReader(new FileReader(out));
		BufferedReader submissionOutInput = new BufferedReader(new FileReader(submissionOutput));
		int subOutLines=0;
		int outLines=0;
		
		while(submissionOutInput.readLine()!=null)subOutLines++;
		while(outInput.readLine()!=null)outLines++;
		outInput.close();
		submissionOutInput.close();
		
		if(subOutLines!=outLines){
			submission.setStatus(SubmissionStatus.WA);
			submission.setErr(StringHelper.encodeDifferentSize(subOutLines, outLines));
			return;
		}
		
		outInput = new BufferedReader(new FileReader(out));
		submissionOutInput = new BufferedReader(new FileReader(submissionOutput));
		
		
		String outLine, subOutLine;
		
		while ((outLine = outInput.readLine()) != null) {
			lineNum++;
			subOutLine = submissionOutInput.readLine();
			if(!outLine.equals(subOutLine)){
				submission.setErr("Line number: "+lineNum+". Correct: "+outLine+" - yours: "+subOutLine);
				submission.setStatus(SubmissionStatus.WA);
				outInput.close();
				submissionOutInput.close();
				return;
			}
			
		}
		outInput.close();
		submissionOutInput.close();
		
		//ALL passed AC
		submission.setStatus(SubmissionStatus.AC);
	}
	
}
