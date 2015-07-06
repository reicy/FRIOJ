package com.TK.frioj.dao;

import java.util.List;

import com.TK.frioj.entities.Session;

public interface SessionDao {
	
	void addSession(Session session);
	
	Session getSession(int sessionId);
	
	void updateSession(Session session);
	
	void removeSession(int sessionId);
	
	List<Integer> getAllSessionsIds();
	
	List<Session> getAllSessions();
	

	List<Integer> getSessionProblemsIds(int sessionId);

	List<Integer> getSessionMembersIds(int sessionId);
	
	void addSessionMember(int sessionId, int userId);
	
	void addSessionProblem(int sessionId, int problemId);
	
	void removeSessionMember(int sessionId, int userId);
	
	void removeSessionProblem(int sessionId, int problemId);
	
	void removeAllSessionMembers(int sessionId);
	
	void removeAllSessionProblems(int sessionId);
	
	List<Integer> getAllUserSesionsIds(int userId);
	
	List<Session> getAllUserSessions(int userId);
	
	List<String> getSessionMembersNames(int sessionId);
	
	List<String> getSessionMembersLogins(int sessionId);


}
