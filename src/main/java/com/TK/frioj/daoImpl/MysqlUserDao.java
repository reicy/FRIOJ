package com.TK.frioj.daoImpl;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.User;
import com.TK.frioj.enums.Roles;
import com.TK.frioj.rowMappers.UserRowMapper;

@Repository
@Transactional
public class MysqlUserDao implements UserDao {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MysqlUserDao.class);

	private NamedParameterJdbcOperations jdbcTemplate;

	@Autowired
	private UserRowMapper userRowMapper;

	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public int getUserId(String name) {

		int userId = -1;
		String sql = "SELECT `UserId` FROM `User` WHERE `Login`= :name";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"name", name);

		try {
			userId = jdbcTemplate.queryForObject(sql, params, Integer.class);
		} catch (DataAccessException ex) {

		}
		return userId;
	}

	public int existUser(String name) {

		String sql = "SELECT count(*) FROM `User` WHERE `Login`= :name";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"name", name);

		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	public Roles getUserRole(String name) {

		String sql = "SELECT `Authorization` FROM `User` WHERE `Login`= :name";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"name", name);

		Roles role = Roles.Anonymous;
		try {
			role = Roles.getRole(jdbcTemplate.queryForObject(sql, params, Integer.class).intValue());
		} catch (DataAccessException ex) {

		}

		return role;
	}

	@Override
	public List<String> getAllJuniorAndSeniorUsers() {
		String sql = "SELECT `Login` FROM `User` WHERE `Authorization` = '4' OR `Authorization` = '3'";
		return jdbcTemplate.queryForList(sql, new HashMap<String, String>(),String.class);
	}

	@Override
	public String getUserName(int id) {

		String sql = "SELECT `Login` FROM `User` WHERE `UserId`= :userId";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", id);

		return jdbcTemplate.queryForObject(sql, params, String.class);
	}

	@Override
	public void addUser(User user) {
		String sql = "INSERT INTO `User`(`Login`, `Password`, `UserName`, `Email`, `Info`, `Name`, `Surname`) "
				+ "VALUES ( :login , :password , :userName , :email , :info, :name, :surname)";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("login", user.getLogin())
				.addValue("password", user.getPassword())
				.addValue("userName", user.getUserName())
				.addValue("email", user.getEmail())
				.addValue("info", user.getInfo())
				.addValue("name", user.getName())
				.addValue("surname", user.getSurname());
		jdbcTemplate.update(sql, params);

	}

	@Override
	public void updateUser(User user) {

		String sql = "UPDATE `User` SET `UserName`= :userName ,"
				+ "`Email`= :email ,`Info`= :info "
				+ "WHERE `UserId` = :userId";

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userId", user.getUserId())
				.addValue("userName", user.getUserName())
				.addValue("email", user.getEmail())
				.addValue("info", user.getInfo());
		jdbcTemplate.update(sql, params);

	}

	@Override
	public User getUser(int userId) {
		String sql = "SELECT `UserId`, `Login`, `UserName`, `Authorization`, `Email`, `Info`, `Enabled`,`Name`, `Surname`  FROM `User` WHERE `UserId` = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		return jdbcTemplate.queryForObject(sql, params, userRowMapper);
	}

	@Override
	public User getUser(String name) {
		String sql = "SELECT `UserId`, `Login`, `UserName`, `Authorization`, `Email`, `Info`, `Enabled`,`Name`, `Surname`  FROM `User` WHERE `Login` = :name";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", name);
		return jdbcTemplate.queryForObject(sql, params, userRowMapper);
	}

	@Override
	@PreAuthorize("hasRole('Admin')")
	public void changeUserRole(int userId, Roles newRole) {
		String sql = "UPDATE `User` SET `Authorization`= :role WHERE `UserId` = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"userId", userId).addValue("role", newRole.getNum());
		jdbcTemplate.update(sql, params);

	}

	@Override
	@PreAuthorize("hasRole('Admin')")
	public void changeUserStatus(int userId, boolean enabled) {
		logger.info("" + enabled);
		String sql = "UPDATE `User` SET `Enabled`= :enabled WHERE `UserId` = :userId";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId).addValue("enabled", (enabled) ? 1 : 0);
		jdbcTemplate.update(sql, params);

	}

	@Override
	public List<User> getAllUsersRolesAndStatusesExcept0() {
		String sql = "SELECT `UserId`, `Login`, `UserName`, `Authorization`, `Email`, `Info`, `Name`, `Surname`, `Enabled` FROM `User` WHERE `UserId` != 0";
		MapSqlParameterSource params = new MapSqlParameterSource();
		return jdbcTemplate.query(sql, params, userRowMapper);
		
	}

	@Override
	public String getPassword(String userName) {
		String sql = "SELECT `Password` FROM `User` WHERE `Login` = :userName ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"userName", userName);
		try {
			return jdbcTemplate.queryForObject(sql, params, String.class);
		} catch (EmptyResultDataAccessException ex) {
			return "";
		}
	}

	@Override
	public void updatePassword(String userName, String newPassword) {
		String sql = "UPDATE `User` SET `Password`= :pass WHERE `Login` = :userName ";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"userName", userName).addValue("pass", newPassword);
		jdbcTemplate.update(sql, params);
	}

	@Override
	public int existEmail(String email) {
		String sql = "SELECT count(*) FROM `User` WHERE `Email`= :email";

		MapSqlParameterSource params = new MapSqlParameterSource().addValue(
				"email", email);

		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}

}
