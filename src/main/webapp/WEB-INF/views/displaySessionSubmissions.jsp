<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<h2>Session submissions</h2>

<form action="/frioj/admin/displaySessionSubmissions" method="get"
	enctype="multipart/form-data">

	Session Id <input type="number" name="sessionId" /> Name <input
		type="text" name="name" /> <input type="submit" value="submit" /> <br />
	<br /> <br />

	<table class="submissionTable">
		<tr>
			<th>id</th>
			<th>user</th>
			<th>problem id</th>
			<th>status</th>
			<th>run time</th>
			<th>lang</th>
			<th>date</th>

		</tr>
		<c:forEach items="${submissions}" var="submission">
			<joda:format value="${submission.date}" pattern="HH:mm dd-MM-YYYY"
				var="date" />
			<tr>
				<td><a class="noDecorations"
					href="<c:out value="/frioj/admin/submission/?submissionId=${submission.submissionId}"/>"><c:out
							value="${submission.submissionId}" /></a></td>

				<td class="wrapped"><c:out value="${submission.userName}" /></td>

				<td><a class="noDecorations"
					href="<c:out value="/frioj/problem/${submission.problem.problemId}"/>"><c:out
							value="${submission.problem.problemId}" /></a></td>

				<td><c:out value="${submission.status}" /></td>

				<td><c:out value="${submission.runTime}" /></td>

				<td><c:out value="${submission.lang}" /></td>

				<td><c:out value="${date}" /></td>





			</tr>



		</c:forEach>
	</table>






	</div>


</form>

<custom-paginator url="/frioj/admin/displaySessionSubmissions/" class="centerVertAllignForPaginators"
	page="<c:out value="${page}"/>"
	minpage="<c:out value="${minPage}"/>"
	maxpage="<c:out value="${maxPage}"/>"
	params="<c:out value="${params}"/>"> 
</custom-paginator>





















