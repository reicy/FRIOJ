<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tiles1"%>
    
<!DOCTYPE html>
<html>
    <head>
    	<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0" content="text/html;charset=UTF-8">
    	
    
        <link href="<c:url value="/resources/css/mainCss.css" />" rel="stylesheet" type="text/css"/>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/mainJs.js" />"> </script>
        <script src='https://www.google.com/recaptcha/api.js'></script>
        
        <!-- code highlight -->
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.2.0/styles/default.min.css">
		<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.2.0/highlight.min.js"></script>
		
        
        <!-- polymer -->
        
        <!-- 
        <script src="/resources/js/polymer/webcomponentsjs/webcomponents.js"></script>
        
        <script src="/resources/js/polymer/webcomponentsjs/webcomponents-lite.min.js"></script>
         -->
        <script src="<c:url value="/resources/js/polymer/webcomponentsjs/webcomponents.min.js"/>"></script>
        
        <link rel="import" href="<c:url value="/resources/js/polymer/elements/simpleElem.html"/>">
		
		<tiles1:useAttribute name="javascripts" id="javascripts" ignore="true" />
		
		<c:forEach items="${javascripts}" var="jsfile">
        	<script type="text/javascript" src="<c:url value="${jsfile}"/>"></script>
    	</c:forEach>
    	
    	<title><tiles:insertAttribute name="title" defaultValue="no title"/></title>

    </head>
    <body>
    
       
        <div id="main">
        <a href="/frioj/" style="text-decoration: none">
        <header>
        	
	            <span id="fri">FRI</span>
	            <span id="oj">online judge</span>
           
        </header>
        </a><!-- TODO inak -->
        <div id="mid">
            <div id="menu">
                <h2>
                    Menu
                </h2>
                <ul>
                    <li><a href="/frioj/rankList">ranklist</a></li>
                    
                    <li><a href="/frioj/allProblems">problems</a></li>
                    <security:authorize access="isAuthenticated()">
                   		<li><a href="/frioj/searchProblem">search problem</a></li>
                    	<li><a href="/frioj/submit">submit</a></li>
                    	<li><a href="/frioj/displayAllSubmissions">submissions</a></li>
                    	<li><a href="/frioj/displayAllSessions">sessions</a></li>
                    	<li><a href="/frioj/displayAllArticles">articles</a></li>
                    	<li><a href="/frioj/displayAllTutorials">tutorials</a></li>
                    	<li><a href="/frioj/shoutbox/">shoutbox</a></li>
                    	<li><a href="/frioj/contactMeForm/">contact me</a></li>
                    	
                    	
                    	
                    </security:authorize>
                
                    <li><a href="/frioj/links" class="gap">links</a></li>
                    
                                        
                    <security:authorize access="isAuthenticated()" >
                   		<li><a href="/frioj/profile">account</a></li>
                    	<li><a href="/frioj/logout">logout</a></li>
                    </security:authorize>
                    
                    <security:authorize access="isAnonymous()">
                    	<li><a href="/frioj/login">login</a></li>
                    	<li><a href="/frioj/registrationForm">registration</a></li>
                    </security:authorize>
                    
                    <li><div id="gap"></div></li>
                    
                    <security:authorize access="hasAnyRole('Admin,Teacher')">
                    	<li><a href="/frioj/admin/addProblemForm">Add problem</a></li>
                    	<li><a href="/frioj/admin/addProblemInOutForm">Problem in out</a></li> 
                    	<li><a href="/frioj/admin/addArticleForm">Add article</a></li>
                    	<li><a href="/frioj/admin/submission">Display submission</a></li>
                    	<li><a href="/frioj/admin/displaySessionSubmissions">Session submissions</a></li>
                    	<li><a href="/frioj/admin/settings/">Settings</a></li>
                    	
                   
                    </security:authorize>
                    
                    <security:authorize access="hasRole('Admin')">
                    	<li><a href="/frioj/admin/updateUserRolesAndStatusesForm">User roles</a></li>
                    </security:authorize>
                    
                    
                    <div style="padding-bottom: 400px;"></div>
                   
                </ul>
               
            </div>
            
            <div id="webPageContent">
                
                 <tiles:insertAttribute name="content" />
                
            </div>
            
            
            
            
            
            
            
            
            
            
            
            
        </div>
       
            <footer>
                
                 <p>author TK</p>
            </footer>
            </div>
    </body>
</html>
