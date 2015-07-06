<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<h2 id="addProblemHeader">Add problem</h2>
<form accept-charset="UTF-8" action="/frioj/admin/addProblem" method="post" enctype="multipart/form-data">

	<div class="addProblemField">	
		<span>name</span>
		<div>
			<input type="text" value="" name = "name">
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>note</span>
		<div>
			<input type="text" value="" name = "note">
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>text</span>
		<div>
			<textarea rows="10" cols="50" name="text"></textarea>
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>input</span>
		<div>
			<textarea rows="10" cols="50" name="input"></textarea>
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>output</span>
		<div>
			<textarea rows="10" cols="50" name="output"></textarea>
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>time limit</span>
		<div>
			<input type="number" step="1" min="0" value="3000" name = "timeLimit">
		</div>
	</div>
	
	<div class="addProblemField">	
		<span>pdf</span>
		<div>
			<input type="file"  name = "pdf">
		</div>
	</div>
	
	<input type="submit" id="addProblemSubmit" value="submit">			
</form>
