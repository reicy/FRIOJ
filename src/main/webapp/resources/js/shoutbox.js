
$(document).ready(
		function() {

			$("input:radio").change(
					function() {
						sendToServerPost(
								"/frioj/shoutbox/changeMessageVisibility/"
										+ $(this).attr("name"), $(this).val());
					});

		});

function sendToServerPost(url, value) {

	$.post(url, {
		value : value
	});

}
