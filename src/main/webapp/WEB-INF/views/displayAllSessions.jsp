<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Sessions</h2>

<security:authorize access="hasAnyRole('Teacher,Admin')">
	<form action="/frioj/admin/addSession">
	<h3>Add session</h3>
	name: <input type="text" name="name" maxlength="20"/>
	<input type="submit"/>
	</form>
</security:authorize>


<c:forEach items="${sessionList}" var="session">
	
		<joda:format value="${session.start}" pattern="HH:mm" var="start" />
		<joda:format value="${session.end}" pattern="HH:mm" var="end" />
		<joda:format value="${session.start}" pattern="dd.MM." var="startDayMonth" />
		<joda:format value="${session.end}" pattern="dd.MM." var="endDayMonth" />
			
		<div class="singleSession">
		
		<a  href="/frioj/session/<c:out value="${session.sessionId}"/>" class="greyButtonSizeLess">
			<span class="singleSessionName">
				<c:out value="${session.name}" />
			</span>
			
			<span class="singleSessionTime">
				<c:out value="${start} - ${end}"/>
			</span>
			
			<span class="singleSessionDate">
				<c:out value="( ${startDayMonth} - ${endDayMonth} )"/>
			</span>
		</a>
		
		<security:authorize access="hasAnyRole('Teacher,Admin')">
			
			<a class="greyButtonSizeLess  grayBack roundedBorders"  href="/frioj/admin/updateSessionForm/<c:out value="${session.sessionId}"/>">
				update
			</a>	
			
			<div onclick="deleteSth('session','<c:out value="${session.name}"/>','/frioj/admin/deleteSession/<c:out value="${session.sessionId}"/>')" class="greyButtonSizeLess  grayBack roundedBorders">
				delete
			</div>
			
		</security:authorize>

		</div>
	
	
</c:forEach>
 
