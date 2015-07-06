<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
 
 
<!DOCTYPE html>
<html>
    <head>
        <link href="<c:url value="/resources/css/errPage.css" />" rel="stylesheet" type="text/css"/>
       
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>error page</title>
		
</head>
<body> 

	<div id="content">
		<div id="errorPageContent">
		
			<h1>We are sorry</h1>
			<p>
				<c:out value="${response }">Unknown error</c:out>
				
				
			</p>
			<div>
				<a href="/frioj/">Return to the homepage</a>
			</div>
		
		</div>
	</div>

</body>
</html>