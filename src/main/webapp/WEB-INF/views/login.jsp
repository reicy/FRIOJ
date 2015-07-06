<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<h2>Login</h2>

<form action="/frioj/j_spring_security_check" method="post">
	
		<input type="text" name="login" placeholder="username" id="username" maxlength="20"/>
		<br/><br/>
		<input type="password" name="pass" placeholder="password" id="password" maxlength="20"/>
		<br/><br/>
		
		<input type="submit"/>
</form>
