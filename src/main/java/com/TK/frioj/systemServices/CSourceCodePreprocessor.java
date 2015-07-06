package com.TK.frioj.systemServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.TK.frioj.entities.Submission;

@Service
public class CSourceCodePreprocessor implements SourceCodePreprocessor {

	private String[][] forbiddenLibraries = 
		{
			{"#include\\s*<pthread.h>","pthread.h"}
		};
	
	private String[][] forbiddenExpressions = 
		{
			{"pthread_create","Thread (pthread_create)"}
		};
	
	/**
	 * checks whether forbidden commands are present in code 
	 * returns true if all checks pass
	 */
	@Override
	public boolean checkCode(Submission submission) {
		Pattern pattern;
		Matcher matcher;
		String SC = submission.getSourceCode();
		for (String[] expPair : forbiddenExpressions) {
			pattern = Pattern.compile(expPair[0]);
			matcher = pattern.matcher(SC);
			if(matcher.find()){
				submission.setErr("forbidden expression - "+expPair[1]);
				return false;
			}
		}
		return true;
	}

	/**
	 * checks whether libraries from blacklist are included
	 * returns true if all checks pass
	 */
	@Override
	public boolean checkLibraries(Submission submission) {
		Pattern pattern;
		Matcher matcher;
		String SC = submission.getSourceCode();
		for (String[] libPair : forbiddenLibraries) {
			pattern = Pattern.compile(libPair[0]);
			matcher = pattern.matcher(SC);
			if(matcher.find()){
				submission.setErr("forbidden library - "+libPair[1]);
				return false;
			}
		}
		return true;
	}
}
