<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>


<h2 id="articleHeader">Create/Update Article</h2>

<form:form commandName="article"  action="/frioj/admin/updateArticle" method="post" enctype="multipart/form-data">
		<form:input type = "hidden"  path="articleId"/>
		
	<div class="articleField">	
		<span>name</span>
		<div>
			<form:input type="text" path="name" maxlength="255"/>	
		</div>
	</div>
	
	<div class="articleField">	
		<span>note</span>
		<div>
			<form:textarea rows="7" cols="60" path="note"></form:textarea>		
		</div>
	</div>
	
	<div class="articleField">
		<span>content</span>
		<div>
			<form:textarea rows="15" cols="60" path="content"></form:textarea>
		</div>		
	</div>
	
	<div class="articleField">
		<span>prerequisite</span>
		<div>
			<form:input type="text" path="pre" maxlength="255"/>	
		</div>		
	</div>
	
	<div class="articleField">	
		<span>status</span>
		<div>
			<select name="status">
			
				<option value="1" <c:if test="${article.status.num==1}">selected="selected"</c:if>>
					hidden
				</option>	
				
				<option value="2" <c:if test="${article.status.num==2}">selected="selected"</c:if>>
					article
				</option>	
				
				<option value="3" <c:if test="${article.status.num==3}">selected="selected"</c:if>>
					tutorial
				</option>	
				
			</select>	
		</div>
	</div>
	
	<input id="articleSubmit" type="submit"  value="submit">			
	
</form:form>
