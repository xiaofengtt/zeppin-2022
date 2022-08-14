var flagSubmit = true;
$(".loginBtn").click(function(){
	var userName = $("#username").val().replace(/(^\s*)|(\s*$)/g, "");
	var passWord = $("#password").val().replace(/(^\s*)|(\s*$)/g, "");
	if(userName == ''){
		layer.msg("请输入用户名");
	}else if(passWord == ''){
		layer.msg("请输入密码");
	}else{
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../loginBack/login',
	        type: 'post',
	        async:false,
	        data: {
	            "username": userName,
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
//回车事件
document.onkeydown = function (event) {
    var e = event || window.event;
    if (e && e.keyCode == 13) { //回车键的键值为13
        $(".loginBtn").click(); //调用登录按钮的登录事件
    }
};