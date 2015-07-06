<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<h2>Contact me</h2>

<div class="responseMSG">
	<c:out value="${response }"></c:out>
</div>

If you find bug or you have suggestion, please send us message. TIA
<br/>
<br/>
<form action="/frioj/sendMsg" method="post">

	<textarea rows="15" cols="90" name="content" placeholder="enter message" maxlength="1900"></textarea><br/>
	<br/>
	<br/>
	<input type="submit"/>
	<input type="reset" />
</form>
