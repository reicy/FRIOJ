<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Update Profile</h2>

<form:form commandName="user"  action="/frioj/updateProfile" method="post" enctype="multipart/form-data">
	
	<div class="profileUpdate">
		
		<div class="singleProfileUpdateField">
			<div>Name</div>
			<form:input type="text" path="userName" />
		</div>
		
		<div class="singleProfileUpdateField">
			<div>Email</div>
			<form:input type="text" path="email" />
		</div>
		
		<div class="singleProfileUpdateField">
			<div>Description</div>
			<form:textarea rows="20" cols="70" path="info" maxlength="1500"></form:textarea>
		</div>
	
	</div>
	
	
	
	<input type="submit"  value="submit">			
	
</form:form>
