<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<h2>Submit</h2>

<div class="responseMSG">
 <%
 if(request.getAttribute("response") != null){
		out.println(request.getAttribute("response"));
	}
%>
</div>

<form action="/frioj/submitProblem" method="post">

problem id <input type="text" name="problemId" size="5" value="<c:out value="${problemId}"/>"/><br/>

<br/>

<label for="javaRadio">Java</label>
<input type="radio" name="lang" id="javaRadio" value="Java" checked="checked"/>
<label for="cppRadio">C++</label>
<input type="radio" name="lang" id="cppRadio" value="CPP"/>
<label for="cRadio">C</label>
<input type="radio" name="lang" id="cRadio" value="C"/>
<br/>
<br/>
<textarea rows="15" cols="90" name="code" placeholder="paste or enter your code"></textarea><br/>
<br/>
<br/>
<input type="submit"/>
<input type="reset" />
</form>
