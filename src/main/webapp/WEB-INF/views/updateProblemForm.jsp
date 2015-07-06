<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Update Problem <% out.print(request.getAttribute("problemId"));%></h2>

<form:form accept-charset="UTF-8" commandName="problem"  action="/frioj/admin/updateProblem" method="post" enctype="multipart/form-data">
	<input type = "hidden" name = "problemId" value="<% out.print(request.getAttribute("problemId"));%>"/>
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
				<form:input type="text" path="note" />			
			</td>
		</tr>
		
		<tr>
			<td>Text</td>
			<td>
				<form:textarea rows="10" cols="50" path="text"></form:textarea>		
			</td>
		</tr>
		
		<tr>
			<td>Input</td>
			<td>
				<form:textarea rows="10" cols="50" path="input"></form:textarea>	
			</td>
		</tr>
		
		<tr>
			<td>Output</td>
			<td>
				<form:textarea rows="10" cols="50" path="output"></form:textarea>		
			</td>
		</tr>
		
		<tr>
			<td>Time Limit(milliseconds)</td>
			<td>
				<form:input type="number" path="timeLimit" step="1" min="0"/>	
			</td>
		</tr>
		
		<tr>
			<td>Memory Limit(MB)</td>
			<td>
				<form:input type="number" path="memoryLimit" step="1" min="0"/>	
			</td>
		</tr>
		
		<tr>
			<td>Max input file size(characters)</td>
			<td>
				<form:input type="number" path="maxInputFileSize" step="1" min="0"/>	
			</td>
		</tr>
		
		
		
		<tr>
			<td>Status</td>
			<td>
				<select name="status">
  					<option value="1" <%if(request.getAttribute("stav").equals("1"))out.print("selected=\"selected\""); %>>visible</option>
  					<option value="2" <%if(request.getAttribute("stav").equals("2"))out.print("selected=\"selected\""); %>>hidden</option>
				</select>	
			</td>
		</tr>
	
		<tr>
			<td>Add new/delete pdf</td>
			<td>
				<input type="checkbox"  name = "newPdf">			
			</td>
		</tr>
	
		<tr>
			<td>Pdf</td>
			<td>
				<input type="file"  name = "pdf">			
			</td>
		</tr>
	
	
	
	</table>
	<input type="submit"  value="submit">			
	
</form:form>
