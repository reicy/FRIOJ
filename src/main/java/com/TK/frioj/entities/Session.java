package com.TK.frioj.entities;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

public class Session {

	private int sessionId;
	private String name;
	private DateTime start;
	private DateTime end;
	private ArrayList<Integer> members;

	private ArrayList<Integer> problems;

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public DateTime getEnd() {
		return end;
	}

	public Date getDend() {
		return end.toDate();
	}

	public Date getDstart() {
		return start.toDate();
	}

	public void addProblem(int problemId) {
		if (!problems.contains(problemId))
			problems.add(problemId);

	}

	public void addMember(int memberId) {
		if (!members.contains(memberId))
			members.add(memberId);
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	public ArrayList<Integer> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Integer> members) {
		this.members = members;
	}

	public ArrayList<Integer> getProblems() {
		return problems;
	}

	public void setProblems(ArrayList<Integer> problems) {
		this.problems = problems;
	}

	public Session(int sessionId, String name, DateTime start, DateTime end) {
		super();
		this.sessionId = sessionId;
		this.name = name;
		this.start = start;
		this.end = end;
		members = new ArrayList<Integer>(30);
		problems = new ArrayList<Integer>(30);
	}

}
