package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UniversalStringStringArrayRowMapper implements RowMapper<String []>{

	@Override
	public String [] mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String [] pair = new String[2];
		pair[0]= resultSet.getString(1);
		pair[1]= resultSet.getString(2);
		
		return pair;
	}

}