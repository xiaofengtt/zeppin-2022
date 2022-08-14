$(document).ready(function(){
	$(".switch-box").tap(function(){
		if($(".switch-bar").hasClass("switch-bar-true")){
			$(".switch-bar").removeClass("switch-bar-true").addClass("switch-bar-false");
			$(".switch-box").find("span").eq(0).removeClass("color-white");
		}else{
			$(".switch-bar").removeClass("switch-bar-false").addClass("switch-bar-true");
			$(".switch-box").find("span").eq(0).addClass("color-white");
		}
	});
	
	appendOpenId();
});
