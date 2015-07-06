package com.TK.frioj.enums;

import java.util.LinkedList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.TK.frioj.entities.User;

public enum Roles {

	Admin(1), Teacher(2), Senior(3), Junior(4), Anonymous(5);
	
	private int num;
	
	private Roles(int num){
		this.num = num;
	}

	public int getNum() {
		return num;
	}
	
	public static Roles getRole(int num){
		switch (num) {
		case 1:
			return Admin;
		
		case 2:
			return Teacher;
		
		case 3:
			return Senior;
		
		case 4:
			return Junior;
		}
		
		return Anonymous;
	}
	
	public static Roles getRole(String role){
		switch (role) {
		case "Admin":
			return Admin;
		
		case "Teacher":
			return Teacher;
		
		case "Senior":
			return Senior;
		
		case "Junior":
			return Junior;
		}
		
		return Anonymous;
	}

	public static boolean isTeacherOrAdmin(User user){
		return(user.getRole().equals(Admin)|| user.getRole().equals(Teacher));
	}

	public static LinkedList<Roles> getRoles() {
		LinkedList<Roles> roles = new LinkedList<Roles>();
		for (GrantedAuthority authority  : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			roles.add(Roles.getRole(authority.getAuthority()));
		}	
		return roles;
	}

	
}
