package com.TK.frioj.helpers;


import org.springframework.security.core.context.SecurityContextHolder;
import com.TK.frioj.enums.Roles;

public class AuthorizationHelper {
	
	public static boolean isSenior(){
		return Roles.getRoles().contains(Roles.Senior);
	}
	
	public static boolean isJunior(){
		return Roles.getRoles().contains(Roles.Junior);
	}
	
	public static boolean isAdmin(){
		return Roles.getRoles().contains(Roles.Admin);
	}
	
	public static boolean isTeacher(){
		return Roles.getRoles().contains(Roles.Teacher);
	}
	
	public static boolean isNotLogged(){
		return Roles.getRoles().contains(Roles.Anonymous);
	}
	
	public static boolean isTeacherOrAdmin(){
		return isTeacher() || isAdmin();
	}
	
	public static boolean isJuniorOrSenior(){
		return isJunior()||isSenior();
	}
	
	public static String getCurrentUserName(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
