package com.TK.frioj.dao;

import java.util.List;

import com.TK.frioj.entities.User;
import com.TK.frioj.enums.Roles;

public interface UserDao {

	int getUserId(String name);
	
	String getUserName(int id);
	
	Roles getUserRole(String name);
	
	List<String> getAllJuniorAndSeniorUsers();
	
	void addUser(User user);
	
	void updateUser(User user);
	
	User getUser(int userId);
	
	User getUser(String name);
	
	void changeUserRole(int userId, Roles newRole);
	
	void changeUserStatus(int userId, boolean enabled);
	
	List<User> getAllUsersRolesAndStatusesExcept0();
	
    int existUser(String name);

	String getPassword(String userName);

	void updatePassword(String userName, String newPassword);

	int existEmail(String email);
	
}
