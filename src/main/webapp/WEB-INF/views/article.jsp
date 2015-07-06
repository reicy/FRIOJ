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

<div class="articleTitle">

	<c:out value="${article.name}"></c:out>

</div>

<c:if test="${prerequisiteMathed==true }">
	<div class="articleContent">
	
		<c:out value="${article.content}" escapeXml="false"></c:out>
	
	</div>
</c:if>

<c:if test="${prerequisiteMathed==false }">
	<div class="articleContent">
	
		<h2>to unlock this article solve problem <c:out value="${article.pre}"></c:out></h2>
	
	</div>
</c:if>

<div class="articleAuthor">

	<c:out value="${article.author}" ></c:out>

</div>


