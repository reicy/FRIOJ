<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">

	$( document ).ready(function() {
		
		$("#addProblemTagButton").click(function(){
			window.location = "/frioj/addTagToProblem/<c:out value="${problem.problemId }"/>/"+$( "#tags option:selected" ).val();
		});
			
		
		
	});
	
</script>


<div class="problemName">
	<c:out value="${problem.name}"></c:out>
</div>

<div class="problemTags">
	<span>
		<c:forEach items="${problemTags}" var="tag">
			
			#<c:out value="${tag.hashTagName }"></c:out>&nbsp;
			<security:authorize access="hasAnyRole('Senior,Admin,Teacher')">
				<a class="greyButtonSizeLess" href="/frioj/removeTagFromProblem/<c:out value="${problem.problemId }"/>/<c:out value="${tag.hashTagId } "/>">X</a>
			</security:authorize>
			 <br/>
		
		</c:forEach>
	</span>
	
	<security:authorize access="hasAnyRole('Senior,Admin,Teacher')">
		<select id="tags">
			<c:forEach items="${tags }" var="tag">
				<option value="${tag.hashTagId }">
					<c:out value="${tag.hashTagName }"></c:out>
				</option>
			</c:forEach>
			
		</select>
		<div class="greyButtonSizeLess noWidth greyBackground roundedBorders" id="addProblemTagButton">add tag</div>
	</security:authorize>

</div>


<div class="problemInfo">
<div>
	<span>Id: </span><br/>
	<span>Note: </span><br/>
	<span>Time Limit: </span><br/>
	<span>Memory Limit: </span><br/>
	<span>First solution: </span><br/>
	<span>Url: </span><br/>
</div>

<div>
	<c:out value="${problem.problemId}"></c:out><br/>
	<c:out value="${problem.note}"></c:out><br/>
	<c:out value="${problem.timeLimit}"></c:out><br/>
	<c:out value="${problem.memoryLimit}"></c:out><br/>
	<c:out value="${firstSolverName}"></c:out><br/>
		<c:if test="${isPdfIncluded}">
			<a href="<c:out value="${pdfUrl}"/>"><c:out value="${problem.name}"/></a>
		</c:if>
		<br/>
</div>	


<div>
	
	<span>Total subs: </span><br/>
	<span>ACs: </span><br/>
	<span>WAs: </span><br/>
	<span>RTEs: </span><br/>
	<span>TLEs: </span><br/>
	<span>CEs: </span><br/>
	
</div>

<div>

	<c:out value="${problemStatistics.getCount()}"></c:out><br/>
	<c:out value="${problemStatistics.ac}"></c:out><br/>
	<c:out value="${problemStatistics.wa}"></c:out><br/>
	<c:out value="${problemStatistics.rte}"></c:out><br/>
	<c:out value="${problemStatistics.tle}"></c:out><br/>
	<c:out value="${problemStatistics.ce}"></c:out><br/>
</div>	



</div>
	
	<div>
	<security:authorize access="isAuthenticated()">
	
		
		<a href="/frioj/submit?problemId=<c:out value="${problem.problemId}"></c:out>"  class="greyButtonSizeLess grayBack roundedBorders">
				Submit
		</a>
	
		<security:authorize access="hasAnyRole('Admin,Teacher')">
			<a href="/frioj/admin/updateProblemForm?problemId=<c:out value="${problem.problemId}"></c:out>"  class="greyButtonSizeLess grayBack roundedBorders">
					Update
			</a>
		
		
		
		
		</security:authorize>
	
	</security:authorize>
</div>


<c:if test="${!problem.text.isEmpty()}">
	<div class="problemDescriptor">
		<h4>Description</h4>
		<c:out value="${problem.text}"/>
	</div>

</c:if>

<c:if test="${!problem.input.isEmpty()}">
	<div class="problemDescriptor">
		<h4>Input</h4>
		<c:out value="${problem.input}"/>
	</div>

</c:if>

<c:if test="${!problem.output.isEmpty()}">
	<div class="problemDescriptor">
		<h4>Output</h4>
		<c:out value="${problem.output}"/>
	</div>

</c:if>



<c:if test="${isPdfIncluded}">
	<div class="problemPdf">
		<object data="<c:out value="${pdfUrl}"/>" type="application/pdf" width="750" height="800"></object>
	</div>
</c:if>




