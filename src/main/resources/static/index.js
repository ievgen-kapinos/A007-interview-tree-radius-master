$(document).ready(function(){

	
	$("#submit").click(function() {

		var $submit = $("#submit");
		let $response = $("#response");

		$submit.attr("disabled", true);
		$response.val("Processing request ...");

		let request = { 
		  x: $("#x").val(),
		  y: $("#y").val(),
		  radius: $("#radius").val()
		}
						
		$.ajax({
		  method: "POST",
		  url: "search",
		  contentType: "application/json",
		  data: JSON.stringify(request)
		})
		.done(function(data, textStatus, jqXHR) {
			$response.val("Done\n" + JSON.stringify(data));
	    })
	    .fail(function(jqXHR, textStatus, errorThrown) {
	    	$response.val("Fail\nText status = " + textStatus + "\nError thrown = " + errorThrown + "\nResponse text = " + jqXHR.responseText);
	    })
		.always(function() {
			$submit.attr('disabled', false);
		});
	});
});
