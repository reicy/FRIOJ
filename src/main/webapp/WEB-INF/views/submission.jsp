<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Submission</h2>

<form action="/frioj/admin/submission" enctype="multipart/form-data">
	Enter submission id: <input type = "text" name = "submissionId"/>
	<input type = "submit" value = "submit" />
</form>

<c:if test="${submission!=null}"> 
	<joda:format value="${submission.date}" pattern="HH:mm dd-MM-YYYY" var="date" />
	
	<div class="submissionDetails">
		<div>
			<span>Id:</span><br/>
			<span>User:</span><br/>
			<span>Problem Id:</span><br/>
			<span>Status</span><br/>
			<span>RunTime:</span><br/>
			<span>Lang:</span><br/>
			<span>Date:</span><br/>
		</div>
		<div>
			<c:out value="${submission.submissionId }"/><br/>
			<c:out value="${senderName }"/><br/>
			<a class="noDecorations" href="<c:out value="/frioj/problem/${submission.problem.problemId}"/>"><c:out value="${submission.problem.problemId}"/></a><br/>
			<c:out value="${submission.status }"/><br/>
			<c:out value="${submission.runTime }"/><br/>
			<c:out value="${submission.lang }"/><br/>
			<c:out value="${date}"/><br/>
			
		</div>
	</div>
	
	<c:if test="${!submission.err.isEmpty() }">
		<div class="submissionErr">
			<h4>Errors</h4>
			<span>
				<c:out value="${submission.err }"></c:out>
			</span>
		</div>
	</c:if>
	
	
	<div class="submissionCode">
		<h4>Code</h4>
		<span >
<c:out value="${submission.sourceCode }"></c:out>
		</span>
	
	</div>

</c:if>

