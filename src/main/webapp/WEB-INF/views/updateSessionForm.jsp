<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Update Session <% out.print(request.getAttribute("sessionId"));%></h2>


<form:form commandName="session"  action="/frioj/admin/updateSession" method="post" enctype="multipart/form-data">
	<input type = "hidden" name = "sessionId" id="sessionId" value="<% out.print(request.getAttribute("sessionId"));%>"/>
	
	<div class="updateSessionField">
		<div >name</div>
		<form:input type="text" path="name" />
	</div>
	
	<div class="updateSessionField">
		<div >start</div>
		<form:input type="datetime-local" step="1" id="start" path="start"/>
		<!-- 
		<form:input type="text" path="start" />
		 -->
	</div>
	
	<div class="updateSessionField">
		<div >end</div>
		<form:input type="datetime-local" step="1" id="end" path="end"/>
		<!-- 
		<form:input type="text" path="end" />
		-->
	</div>
	
	<div class="updateSessionField">
		<div >days</div>
		<input type="text" id="days" />
	</div>
	
	<div class="updateSessionField">
		<div >hours</div>
		<input type="text" id="hours"/>
	</div>
	
	<div class="updateSessionField">
		<div >minutes</div>
		<input type="text" id="minutes"/>
	</div>
	
	<div class="updateSessionFieldMembers">
		<div class="updateSessionFieldLabel" >members</div>
		<div id="members">
				
				
				
		</div>
		<div>
			<input type="text" id="nameInputField"/>
			<div id="addMemberThroughName" class="greyButtonDiv noWidth">
			add by name
			</div>
		</div>
		<div id="addMember" class="greyButtonDiv">
			add
		</div>
	</div>
	
	<div class="updateSessionFieldProblems">
		<div class="updateSessionFieldLabel">problems</div>
		<div id="problems">
				
				
				
		</div>
		<div>
			<input type="text" id="problemIdInputField"/>
			<div id="addProblemThroughId" class="greyButtonDiv noWidth">
			add by id
			</div>
		</div>
		<div id="addProblem" class="greyButtonDiv">
			add
		</div>
	</div>
	
	<input type = "submit" id="updateSessionSubmit" value = "submit"/>
</form:form>		