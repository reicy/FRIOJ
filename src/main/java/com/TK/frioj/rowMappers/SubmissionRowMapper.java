package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.SubmissionStatus;

@Component
public class SubmissionRowMapper implements RowMapper<Submission>{

	@Autowired
	private ProblemDao problemDao;
	
	@Override
	public Submission mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Submission submission = new Submission();
		submission.setSubmissionId(resultSet.getInt("SubmissionId"));
		submission.setUserId(resultSet.getInt("UserId"));
		submission.setProblem(problemDao.getProblem(resultSet.getInt("ProblemId")));
		submission.setSourceCode(resultSet.getString("SourceCode"));
		submission.setLanguage(Languages.valueOf(resultSet.getString("Language")));
		submission.setStatus(SubmissionStatus.valueOf(resultSet.getString("Status")));
		submission.setRunTime(resultSet.getInt("RunTime"));
		submission.setName(resultSet.getString("Name"));
		submission.setDate(new DateTime(Timestamp.valueOf(resultSet.getString("Date"))));
		submission.setErr(resultSet.getString("Err"));
		
		return submission;
	}

}