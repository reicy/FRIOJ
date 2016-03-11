<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>




<h2 id="addProblemHeader">Upload in out files</h2>

<div class="responseMSG">
	<c:out value="${msg}"></c:out>
</div>


<form accept-charset="UTF-8" action="/frioj/admin/addProblemInOut" method="post" enctype="multipart/form-data">

	<div class="addProblemField">	
		<span>Problem Id</span>
		<div>
			<input type="number" value="" step="1" name = "problemId">
		</div>
	</div>

	<div class="addProblemField">	
		<span>In</span>
		<div>
			<input type="file"  name = "inFile">
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>Out</span>
		<div>
			<input type="file"  name = "outFile">
		</div>
	</div>
		
	<input type="submit" id="addProblemSubmit" value="submit">	
</form>