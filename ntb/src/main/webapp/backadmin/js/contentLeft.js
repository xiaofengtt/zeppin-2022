
$(document).ready(function() {
	var name = $("#adminName").html();
	if (name == ''){
		window.location.href = "../views/login.jsp";
	}
	 
	$(".btn-user-edit").colorbox({
		iframe : true,
		width : "400px",
		height : "620px",
		opacity : '0.5',
		overlayClose : false,
		escKey : true
	});
})