package com.TK.frioj.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.TK.frioj.entities.User;
import com.TK.frioj.enums.Roles;

@Component
public class UserRowMapper implements RowMapper<User>{
	
	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		User user = new User();
		
		user.setUserId(resultSet.getInt("UserId"));
		user.setUserName(resultSet.getString("UserName"));
		user.setInfo(resultSet.getString("Info"));
		user.setEmail(resultSet.getString("Email"));
		user.setLogin(resultSet.getString("Login"));
		user.setEnabled(resultSet.getInt("Enabled")==1);		
		user.setRole(Roles.getRole(resultSet.getInt("Authorization")));
		
		return user;
	}
}	