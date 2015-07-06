<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<h2>Registration</h2>



<div class="red">

	<c:out value="${message}"></c:out>
	<br/><br/>
</div>

<div class="registrationForm">
	<form:form modelAttribute="userRegistrationDTO" action = "/frioj/registration" method="POST">
		
		<div class = "registrationField">
		
	       <label>Login</label>
	       <form:input path="login" value="" />
	       <form:errors path="login" element="div"/>
	    </div>
	    
	    <div class = "registrationField">
	       <label>Password</label>
	       <input name="password" value="" type="password" id="pass1"/>
	       <form:errors path="password" element="div" />
	    </div>
	    
	   <div class = "registrationField">    
	       <label>Verify password</label>
	       <input value="" type="password" id="pass2"/>
	   </div>
	    
	   <div class = "registrationField"> 
	       <label>Email</label>
	       <form:input path="email" value="" />
	       <form:errors path="email" element="div" />
	   </div>
	    
	   <div class = "registrationField">   
	       <input type = "submit" value = "submit" id="submit" />
	   </div>
	    </form:form>
    
</div>