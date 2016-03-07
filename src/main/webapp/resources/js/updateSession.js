var $currentNumOfUserSelects = 0;
var $userNames;
var $currentNumOfProblemSelects = 0;
var $problemNames;
var $ajaxReqCounter = 0;
var $response;

$(document).ready(
		function() {
			$.ajax({
				type : "Get",
				url : "/frioj/admin/getSession/"
						+ document.getElementById('sessionId').value,
				data : "",
				success : function(response) {

					
					$response = response;
					
					generateSessionContent();

				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

			// all user names
			$.ajax({
				type : "Get",
				url : "/frioj/admin/getAllUserNames",
				data : "",
				success : function(response) {
					$userNames = JSON.parse(response);
					generateSessionContent();
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

			// all problem names and ids

			$.ajax({
				type : "Get",
				url : "/frioj/admin/getAllProblemNames",
				data : "",
				success : function(response) {
					$problemNames = JSON.parse(response);
					generateSessionContent();
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});

			$(document).on('change', '#start', function() {
				updateTotalTime();
			});

			$(document).on('change', '#end', function() {
				updateTotalTime();
			});
			$(document).on('change', '#days', function() {
				updateEndTime();
			});
			$(document).on('change', '#hours', function() {

				updateEndTime();
			});
			$(document).on('change', '#minutes', function() {
				updateEndTime();
			});

			$("#addMemberThroughName").click(function() {
				addMemberByNameInput();
			});

			$("#addProblemThroughId").click(function() {
				addProblemByIdInput();
			});

			$("#addMember").click(function() {
				addUserSelect();
			});
			$("#addProblem").click(function() {
				addProblemSelect();
			});
			$("#removeProblem").click(function() {
				removeProblemSelect();
			});

		});

function addUserSelect(name) {

	$memberDiv = $("<div>").attr('id', "memDiv" + $currentNumOfUserSelects);
	$deleteButton = $("<div>").attr('id', "" + $currentNumOfUserSelects);
	$deleteButton.attr('class', 'greyButtonDiv');
	$deleteButton.text("X");
	$container = $("<div>").attr('class', 'inlineDiv');

	$container.append("<select class='selectUserName' id=\"uS"
			+ $currentNumOfUserSelects + "\" name=\"uS"
			+ $currentNumOfUserSelects + "\" ></select>");

	$memberDiv.append($container);
	$memberDiv.append($deleteButton);
	$("#members").append($memberDiv);

	$($userNames).each(function() {
		$option = $("<option>").attr('value', this).text(this);
		if (typeof name !== 'undefined' && ("" + name) === ("" + this))
			$option.attr("selected", "\"selected\"");
		$("#uS" + $currentNumOfUserSelects).append($option);
	});

	$deleteButton.click(function() {
		$("#memDiv" + $(this).attr('id')).remove();
	});

	$currentNumOfUserSelects++;
}

function addMemberByNameInput() {

	$enteredName = $("#nameInputField").val();
	$position = $.inArray($enteredName, $userNames);
	if ($position > -1) {
		addUserSelect($enteredName);
	}
}

function addProblemByIdInput() {
	$enteredId = $("#problemIdInputField").val();
	$($problemNames).each(function() {
		if (this[0] === $enteredId) {
			addProblemSelect(this);

		}

	});

}

function addProblemSelect(name) {

	$problemDiv = $("<div>")
			.attr('id', "probDiv" + $currentNumOfProblemSelects);
	$deleteButton = $("<div>").attr('id', "" + $currentNumOfProblemSelects);
	$deleteButton.attr('class', 'greyButtonDiv');
	$deleteButton.text("X");
	$container = $("<div>").attr('class', 'inlineDiv');

	$container.append("<select class='selectUserName' id=\"pS"
			+ $currentNumOfProblemSelects + "\" name=\"pS"
			+ $currentNumOfProblemSelects + "\" ></select>");

	$problemDiv.append($container);
	$problemDiv.append($deleteButton);
	$("#problems").append($problemDiv);

	$($problemNames).each(
			function() {
				$option = $("<option>").attr('value', this[0]).text(
						this[0] + " - " + this[1]);
				if (typeof name !== 'undefined' && ("" + name) === ("" + this))
					$option.attr("selected", "\"selected\"");
				$("#pS" + $currentNumOfProblemSelects).append($option);
			});

	$deleteButton.click(function() {
		$("#probDiv" + $(this).attr('id')).remove();
	});

	$currentNumOfProblemSelects++;
}

function removeUserSelect() {

	if ($currentNumOfUserSelects > 0)
		$currentNumOfUserSelects--;
	$("#uS" + $currentNumOfUserSelects).remove();
}

function removeProblemSelect() {

	if ($currentNumOfProblemSelects > 0)
		$currentNumOfProblemSelects--;
	$("#pS" + $currentNumOfProblemSelects).remove();
}

function updateTotalTime() {

	var $start = new Date($("#start").val());
	var $end = new Date($("#end").val());
	var $totalTime = new Date($end - $start);

	$("#days").val(Math.floor(($end - $start) / 86400000));
	$("#hours").val($totalTime.getHours()-1);
	$("#minutes").val($totalTime.getMinutes());

}

function updateEndTime() {

	var $start = new Date($("#start").val());
	var $days = $("#days").val();
	var $hours = $("#hours").val();
	var $minutes = $("#minutes").val();
	var $diff = new Date($days * 86400000 + $hours * 3600000 + 60000 * $minutes);
	var $end = new Date($start.getTime() + $diff.getTime());
	document.getElementById("end").value = $end.toISOString().substring(0, 19);// "2014-11-16T15:25:33";//start.toISOString();

}

function generateSessionContent(){
	
	$ajaxReqCounter++;
	//console.log($ajaxReqCounter);
	
	if($ajaxReqCounter >=3 ){
		
		var json = JSON.parse($response);
		var start = new Date(json.start);
		start.setMilliseconds(start.getMilliseconds() + 2*3600000);
		var end = new Date(json.end);
		end.setMilliseconds(start.getMilliseconds() + 2*3600000);
		
		
		var userNames = json.userNames;
		var problemNames = json.problemNames;

		//document.getElementById("start").value = start;
				//.toISOString().substring(0, 19);// "2014-11-16T15:25:33";//start.toISOString();
		//document.getElementById("end").value = end;
		//.toISOString().substring(0, 19);// "2014-11-16T15:25:33";//start.toISOString();

		updateTotalTime();

		$(userNames).each(function() {
			addUserSelect(this);
		});
		$(problemNames).each(function() {
			addProblemSelect(this);
		});
		
		
	}
	
	
}