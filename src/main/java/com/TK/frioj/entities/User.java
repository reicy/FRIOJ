package com.TK.frioj.entities;

import com.TK.frioj.enums.Roles;

public class User {
	private int userId;
	private String login;
	private String userName;
	private Roles role;
	private String email;
	private String info;
	private boolean enabled;
	private String password;

	public User() {
		super();
	}

	public User(int userId, String login, String userName, Roles role,
			String email, String Info, boolean enabled, String password) {
		super();
		this.userId = userId;
		this.login = login;
		this.userName = userName;
		this.role = role;
		this.email = email;
		this.info = Info;
		this.enabled = enabled;
		this.password = password;
	}

	public User(String login, String userName, String email, String Info,
			String password) {
		super();
		this.userId = -1;
		this.login = login;
		this.userName = userName;
		this.role = Roles.Junior;
		this.email = email;
		this.info = Info;
		this.enabled = true;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
