<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Update Password</h2>

<div class="responseMSG">
	<c:out value="${response }" ></c:out>
</div>

<form action="/frioj/updatePassword" method="post" enctype="multipart/form-data">
	
	<div class="profileUpdate">
		
		<div class="singleProfileUpdateField">
			<div>Password</div>
			<input type="password" name="currentPass" />
		</div>
		
		<div class="singleProfileUpdateField">
			<div>New password</div>
			<input type="password" name="newPass1" />
		</div>
		
		<div class="singleProfileUpdateField">
			<div>Retype new password</div>
			<input type="password" name="newPass2" />
		</div>
	
	</div>
	
	<input type="submit"  value="submit">			
	
</form>
