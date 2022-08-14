var flagSubmit = true;
$(function(){
	$("body").css({
		"min-height":$(window).height()
	});
})
$(window).resize(function(){
	$("body").css({
		"min-height":$(window).height()
	});
})
$(".loginBtn").click(function(){
	var userName = $("#username").val().replace(/(^\s*)|(\s*$)/g, "");
	var passWord = $("#password").val().replace(/(^\s*)|(\s*$)/g, "");
	if(userName == ''){
		layer.msg("Please fill in the username");
	}else if(passWord == ''){
		layer.msg("Please fill in the passWord");
	}else{
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../loginStore/login',
	        type: 'post',
	        async:false,
	        data: {
	            "mobile": userName,
	            "password": passWord
	        }
	    }).done(function(data) {
	    		if (data.status == "SUCCESS") {
           		window.location.href="index.html";
            
        		} else {
            		layer.msg(data.message);
        		}
	    }).fail(function(r) {
	        layer.msg("error", {
	            time: 2000
	        },function(){
	        		window.location.href=window.location.href;
	        });
	    });   	
	}
});

document.onkeydown = function (event) {
    var e = event || window.event;
    if (e && e.keyCode == 13) { 
        $(".loginBtn").click(); 
    }
};