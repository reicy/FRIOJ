package com.TK.frioj.systemServices;

import com.TK.frioj.entities.Submission;

public interface SourceCodePreprocessor {
	
	public boolean checkCode(Submission submission);
	public boolean checkLibraries(Submission submission);
}
