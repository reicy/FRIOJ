package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProblemIdAndNameRowMapper implements RowMapper<String []>{

	@Override
	public String [] mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String [] pair = new String[2];
		pair[0]= resultSet.getString("ProblemId");
		pair[1]= resultSet.getString("Name");
		
		return pair;
	}

}