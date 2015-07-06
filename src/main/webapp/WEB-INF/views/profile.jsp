<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<div class="profileUsernameSearch">
	<form action="/frioj/profile" enctype="multipart/form-data">
		Enter username: <input type = "text" name = "name"/>
		<input type = "submit" value = "submit" />
	</form>
</div>

<div class="profile">	
<h2><c:out value="${user.login}"></c:out></h2>

	<div class = "userName">
		<span>name: </span><c:out value="${user.userName}"/>
	</div>
	
	<div class = "userRole">
		<span>status: </span><c:out value="${role}"/>
	</div>

	
	
	
	<div class = "userDescripton">
		<span>description: </span>
		<c:if test="${user.info.equals(\"\")}">no description found</c:if>
		<div class="preformated"><c:out  value="${user.info}"/>
		</div>
		
	</div>
	
	
	<c:if test="${displayUpdateButton}">
		<a class="greyButtonSizeLess greyBackground right" href="/frioj/updateProfileForm">update profile</a>
		<a class="greyButtonSizeLess greyBackground right" href="/frioj/updatePasswordForm">change password</a>
		
	</c:if>
	
	
	<div class="profileStats">
		<h4>Stats</h4>
		
		<div class="singleUserStat">
			<div>Total submissions:</div>
			<c:out value="${userStatistics.count }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>ACs:</div>
			<c:out value="${userStatistics.ac }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>WAs:</div>
			<c:out value="${userStatistics.wa }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>RTEs:</div>
			<c:out value="${userStatistics.rte }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>CEs:</div>
			<c:out value="${userStatistics.ce }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>TLEs:</div>
			<c:out value="${userStatistics.tle }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>Average AC runtime to limit:</div>
			<c:out value="${userStatistics.averageACTime }"></c:out>
		</div>
		
		<div class="singleUserStat">
			<div>Solved:</div>
			<c:set var="total" value="${fn:length(userStatistics.solved)}" />
			<c:forEach items="${userStatistics.solved }" var="solved" varStatus="current">
				<c:out value="${solved}"></c:out>
				<c:if test="${current.count <= (total - 1)}">,</c:if>
			</c:forEach>
			
		</div>
		
		<div class="singleUserStat">
			<div>Tried but not solved:</div>
			<c:set var="total" value="${fn:length(userStatistics.triedNotSolved)}" />
			<c:forEach items="${userStatistics.triedNotSolved }" var="triedNotSolved" varStatus="current">
				<c:out value="${triedNotSolved}"></c:out>
				<c:if test="${current.count <= (total - 1)}">,</c:if>
			</c:forEach>
		</div>
		
	</div>

</div>
