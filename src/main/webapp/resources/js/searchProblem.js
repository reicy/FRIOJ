var $problems;
var $length;

$( document ).ready(function() {
	
	
	
	   
	    $(document).on('keyup', '#searchBox', function () {
	    	$length =parseInt( $("#searchBox").val().length);
	    	
	    	if($length == 1 ){
	    		
	    		getProblemsContainingString($("#searchBox").val());
	    		
	    		
	    	}else{
	    		if($length == 0){
	    			$("#problemList").empty();
	    		}else{
	    			displayProblems($("#searchBox").val());
	    		}
	    	}
	    	
	    	
	    	
	    });
	    
	   	
	
});

function getProblemsContainingString(str){
	$.ajax({  
	    type : "Get",   
	    url : "/frioj/getProblemsContainingString?str="+str , 
	    data : "",  
	    success : function(response) {  
	    	
	    	
	   
	    	$problems = JSON.parse(response);
	    	displayProblems($("#searchBox").val());
	    	
	    },  
	    error : function(e) {  
	     alert('Error: ' + e);   
	    }  
	   });  
}

function displayProblems(str){
	$("#problemList").empty();
	
	$($problems).each(function(){
		
		if((this[0]+"").indexOf(str) > -1 || (this[1]+"").indexOf(str)> -1){
			$problemId = $("<div>").attr('value',this[0]).text(this[0]);
			$problemId.attr('class', "idColumn");
			$problemName = $("<div>").attr('value',this[1]).text(this[1]);
			$problemName.attr('class', "midFloatDiv");
			
			
			$problem = $("<div>");
			$problem.attr('class', "singleProblemDiv");
			$problem.append($problemId);
			$problem.append($problemName);
			$problem.attr("id",this[0]);
			$("#problemList").append($problem);
		}
		
		
		
				
		
	});
	
	$(".singleProblemDiv").click(function(){
		$("#searchBox").val("");
		window.location = "/frioj/problem/"+$(this).attr("id");
	});
}
