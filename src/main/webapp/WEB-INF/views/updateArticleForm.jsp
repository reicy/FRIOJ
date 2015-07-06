<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@  page 
import="com.TK.frioj.enums.Articles" 
%> 

<h2>Update Article</h2>

<form:form commandName="article"  action="/frioj/updateArticle" method="post" enctype="multipart/form-data">
	<form:input type = "hidden"  path="articleId"/>
	<table>
		<tr>
			<td>Name</td>
			<td>
				<form:input type="text" path="name" />			
			</td>
		</tr>
	
		<tr>
			<td>Note</td>
			<td>
				<form:textarea rows="10" cols="50" path="note"></form:textarea>		
			</td>
		</tr>
		
		<tr>
			<td>Content</td>
			<td>
				<form:textarea rows="10" cols="50" path="content"></form:textarea>		
			</td>
		</tr>
		
		
		<tr>
			<td>Status</td>
			<td>
				<select name="status">
  					<option value="1" <%if(request.getAttribute("status").equals(Articles.Hidden))out.print("selected=\"selected\""); %>>hidden</option>
  					<option value="2" <%if(request.getAttribute("status").equals(Articles.Article))out.print("selected=\"selected\""); %>>article</option>
					<option value="3" <%if(request.getAttribute("status").equals(Articles.Tutorial))out.print("selected=\"selected\""); %>>tutorial</option>
				
				</select>	
			</td>
		</tr>
	
			
	</table>
	<input type="submit"  value="submit">			
	
</form:form>
