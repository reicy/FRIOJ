
$(document).ready(function() {      
	
	$("#submit").prop('disabled', "disabled");
	
	$(document).on('change', '#pass1', function () {
		
		if($("#pass1").val()!==$("#pass2").val()){
			
			$("#submit").prop('disabled', "disabled");
		}else{
			
				$("#submit").prop('disabled', "");
			
		}
    });
	
	$(document).on('change', '#pass2', function () {
		
		if($("#pass1").val()!==$("#pass2").val()){
			
			$("#submit").prop('disabled', "disabled");
		}else{
				$("#submit").prop('disabled', "");
		}
    });
	
});

function testPasswordForm(value){
	return /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{6,}$/.test(value);
}



