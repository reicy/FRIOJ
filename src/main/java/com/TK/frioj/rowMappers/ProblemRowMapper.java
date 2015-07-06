package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.TK.frioj.entities.Problem;
import com.TK.frioj.enums.ProblemStatus;

@Component
public class ProblemRowMapper implements RowMapper<Problem>{

	@Override
	public Problem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Problem problem = new Problem();
		problem.setProblemId(resultSet.getInt("ProblemId"));
		problem.setSetterID(resultSet.getInt("SetterId"));
		problem.setName(resultSet.getString("Name"));
		problem.setNote(resultSet.getString("Note"));
		problem.setText(resultSet.getString("Text"));
		problem.setInput(resultSet.getString("Input"));
		problem.setOutput(resultSet.getString("Output"));
		problem.setStatus(ProblemStatus.getProblemStatus(resultSet.getInt("Status")));
		problem.setTimeLimit(resultSet.getInt("TimeLimit"));
		problem.setMemoryLimit(resultSet.getInt("MemoryLimit"));
		problem.setMaxInputFileSize(resultSet.getInt("MaxInputFileSize"));
		
		try{
			problem.setPdf(resultSet.getBytes("Pdf"));
		}catch(Exception ex){}
		
		return problem;
	}

}

