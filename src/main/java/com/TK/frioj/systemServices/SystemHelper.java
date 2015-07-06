package com.TK.frioj.systemServices;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.exceptions.CompilationException;

@Component
public class SystemHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemHelper.class);
	
	@Autowired
	private SettingsDao settingsDao;
	
	public void createFolder(String path) throws InterruptedException, IOException{
		ProcessBuilder prob = new ProcessBuilder("mkdir",path);
		Process p = null;
		p = prob.start();
		p.waitFor();
	}
	
	public void removeFolder(String path) throws InterruptedException, IOException{
		ProcessBuilder prob = new ProcessBuilder("rm","-r",path);
		Process p = null;
		p = prob.start();
		p.waitFor();
	}
	
	public void createFile(String name, String location, String content) throws IOException {
		File file = new File(location+name);
		file.createNewFile();
		PrintWriter out = new PrintWriter(file);
		out.print(content);
		out.close();
	}

	public void copyFile(String subor,String newName, String source, String destination) throws IOException, InterruptedException {
		ProcessBuilder prob = new ProcessBuilder("cp",source+subor,destination+newName);
		//prob.directory(new File(source));
		Process p = null;
		p = prob.start();
		p.waitFor();
		
	}
	
	public void runScript(String name, String param, String location) throws IOException, InterruptedException {
		ProcessBuilder prob = new ProcessBuilder("./"+name,param);
		prob.directory(new File(location));
		Process p = null;
		p = prob.start();
		p.waitFor();
	}
	
	public void runScript(String location, List<String> cmds) throws IOException, InterruptedException {
		
		ProcessBuilder prob = new ProcessBuilder(cmds);
		prob.directory(new File(location));
		Process p = null;
		p = prob.start();
		p.waitFor();
	}

	public void compileJava(String mainFolder, String name) throws CompilationException, IOException, InterruptedException {
		
		createFile("compilationError.txt", mainFolder, "");
		copyFile("compileJava", "compiler", settingsDao.getScriptsLocation(), mainFolder);
		
		LinkedList<String> params = new LinkedList<String>();
		params.add("./compiler" );
		params.add(name);
		params.add(""+settingsDao.getMaxCompileTime());
		params.add(""+settingsDao.getMaxCompileVirtualMemory());
		runScript(mainFolder, params);
		
		File errFile = new File(mainFolder+"compilationError.txt");
		if(errFile.length()>0)throw new CompilationException();
	}
	
	public void compileC(String mainFolder, String name) throws CompilationException, IOException, InterruptedException {
		
		createFile("compilationError.txt", mainFolder, "");
		copyFile("compileC", "compiler", settingsDao.getScriptsLocation(), mainFolder);

		LinkedList<String> params = new LinkedList<String>();
		params.add("./compiler" );
		params.add(name);
		params.add(""+settingsDao.getMaxCompileTime());
		params.add(""+settingsDao.getMaxCompileVirtualMemory());
		runScript(mainFolder, params);
		
		File errFile = new File(mainFolder+"compilationError.txt");
		if(errFile.length()>0)throw new CompilationException();
	}
	
	public void compileCpp(String mainFolder, String name) throws CompilationException, IOException, InterruptedException {
		
		createFile("compilationError.txt", mainFolder, "");
		copyFile("compileCpp", "compiler", settingsDao.getScriptsLocation(), mainFolder);
		
		LinkedList<String> params = new LinkedList<String>();
		params.add("./compiler" );
		params.add(name);
		params.add(""+settingsDao.getMaxCompileTime());
		params.add(""+settingsDao.getMaxCompileVirtualMemory());
		runScript(mainFolder, params);
		
		File errFile = new File(mainFolder+"compilationError.txt");
		if(errFile.length()>0)throw new CompilationException();
	}

	public void runCSubmission(Submission submission, String mainFolder) throws IOException, InterruptedException {
		
		copyFile("initRunner", "initRunner",settingsDao.getScriptsLocation(), mainFolder);
		copyFile("schrootInit", "schrootInit", settingsDao.getScriptsLocation(), mainFolder);
		
		LinkedList<String> params = new LinkedList<String>();
		params.add("./schrootInit" );
		params.add(""+getTimeLimit(submission));
		params.add(""+submission.getProblem().getMemoryLimit());
		params.add(""+settingsDao.getMaxOutputFileSize());
		params.add(""+settingsDao.getMaxTotalSubmissionRuntime());
		runScript(mainFolder, params);
	
	}
	
	public void runCppSubmission(Submission submission, String mainFolder) throws IOException, InterruptedException {
		
		copyFile("initRunner", "initRunner",settingsDao.getScriptsLocation(), mainFolder);
		copyFile("schrootInit", "schrootInit", settingsDao.getScriptsLocation(), mainFolder);

		LinkedList<String> params = new LinkedList<String>();
		params.add("./schrootInit" );
		params.add(""+getTimeLimit(submission));
		params.add(""+submission.getProblem().getMemoryLimit());
		params.add(""+settingsDao.getMaxOutputFileSize());
		params.add(""+settingsDao.getMaxTotalSubmissionRuntime());
		runScript(mainFolder, params);
		
	}
	
	public void runJavaSubmission(Submission submission, String mainFolder) throws IOException, InterruptedException {
		
		copyFile("javaRunner", "javaRunner",settingsDao.getScriptsLocation(), mainFolder);
		copyFile("runJavaSubmission", "runJavaSubmission", settingsDao.getScriptsLocation(), mainFolder);
		
		LinkedList<String> params = new LinkedList<String>();
		params.addLast("./runJavaSubmission" );
		params.addLast(""+getTimeLimit(submission));
		params.addLast(""+submission.getProblem().getMemoryLimit());
		params.addLast(""+settingsDao.getMaxOutputFileSize());
		params.addLast(""+settingsDao.getMaxTotalSubmissionRuntime());
		params.addLast(""+settingsDao.getStackMemoryLimitJava());
		params.addLast(submission.getName());
		runScript(mainFolder, params);
		
	}
	

	private String getTimeLimit(Submission submission){
		
		double time = submission.getProblem().getTimeLimit()*settingsDao.getMultiplier(submission.getLanguage());
		return ""+(Math.floor(time)+1);
	}
}
