$(document).ready(
		function() {
			$("input[name=juniorPlus]").change(
					function() {
						sendToServerPost(
								"/frioj/admin/settings/changeJuniorPlus", $(
										this).val());
					});

			$("input[name=contestMode]").change(
					function() {
						sendToServerPost(
								"/frioj/admin/settings/changeContestMode", $(
										this).val());
					});

			$("#updateEmail").click(function() {
				submitEmail();
			});

			$("#updateMultipliers").click(function() {
				submitMultipliers();
			});

			$("#updateLocations").click(function() {
				submitLocations();
			});

			$("#updatePaginationRestrictions").click(function() {
				submitPaginationRestrictions();
			});

			$("#updateDefaultProblemSettings").click(function() {
				submitProblemDefaults();
			});
			
			$("#updateCompilationSettings").click(function() {
				submitCompilationLimits();
			});
			
			$("#updateRuntimeRestrictions").click(function() {
				submitRuntimeRestrictions();
			});
			
			$("#updateAdditionalRestrictions").click(function() {
				submitAdditionalRestrictions();
			});
			
			
			
			

		});

function sendToServerPost(url, value) {

	$.post(url, {
		value : value
	});

}

function submitMultipliers() {
	$.post("/frioj/admin/settings/multipliers", {
		cMultiplier : $("#cMultiplier").val(),
		cppMultiplier : $("#cppMultiplier").val(),
		javaMultiplier : $("#javaMultiplier").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	});

}

function submitEmail() {
	$.post("/frioj/admin/settings/email", {
		email : $("#email").val(),
	}, function(data, status) {
		$(".responseMSG").html(data);
	});

}

function submitLocations() {
	$.post("/frioj/admin/settings/sourcesLocations", {
		filesLocation : $("#friojFilesLocation").val(),
		chrootLocation : $("#friojChrootLocation").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	});

}

function submitPaginationRestrictions() {
	$.post("/frioj/admin/settings/paginationRestrictions", {
		articles : $("#maxArticles").val(),
		messages : $("#maxMessages").val(),
		problems : $("#maxProblems").val(),
		submissions : $("#maxSubmissions").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	});

}
function submitProblemDefaults() {
	$.post("/frioj/admin/settings/problemDefaults", {
		maxFileSize : $("#maxFileSize").val(),
		defaultMemoryLimit : $("#defaultMemoryLimit").val(),
		defaultTimeLimit : $("#defaultTimeLimit").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	}

	);

}

function submitRuntimeRestrictions() {
	$.post("/frioj/admin/settings/runtimeRestrictions", {
		maxTotalSubmissionRuntime : $("#maxTotalSubmissionRuntime").val(),
		maxOutputFileSize : $("#maxOutputFileSize").val(),
		stackMemoryLimitJava : $("#stackMemoryLimitJava").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	}

	);

}



function submitCompilationLimits() {
	$.post("/frioj/admin/settings/compilationLimits", {
		compilationTime : $("#maxCompileTime").val(),
		compilationMemory : $("#maxCompileVirtualMemory").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	}

	);

}

function submitAdditionalRestrictions() {
	$.post("/frioj/admin/settings/additionalRestrictions", {
		penalization : $("#penalization").val(),
		maxSubmissionQueueCapacity : $("#maxSubmissionQueueCapacity").val()
	}, function(data, status) {
		$(".responseMSG").html(data);
	}

	);

}

