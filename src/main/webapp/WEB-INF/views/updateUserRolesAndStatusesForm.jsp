<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<h2>Roles</h2>

<form action="/frioj/admin/updateUserRolesAndStatuses" method="post" enctype="multipart/form-data">
		
	<div class="userRoles">
		<c:forEach items="${userList}" var="user">
		
		
			<div class="singleUserRole">
			
				<div class="inlineDiv width130px ">
					<c:out value="${user.login}"/>
				</div>
				
				
				<div class="inlineDiv width130px ">
					<c:out value="${user.name}"/>
				</div>
				
				<div class="inlineDiv width130px ">
					<c:out value="${user.surname}"/>
				</div>
				
				<div class="inlineDiv">
				
					<input type="radio" name="<c:out value="${user.userId}"/>" value="0" <c:if test="${user.enabled==false}">checked</c:if> >Disabled
					<input type="radio" name="<c:out value="${user.userId}"/>" value="1" <c:if test="${user.enabled && user.role.num==1}">checked</c:if>>Admin
					<input type="radio" name="<c:out value="${user.userId}"/>" value="2" <c:if test="${user.enabled && user.role.num==2}">checked</c:if>>Teacher
					<input type="radio" name="<c:out value="${user.userId}"/>" value="3" <c:if test="${user.enabled && user.role.num==3}">checked</c:if>>Senior
					<input type="radio" name="<c:out value="${user.userId}"/>" value="4" <c:if test="${user.enabled && user.role.num==4}">checked</c:if> >Junior
		
				</div>
			
			</div>
		</c:forEach>
		
		<input type="submit" value="submit"/>
	</div>
	
	
</form>
