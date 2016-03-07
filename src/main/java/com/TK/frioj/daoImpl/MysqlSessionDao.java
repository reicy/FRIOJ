package com.TK.frioj.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.TK.frioj.dao.SessionDao;
import com.TK.frioj.entities.Session;
import com.TK.frioj.rowMappers.SessionRowMapper;

@Repository
@Transactional
public class MysqlSessionDao implements SessionDao {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MysqlSessionDao.class);
	private NamedParameterJdbcOperations jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	private SessionRowMapper sessionRowMapper;

	@Override
	public void addSession(Session session) {
		String sql = "INSERT INTO `Session`(`Name`, `Start`, `End`) "
				+ "VALUES (:name,:start,:end)";

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("name", session.getName())
				.addValue("start", session.getStart().toDate())
				.addValue("end", session.getEnd().toDate());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, (SqlParameterSource) params, keyHolder,
				new String[] { "SessionId" });
		session.setSessionId(keyHolder.getKey().intValue());

	}

	@Override
	public Session getSession(int sessionId) {
		String sql = "SELECT `SessionId`, `Name`, `Start`, `End` FROM `Session` WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		
		Session session ;
		try{
			session = jdbcTemplate.queryForObject(sql, params, sessionRowMapper);
		}catch(EmptyResultDataAccessException ex){
			session = null;
		}
		return session;
	}

	@Override
	public void updateSession(Session session) {
		ArrayList<Integer> currentSessionMembers = new ArrayList<Integer>(
				getSessionMembersIds(session.getSessionId()));
		ArrayList<Integer> currentSessionProblems = new ArrayList<Integer>(
				getSessionProblemsIds(session.getSessionId()));

		// update session members
		ArrayList<Integer> members = new ArrayList<Integer>();
		members.addAll(currentSessionMembers);
		members.addAll(session.getMembers());

		for (Integer userId : members) {
			if (currentSessionMembers.contains(userId)
					&& session.getMembers().contains(userId))
				continue;

			if (currentSessionMembers.contains(userId))
				removeSessionMember(session.getSessionId(), userId);

			if (session.getMembers().contains(userId))
				addSessionMember(session.getSessionId(), userId);

		}

		// update session problems
		ArrayList<Integer> problems = new ArrayList<Integer>();
		problems.addAll(currentSessionProblems);
		problems.addAll(session.getProblems());

		for (Integer problemId : problems) {
			if (currentSessionProblems.contains(problemId)
					&& session.getProblems().contains(problemId))
				continue;

			if (currentSessionProblems.contains(problemId))
				removeSessionProblem(session.getSessionId(), problemId);

			if (session.getProblems().contains(problemId))
				addSessionProblem(session.getSessionId(), problemId);

		}

		// update session itself
		String sql = "UPDATE `Session` SET `Name`= :name,`Start`= :start,`End`= :end WHERE `SessionId`= :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("sessionId", session.getSessionId())
				.addValue("start", session.getStart().toDate())
				.addValue("end", session.getEnd().toDate())
				.addValue("name", session.getName());

		jdbcTemplate.update(sql, params);

	}

	@Override
	public void removeSession(int sessionId) {
		removeAllSessionMembers(sessionId);
		removeAllSessionProblems(sessionId);

		String sql = "DELETE FROM `Session` WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public List<Integer> getAllSessionsIds() {
		String sql = "SELECT `SessionId` FROM `SessionMembers` WHERE 1=1";
		MapSqlParameterSource params = new MapSqlParameterSource();
		return jdbcTemplate.queryForList(sql, params, Integer.class);
	}

	@Override
	public List<Integer> getSessionProblemsIds(int sessionId) {
		String sql = "SELECT `ProblemId` FROM `SessionProblems` WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		return jdbcTemplate.queryForList(sql, params, Integer.class);
	}

	@Override
	public List<Integer> getSessionMembersIds(int sessionId) {
		String sql = "SELECT `UserId` FROM `SessionMembers` WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		return jdbcTemplate.queryForList(sql, params, Integer.class);
	}

	@Override
	public List<String> getSessionMembersNames(int sessionId) {
		String sql = "SELECT `UserName` FROM `SessionMembers` join `User` USING(UserId) WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		return jdbcTemplate.queryForList(sql, params, String.class);
	}

	@Override
	public void addSessionMember(int sessionId, int userId) {
		String sql = "INSERT INTO `SessionMembers`(`SessionId`, `UserId`) VALUES (:sessionId, :userId)";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId).addValue("userId", userId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void addSessionProblem(int sessionId, int problemId) {
		String sql = "INSERT INTO `SessionProblems`(`SessionId`, `ProblemId`) VALUES (:sessionId, :problemId)";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId).addValue("problemId", problemId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void removeSessionMember(int sessionId, int userId) {
		String sql = "DELETE FROM `SessionMembers` WHERE `SessionId` = :sessionId AND `UserId` = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId).addValue("userId", userId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void removeSessionProblem(int sessionId, int problemId) {
		String sql = "DELETE FROM `SessionProblems` WHERE `SessionId` = :sessionId AND `ProblemId` = :problemId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId).addValue("problemId", problemId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void removeAllSessionMembers(int sessionId) {
		String sql = "DELETE FROM `SessionMembers` WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void removeAllSessionProblems(int sessionId) {
		String sql = "DELETE FROM `SessionProblems` WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public List<Integer> getAllUserSesionsIds(int userId) {
		String sql = "SELECT `SessionId` FROM `SessionMembers` WHERE `UserId` = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"userId", userId);
		return jdbcTemplate.queryForList(sql, params, Integer.class);
	}

	@Override
	public List<Session> getAllSessions() {
		String sql = "SELECT `SessionId`, `Name`, `Start`, `End` FROM `Session` ORDER BY `Start` DESC";
		MapSqlParameterSource params = new MapSqlParameterSource();
		return jdbcTemplate.query(sql, params, sessionRowMapper);
	}

	@Override
	public List<Session> getAllUserSessions(int userId) {
		String sql = "SELECT `SessionId`, `Name`, `Start`, `End` FROM `Session` JOIN `SessionMembers` USING(SessionId) WHERE `UserId` = :userId ORDER BY `Start` DESC";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"userId", userId);
		return jdbcTemplate.query(sql, params, sessionRowMapper);
	}

	@Override
	public List<String> getSessionMembersLogins(int sessionId) {
		String sql = "SELECT `Login` FROM `SessionMembers` join `User` USING(UserId) WHERE `SessionId` = :sessionId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"sessionId", sessionId);
		return jdbcTemplate.queryForList(sql, params, String.class);
	}

}
