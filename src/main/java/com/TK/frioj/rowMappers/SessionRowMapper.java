package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.TK.frioj.dao.SessionDao;
import com.TK.frioj.entities.Session;

@Component
public class SessionRowMapper implements RowMapper<Session>{
	
	@Autowired
	private SessionDao sessionDao;
	
	@Override
	public Session mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Session session = new Session(0,"",null,null);
		
		session.setSessionId(resultSet.getInt("SessionId"));
		session.setName(resultSet.getString("Name"));
		session.setStart(new DateTime(Timestamp.valueOf(resultSet.getString("Start"))));
		session.setEnd(new DateTime(Timestamp.valueOf(resultSet.getString("End"))));
		session.setProblems(new ArrayList<Integer>(sessionDao.getSessionProblemsIds(session.getSessionId())));
		session.setMembers(new ArrayList<Integer>(sessionDao.getSessionMembersIds(session.getSessionId())));
		
				
		return session;
	}
}
	
		

