<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>


<h2>Settings</h2>

	<div class="responseMSG"></div>
	
	<fieldset class="settingsFieldSet">
    	<legend>Junior Plus</legend>
   		<div class="inlineDiv">
			
				<input type="radio" name="juniorPlus" value="true" <c:if test="${juniorPlus}">checked</c:if> >enabled
				<input type="radio" name="juniorPlus" value="false" <c:if test="${!juniorPlus}">checked</c:if>>disabled
			
		</div>
   
  	</fieldset>

  	
  	<fieldset class="settingsFieldSet">
    	<legend>Contest Mode</legend>
   		<div class="inlineDiv">
			
				<input type="radio" name="contestMode" value="true" <c:if test="${contestMode}">checked</c:if> >enabled
				<input type="radio" name="contestMode" value="false" <c:if test="${!contestMode}">checked</c:if>>disabled
			
		</div>
   
  	</fieldset>

  	
  	<fieldset class="settingsFieldSet">
    	<legend>Language Multipliers</legend>
    	<div class="singleInputOptionDiv">
    		<div>c multiplier</div> <input type="number" id="cMultiplier" value="<c:out value="${cMultiplier}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>c++ multiplier</div> <input type="number" id="cppMultiplier" value="<c:out value="${cppMultiplier}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>java multiplier</div> <input type="number" id="javaMultiplier" value="<c:out value="${javaMultiplier}"/>"/>
    	</div>
    	
    	<div id="updateMultipliers" class="grayBack greyButtonSizeLess">Update</div>
    </fieldset>	
    
    <security:authorize access="hasRole('Admin')">
	    <fieldset class="settingsFieldSet">
	    	<legend>Source Locations</legend>
	    	<div class="singleInputOptionDiv">
	    		<div>chroot location</div> <input id="friojChrootLocation" type="text" value="<c:out value="${friojChrootLocation}"/>"/>
	    	</div>
	    	<div class="singleInputOptionDiv">
	    		<div>files location</div> <input id="friojFilesLocation"  type="text" value="<c:out value="${friojFilesLocation}"/>"/>
	    	</div>
	    	<div id="updateLocations" class="grayBack greyButtonSizeLess">Update</div>
	    </fieldset>	
    
    
	    <fieldset class="settingsFieldSet">
	    	<legend>Administrator email address</legend>
	    	<div class="singleInputOptionDiv">
	    		<div>email</div> <input id="email" type="text" value="<c:out value="${email}"/>"/>
	    	</div>
	    	<div id="updateEmail" class="grayBack greyButtonSizeLess">Update</div>
	    </fieldset>
    </security:authorize>
    
    <fieldset class="settingsFieldSet">
    	<legend>Pagination restrictions</legend>
    	<div class="singleInputOptionDiv">
    		<div>max articles</div> <input id="maxArticles"  type="number" value="<c:out value="${maxArticles}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>max messages</div> <input id="maxMessages"  type="number" value="<c:out value="${maxMessages}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>max problems</div> <input id="maxProblems"  type="number" value="<c:out value="${maxProblems}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>max submissions</div> <input  id="maxSubmissions" type="number" value="<c:out value="${maxSubmissions}"/>"/>
    	</div>
    	<div id="updatePaginationRestrictions" class="grayBack greyButtonSizeLess">Update</div>
    </fieldset>	
    
    <fieldset class="settingsFieldSet">
    	<legend>Default problem settings</legend>
    	
    	<div class="singleInputOptionDiv">
    		<div>max input file size(characters)</div> <input  id="maxFileSize" type="number" value="<c:out value="${maxFileSize}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>default memory limit(MB)</div> <input id="defaultMemoryLimit"  type="number" value="<c:out value="${defaultMemoryLimit}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>default time limit(millis)</div> <input id="defaultTimeLimit"  type="number" value="<c:out value="${defaultTimeLimit}"/>"/>
    	</div>
    	<div id="updateDefaultProblemSettings" class="grayBack greyButtonSizeLess">Update</div>
    </fieldset>	
    
    <fieldset class="settingsFieldSet">
    	<legend>Compilation</legend>
    	
    	<div class="singleInputOptionDiv">
    		<div>max compile time limit(sec)</div> <input  id="maxCompileTime" type="number" value="<c:out value="${maxCompileTime}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>max compile memory limit(KB)</div> <input id="maxCompileVirtualMemory"  type="number" value="<c:out value="${maxCompileVirtualMemory}"/>"/>
    	</div>
    	
    	<div id="updateCompilationSettings" class="grayBack greyButtonSizeLess">Update</div>
    </fieldset>	
    
     <fieldset class="settingsFieldSet">
    	<legend>Runtime restrictions</legend>
    	
    	<div class="singleInputOptionDiv">
    		<div>max total runtime limit(sec)</div> <input  id="maxTotalSubmissionRuntime" type="number" value="<c:out value="${maxTotalSubmissionRuntime}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>max output file size limit(MB)</div> <input id="maxOutputFileSize"  type="number" value="<c:out value="${maxOutputFileSize}"/>"/>
    	</div>
    	<div class="singleInputOptionDiv">
    		<div>java stack memory(MB)</div> <input id="stackMemoryLimitJava"  type="number" value="<c:out value="${stackMemoryLimitJava}"/>"/>
    	</div>
    	<div id="updateRuntimeRestrictions" class="grayBack greyButtonSizeLess">Update</div>
    </fieldset>	
    
    <fieldset class="settingsFieldSet">
    	<legend>Additional restrictions</legend>
    	
    	<div class="singleInputOptionDiv">
    		<div>time penalization for non AC submissions during sessions(min)</div> <input  id="penalization" type="number" value="<c:out value="${penalization}"/>"/>
    	</div>
    	
    	<div class="singleInputOptionDiv">
    		<div>max submission queue capacity</div> <input id="maxSubmissionQueueCapacity"  type="number" value="<c:out value="${maxSubmissionQueueCapacity}"/>"/>
    	</div>
    	
    	<div id="updateAdditionalRestrictions" class="grayBack greyButtonSizeLess">Update</div>
    </fieldset>	
    
    

    <a id="settingsResetToDefaults" href="/frioj/admin/settings/resetToDefaults" class="right greyButtonSizeLess">reset to defaults</a>
