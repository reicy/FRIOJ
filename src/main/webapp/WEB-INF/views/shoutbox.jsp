<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>


<h2>ShoutBox</h2>


<form action="/frioj/shoutbox/addMessage" method="post">
	<textarea rows="10" cols="80" name="msg" maxlength="800"></textarea>
	<br/>
	<input type="submit" value="submit"/>
	
</form>

<c:forEach items="${messages}" var="shoutBoxMessage">
	
	<joda:format value="${shoutBoxMessage.date}" pattern="HH:mm" var="date" />
		
	<div class = "message">
			<c:out value="${shoutBoxMessage.sender}"/>
			<c:out value="${date}"/>
		
		<security:authorize access="hasAnyRole('Admin,Teacher')">
			<a class="greyButtonSizeLess grayBack roundedBorders" href="/frioj/shoutbox/deleteMessage/<c:out value="${shoutBoxMessage.shoutBoxMessageId }"/>">delete</a>
			
			<div class="inlineDiv">
			
				<input type="radio" name="<c:out value="${shoutBoxMessage.shoutBoxMessageId }"/>" value="true" <c:if test="${shoutBoxMessage.visibility}">checked</c:if> >visible
				<input type="radio" name="<c:out value="${shoutBoxMessage.shoutBoxMessageId }"/>" value="false" <c:if test="${!shoutBoxMessage.visibility}">checked</c:if>>hidden
			
			</div>
		
		</security:authorize>			
					
		<br/><br/>
					<c:out value="${shoutBoxMessage.content}"/>
	</div>
	<hr/>
</c:forEach>
 
 <br/><br/><br/>
 <security:authorize access="hasAnyRole('Admin,Teacher')">
			<a href="/frioj/shoutbox/deleteAllMessages" class="greyButtonSizeLess grayBack roundedBorders red">delete all</a>
</security:authorize>
