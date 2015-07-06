<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<h2>Problems</h2>


<div class="allProblemsTagSelectForm">
	<form action="/frioj/allProblems" method="post" enctype="multipart/form-data">
	
		<select id="tags" name="tagId">
			<option value="0">all</option>
			<c:forEach items="${tags}" var="tag">
				<option value="${tag.hashTagId }">
					<c:out value="${tag.hashTagName }"></c:out>
				</option>
			</c:forEach>
		</select>
		<input type="submit" value="display">
	</form>
</div>

<c:forEach items="${problemList}" var="problem">
	
	<div class="problemRow">
	
		<a  href="/frioj/problem/<c:out value="${problem.problemId}"/>" class="greyButtonSizeLess problemRowName black">
		
			<span class="problemRowNum <c:out value="${problem.idColor} "/>">
				<c:out value="${problem.problemId} "/>
			</span>
		
			<span class="<c:if test="${problem.status.numRepresentation==2 }">red</c:if>">
				<c:out value="${problem.name}"/>
			</span>
		
		</a>
	
	
		<security:authorize access="hasAnyRole('Teacher,Admin')">
			<a class="greyButtonSizeLess problemRowUpdate grayBack roundedBorders"  href="/frioj/admin/updateProblemForm?problemId=<c:out value="${problem.problemId}"/>">
				update
			</a>
			
		</security:authorize>
	
	</div>
	
	
</c:forEach>

<div class="pageNumbers">
	
		<%@ page import="java.lang.Math" %>
		<%
			int currentPage = 1;
			int pages = 1;
			int currentTag = -1;
			if(request.getAttribute("currentPage")!=null){
				currentPage = (Integer)(request.getAttribute("currentPage"));
			}
			
			if(request.getAttribute("pages")!=null){
				pages = (Integer)(request.getAttribute("pages"));
				
			}
			
			if(request.getAttribute("currentTag")!=null){
				currentTag = (Integer)(request.getAttribute("currentTag"));
				
			}
			
			if(pages>1){
			
				if(currentPage==1){
				
					for(int i = 1; i <= Math.min( 3 , pages); i++){
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+i+"&tagId="+currentTag+"\">"+i+"</a>");
					}
					out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+Math.min(currentPage+1, pages)+"&tagId="+currentTag+"\">"+"next"+"</a>");
				
				}else{
					if(currentPage==pages){
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+Math.max(currentPage-1, 1)+"&tagId="+currentTag+"\">"+"prev"+"</a>");
						
						for(int i = Math.max(currentPage-2, 1); i <= currentPage; i++){
							out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+i+"&tagId="+currentTag+"\">"+i+"</a>");
						}
						
					}else{
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+Math.max(currentPage-1, 1)+"&tagId="+currentTag+"\">"+"prev"+"</a>");
	
						for(int i = currentPage-1; i <= Math.min( currentPage+1 , pages); i++){
							out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+i+"&tagId="+currentTag+"\">"+i+"</a>");
						}
						
						out.print("<a class=\"greyButtonSizeLess\" href=\"/frioj/allProblems?page="+Math.min(currentPage+1, pages)+"&tagId="+currentTag+"\">"+"next"+"</a>");
						
					}
				}
			}
			
		%>
	</div>
