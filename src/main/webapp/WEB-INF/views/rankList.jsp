<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Ranklist</h2>

<form action="/frioj/rankList" enctype="multipart/form-data">
	Top N: <input type = "number" name = "topN" step="1"/>
	<input type = "submit" value = "submit" />
</form>

<br/><br/>

<table class="submissionTable">
	<tr>
		<th>Rank</th>
		<th>Nickname</th>
		<th>AC</th>
		<th>All</th>
	
	</tr>
	<c:forEach items="${rankList}" var="rankRow">
		
		<tr>
			<td>
				<c:out value="${rankRow.rank}"/>
			</td>
			
			<td>
				<a class="noDecorations" href="<c:out value="/frioj/profile?name=${rankRow.login}"/>"><c:out value="${rankRow.login}"/></a>
			</td>
				
			<td>
				<c:out value="${rankRow.numberOfACSubmissions}"/>
			</td>
				
			<td>
				<c:out value="${rankRow.numberOfAllSubmissions}"/>
			</td>
				
								
		
	
		
		
		</tr>
		
	</c:forEach>
</table>

