<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>




<h2>Submissions</h2>

<table class="submissionTable">
	<tr>
		<th>id</th>
		<th>problem id</th>
		<th>status</th>
		<th>run time</th>
		<th>lang</th>
		<th>date</th>
	
	</tr>
	<c:forEach items="${submissionList}" var="submission">
		<joda:format value="${submission.date}" pattern="HH:mm dd-MM-YYYY" var="date" />
		<tr>
			<td>
				<c:out value="${submission.submissionId}"/>
			</td>
			
			<td>
				<a class="noDecorations" href="<c:out value="/frioj/problem/${submission.problem.problemId}"/>"><c:out value="${submission.problem.problemId}"/></a>
			</td>
				
			<td>
				<c:out value="${submission.status}"/>
			</td>
				
			<td>
				<c:out value="${submission.runTime}"/>
			</td>
				
			<td>
				<c:out value="${submission.lang}"/>
			</td>
				
			<td>
			
				<c:out value="${date}"/>
			</td>
					
		
	
		
		
		</tr>
		
		
		
		<security:authorize access="hasRole('Senior')||${isJuniorPlusEnabled}">
			<c:if test="${!submission.err.isEmpty() }">
				<tr class="SeniorSubmission">
					<td colspan="6" >
					
						<span class="clickMe">
							click for hint
						</span>
						<span class="notDisplayed">
							<c:out value="${submission.err }"></c:out>
						</span>
					</td>
				</tr>
			</c:if>
		</security:authorize>
		
	</c:forEach>
</table>


<div class="pageNumbers">
	
		<%@ page import="java.lang.Math" %>
		<%
			int currentPage = 1;
			int pages = 1;
			if(request.getAttribute("currentPage")!=null){
				currentPage = (Integer)(request.getAttribute("currentPage"));
			}
			
			if(request.getAttribute("pages")!=null){
				pages = (Integer)(request.getAttribute("pages"));
				
			}
			
			if(currentPage!=1 || pages!=1){
			
				if(currentPage==1){
				
					for(int i = 1; i <= Math.min( 3 , pages); i++){
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+i+"\">"+i+"</a>");
					}
					if(pages!=0)
					out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+Math.min(currentPage+1, pages)+"\">"+"next"+"</a>");
				
				}else{
					if(currentPage==pages){
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+Math.max(currentPage-1, 1)+"\">"+"prev"+"</a>");
						
						for(int i = Math.max(currentPage-2, 1); i <= currentPage; i++){
							out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+i+"\">"+i+"</a>");
						}
						
					}else{
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+Math.max(currentPage-1, 1)+"\">"+"prev"+"</a>");
	
						for(int i = currentPage-1; i <= Math.min( currentPage+1 , pages); i++){
							out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+i+"\">"+i+"</a>");
						}
						
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/displayAllSubmissions?page="+Math.min(currentPage+1, pages)+"\">"+"next"+"</a>");
						
					}
				}
			}
			
		%>
	</div>

