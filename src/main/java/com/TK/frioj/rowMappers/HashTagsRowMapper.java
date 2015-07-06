package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.TK.frioj.entities.HashTag;

@Component
public class HashTagsRowMapper implements RowMapper<HashTag>{
	
	@Override
	public HashTag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		HashTag hashTag = new HashTag();
		
		hashTag.setHashTagId(resultSet.getInt("HashTagId"));
		hashTag.setHashTagName(resultSet.getString("HashTagName"));
		
		return hashTag;
	}
}	