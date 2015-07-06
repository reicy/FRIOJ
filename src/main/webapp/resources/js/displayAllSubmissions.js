
$(document).ready(function() {
	$(".SeniorSubmission").click(function() {

		if ($(this).find('.clickMe').is(":visible")) {

			$(this).find('.clickMe').toggle();
			$(this).find('.notDisplayed').toggle();
		} else {
			$(this).find('.clickMe').toggle();
			$(this).find('.notDisplayed').toggle();
		}
	})
});
