package com.TK.frioj.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

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

import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.entities.Session;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.SubmissionStatus;
import com.TK.frioj.helpers.StringHelper;
import com.TK.frioj.rowMappers.SubmissionRowMapper;
import com.TK.frioj.rowMappers.UniversalStringStringArrayRowMapper;

@Repository
@Transactional
public class MysqlSubmissionDao implements SubmissionDao{

	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MysqlSubmissionDao.class);
	private NamedParameterJdbcOperations jdbcTemplate;
	
	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Autowired
	private SubmissionRowMapper submissionRowMapper;
	
	@Autowired
	private UniversalStringStringArrayRowMapper universalStringStringArrayRowMapper;
	
	
	@Override
	public void addSubmission(Submission submission) {
		String sql = "INSERT INTO `Submission`(`UserId`, `ProblemId`, `SourceCode`, "
				+ "`Language`, `Status`, `RunTime`, `Name`, `Err`) "
				+ "VALUES (:UserId,:ProblemId,:SourceCode,:Language,"
				+ ":Status,:RunTime,:Name,:Err)";
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("UserId", submission.getUserId())
		.addValue("ProblemId", submission.getProblem().getProblemId())
		.addValue("SourceCode", submission.getSourceCode())
		.addValue("Language", submission.getLanguage().toString())
		.addValue("Status", submission.getStatus().toString())
		.addValue("RunTime", submission.getRunTime())
		.addValue("Name", submission.getName())
		.addValue("Err", submission.getErr());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, (SqlParameterSource) params, keyHolder, new String[]{"SubmissionId"});
		submission.setSubmissionId( keyHolder.getKey().intValue());
	}
	
	@Override
	public void updateSubmission(Submission submission) {
		String sql = "UPDATE `Submission` set `Status` = :status , `RunTime` = :runtime , `Err` = :err WHERE `SubmissionId` = :submissionId";
				
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("submissionId", submission.getSubmissionId())
		.addValue("status", submission.getStatus().toString())
		.addValue("runtime", submission.getRunTime())
		.addValue("err", submission.getErr());
		
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public Submission getSubmission(int submissionId) {
		SqlParameterSource params = new MapSqlParameterSource("id", submissionId);
		String sql = "SELECT `SubmissionId`, `UserId`, `ProblemId`, `SourceCode`, `Language`, `Status`, `RunTime`, `Name`, `Date`, `Err` FROM `Submission` WHERE SubmissionId = :id";
		return jdbcTemplate.queryForObject(sql, params, submissionRowMapper);
	}
	

	@Override
	public List<Submission> getAllUserSubmissions(int userId) {
		SqlParameterSource params = new MapSqlParameterSource("userId", userId);
		String sql = "SELECT `SubmissionId`, `UserId`, `ProblemId`, `SourceCode`, `Language`, `Status`, `RunTime`, `Name`, `Date`, `Err` FROM `Submission` WHERE `UserId` = :userId ORDER BY `Date` desc";
		return jdbcTemplate.query(sql, params, submissionRowMapper);
	}

	@Override
	public List<Integer> getAllUserNotACSubmissions(int userId) {
		String sql = "SELECT `ProblemId` FROM `Submission` WHERE `UserId` = :userId AND `Status` IN ('WA','CE','TLE','RTE')";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		
		try{
			List<Integer> list = jdbcTemplate.queryForList(sql, params, Integer.class);
			
			return list;
		}catch(EmptyResultDataAccessException ex){
			
			return new ArrayList<Integer>();
		}
	}


	@Override
	public List<Integer> getAllUserSubmissionNumbersOfStatus(int userId, SubmissionStatus status) {
		String sql = "SELECT `ProblemId` FROM `Submission` WHERE `UserId` = :userId AND `Status` = '"+status.toString()+"'";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		try{
			List<Integer> list = jdbcTemplate.queryForList(sql, params, Integer.class);
			return list;
		}catch(EmptyResultDataAccessException ex){
			
			return new ArrayList<Integer>();
		}
		
		
	}

	@Override
	public List<Submission> getAllSessionSubmissions(Session session) {
		String sql = "SELECT `SubmissionId`, `UserId`, `ProblemId`, `SourceCode`, "
				+ "`Language`, `Status`, `RunTime`, `Name`, `Date`, `Err` FROM `Submission` "
				+ "WHERE `UserId` IN ("+StringHelper.addCommaDelim(session.getMembers())+") "
						+ "AND `ProblemId` IN("+StringHelper.addCommaDelim(session.getProblems())+") "
								+ "AND (Date BETWEEN :start AND :end )";
		
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("start", session.getStart().toDate())
		.addValue("end", session.getEnd().toDate());
		return jdbcTemplate.query(sql, params, submissionRowMapper);
	}

	@Override
	public Map<String, Integer> getAllUserSubmissionsCount() {
		String sql = "SELECT `Login` , COUNT(*) FROM `Submission` JOIN `User` USING(`UserId`) GROUP BY `Login`";
		List<String[]> list = jdbcTemplate.query(sql, new MapSqlParameterSource() , universalStringStringArrayRowMapper);
		Map<String,Integer> mapLoginNumberOfSolutions = new HashMap<String, Integer>();
		
		for (String[] pair : list) {
			mapLoginNumberOfSolutions.put(pair[0], Integer.parseInt(pair[1]));
		}
		
		return mapLoginNumberOfSolutions;
	}

	@Override
	public List<String[]> getTopNUserACSubmissionsCountDisticntOrdered(int topN) {
		String sql = "SELECT  `Login` , COUNT( DISTINCT `ProblemId` ) FROM `Submission` JOIN `User` USING(`UserId`) WHERE `Status` = 'AC' AND `Authorization` != 1 GROUP BY `Login` ORDER BY 2 DESC LIMIT :topN ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("topN", topN);
		return jdbcTemplate.query(sql, params , universalStringStringArrayRowMapper);
	}

	@Override
	public String getFirstSolverNameOfProblem(int problemId) {
		String sql = "SELECT  `Login` FROM `Submission` JOIN `User` USING(`UserId`) WHERE `Status` = 'AC' AND `Authorization` != 1 AND `ProblemId` = :problemId ORDER BY `Date` ASC LIMIT 1";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("problemId", problemId);
		return jdbcTemplate.queryForObject(sql, params , String.class);
	
	}

	@Override
	public List<String[]> getCountOfEachStatusForProblem(int problemId) {
		String sql = "SELECT `Status`, COUNT(`Status`) FROM `Submission` WHERE `ProblemId` = :problemId GROUP BY `Status`";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("problemId", problemId);
		return jdbcTemplate.query(sql, params , universalStringStringArrayRowMapper);
	}
	
	
	@Override
	public List<String[]> getCountOfEachStatusForUser(int userId) {
		String sql = "SELECT `Status`, COUNT(`Status`) FROM `Submission` WHERE `UserId` = :userId GROUP BY `Status`";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		return jdbcTemplate.query(sql, params , universalStringStringArrayRowMapper);
	}

	@Override
	public int getUserSolvedProblemsCount(int userId) {
		String sql = "SELECT COUNT(DISTINCT `ProblemId`) FROM `Submission` WHERE `UserId` = :userId AND `Status`='AC';";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		try{
			return jdbcTemplate.queryForObject(sql, params, Integer.class);
		}catch(EmptyResultDataAccessException ex){
			return 0;
		}
	}

	@Override
	public int getAllUserSubmissionNumbersOfStatusCount(int userId,	SubmissionStatus subStat) {
		String sql = "SELECT COUNT(*) FROM `Submission` WHERE `UserId` = :userId AND `Status` = :status ";
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("userId", userId)
		.addValue("status", subStat.toString());
		
		try{
			return jdbcTemplate.queryForObject(sql, params, Integer.class);
		}catch(EmptyResultDataAccessException ex){
			return 0;
		}
	
	}

	@Override
	public int getAllUserSubmissionsCount(int userId) {
		String sql = "SELECT COUNT(*) FROM `Submission` WHERE `UserId` = :userId ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		try{
			return jdbcTemplate.queryForObject(sql, params, Integer.class);
		}catch(EmptyResultDataAccessException ex){
			return 0;
		}
		
	}

	@Override
	public double getUserAverageACSubsRunTime(int userId) {
		String sql = "SELECT AVG(`RunTime`/`TimeLimit`) FROM `Submission` JOIN `Problem` USING(`ProblemId`) WHERE `UserId` = :userId AND `Submission`.`Status`='AC'";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		
		try{
			Double time = jdbcTemplate.queryForObject(sql, params, Double.class);
			return (time==null)?0:time;
		}catch(EmptyResultDataAccessException ex){
			return 0;
		}
	}

	@Override
	public List<Integer> getAllUserTriedProblems(int userId) {
		String sql = "SELECT DISTINCT `ProblemId` FROM `Submission` WHERE `UserId` = :userId ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		
		try{
			return jdbcTemplate.queryForList(sql, params, Integer.class);
		}catch(EmptyResultDataAccessException ex){
			return new LinkedList<Integer>();
		}
	}

	@Override
	public List<Integer> getAllUserSolvedProblems(int userId) {
		String sql = "SELECT DISTINCT `ProblemId` FROM `Submission` WHERE `UserId` = :userId AND Status = 'AC'";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		
		try{
			return jdbcTemplate.queryForList(sql, params, Integer.class);
		}catch(EmptyResultDataAccessException ex){
			return new LinkedList<Integer>();
		}
	}

	@Override
	public boolean hasUserSolvedProblem(int problemId, int userId) {
		String sql = "SELECT  COUNT(*) FROM `Submission` WHERE `ProblemId` = :problemId AND `UserId` = :userId AND `Status` = 'AC'";
		MapSqlParameterSource params = new MapSqlParameterSource()
		.addValue("userId", userId)
		.addValue("problemId",problemId);
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class).intValue()>=1;
		
	}
	

	
}
