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

<%
	int row = 0;
	int acToAllTable [][][] = (int[][][])request.getAttribute("acToAllTable");
%>
<h2><c:out value="${session.name}"></c:out></h2>
<table class="sessionTable">
	<tr>
		<th></th>
		<c:forEach items="${session.problems}" var="problemId">
			<th class="firstRow">
				<a class="noDecorations" href="/frioj/problem/<c:out value="${problemId}"/>">
					<c:out value="${problemId}"></c:out>
				</a>
			</th>
		</c:forEach>
	</tr>
	
	<c:forEach items="${sessionMembersNames}" var="member">
		<tr>
			<th>
				<c:out value="${member}"/>
			</th>
			<%
				for(int i = 0;i<acToAllTable[row].length;i++){
					out.print("<td ");
					
						if(acToAllTable[row][i][1]>0)out.print("class=\"victory\"");
						else{
							if(acToAllTable[row][i][0]>0)out.print("class=\"defeat\"");
							
						}
					
					out.print(">");
					
					if(acToAllTable[row][i][0]>0)out.print(acToAllTable[row][i][0]);
					else out.print("-");
					out.print("</td>");
				}
				row++;
			%>
			
		</tr>
	</c:forEach>





</table>