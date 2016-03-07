package com.TK.frioj.entities;

import org.joda.time.DateTime;

import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.SubmissionStatus;

public class Submission {

	private int submissionId;
	private int userId;
	private String sourceCode;
	private String err;
	private String out;
	private String in;
	private String output;
	private String name;
	private Languages lang;
	private Problem problem;
	private SubmissionStatus status;
	private int runTime;
	private DateTime date;
	private String userName;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Languages getLang() {
		return lang;
	}

	public void setLang(Languages lang) {
		this.lang = lang;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public void setLanguage(Languages lang) {
		this.lang = lang;
	}

	public int getRunTime() {
		return runTime;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public Languages getLanguage() {
		return lang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Submission(String sourceCode, String out, String in, String name,
			Languages lang, Problem problem) {
		super();
		this.sourceCode = sourceCode;
		this.out = out;
		this.in = in;
		this.name = name;
		this.lang = lang;
		this.problem = problem;
		this.status = SubmissionStatus.NE;
	}

	public Submission() {
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Problem getProblem() {
		return problem;
	}

	public SubmissionStatus getStatus() {
		return status;
	}

	public void setStatus(SubmissionStatus status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public int getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(int submissionId) {
		this.submissionId = submissionId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
