var base = new Base64();
$(function() {
	document.onkeydown = function(event){
		if(event.keyCode==13) {
			$(".clickBtn").click();
			return false;
		}
	} 
	$(".iframe").css({"height":$(window).height()});
    $(".code").click(function() {
        $(this).hide().attr('src', '../rest/qcb/admin/kaptchaImage').fadeIn();
    });
    $(".piaochecked").on("click", function() {
        if (!$(this).hasClass("on_check")) {
            $(this).css("border", "1px solid #c6c6c6");
        } else {
            $(this).css("border", "none");
        }
        $(this).toggleClass("on_check");
    });
});
if (getCookie("name")) {
    $(".telNum").val(getCookie("name"));
    $(".piaochecked").removeClass("on_check");
    $(".piaochecked").css("border", "none");
}
$(".clickBtn").click(function(event) {
    var phoneNumber = /^1(3|4|5|7|8)\d{9}$/; //手机号正则式
    //验证手机号正确性
    if (!phoneNumber.test($(".telNum").val().replace(/(^\s*)|(\s*$)/g, ""))) {
        $(".telNum").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    } else {
        $(".telNum").removeClass("light").parent().siblings(".tips").fadeOut();
    }
    //校验密码非空
    if ($(".password").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
        $(".password").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    } else {
        $(".password").removeClass("light").parent().siblings(".tips").fadeOut();
    }
    //校验验证码非空
    if ($(".codeinput").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
        $(".codeinput").addClass("light").focus().parent().siblings(".tips").fadeIn();
        return false;
    } else {
        $(".codeinput").removeClass("light").parent().siblings(".tips").fadeOut();
    }

	$(".loginBtn").addClass("loadingBtn").html("").removeClass("clickBtn");
    var form = $("#loginsubmit");
    $.ajax({
        url: form.attr("action"),
        type: 'post',
        async:false,
        data: {
            "loginname": base.encode($(".telNum").val().replace(/(^\s*)|(\s*$)/g, "")),
            "password": base.encode($(".password").val().replace(/(^\s*)|(\s*$)/g, "")),
            "kaptcha": $(".codeinput").val().replace(/(^\s*)|(\s*$)/g, ""),
            "CSRFToken":$('input[name="CSRFToken"]').val()
        }
    }).done(function(data) {
    	if (data.status == "SUCCESS") {
            if (!$(".piaochecked").hasClass('on_check')) {
                delCookie("name");
                setCookie("name", $(".telNum").val());
            } else {
                delCookie("name");
            }
            if(data.data==true){
            	$(".bindWeChatIframe").show();
            	document.getElementById("wechatAgainBtn").click();
            	wechatFlagUnbundling();
            }else{
            	window.location.href = "../qcb/index.jsp?islogin=0";
            }
            
        } else {
            layer.msg(data.message);
            $(".code").hide().attr('src', '../rest/qcb/admin/kaptchaImage').fadeIn();
            $(".loginBtn").removeClass("loadingBtn").html("登录").addClass("clickBtn");
        }
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    });
    return false;
});
$(window).resize(function(){
	$(".iframe").css({"height":$(window).height()});
});
var timer;
function wechatFlagUnbundling() {	
	timer = setTimeout("wechatFlagUnbundling()", 1000);		
	 $.ajax({
	        url: '../rest/qcb/admin/loginWechatResult',
	        type: 'get',
	        data: {
	            "timestamp":new Date().getTime() 
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
		    	if(r.data==true){
					clearTimeout(timer); 
					$(".iframe").hide();
					window.location.href = "../qcb/index.jsp";					
				}else{
					/*  layer.msg("绑定失败，请重新绑定");  */
				}
	    	}else{
	    		layer.msg(r.message);
	    	}	
	    }).fail(function(r){
	    	 layer.msg("error", {
	             time: 2000
	         });
	    });
	
}
$(".bindWeChatIframe .closeIframe").click(function(){
	clearTimeout(timer); 
	$(".iframe").hide();
	$(".loginBtn").removeClass("loadingBtn").html("登录").addClass("clickBtn");
});

$(".telNum").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		var phoneNumber = /^1(3|4|5|7|8)\d{9}$/; //手机号正则式
		if (!phoneNumber.test($(".telNum").val().replace(/(^\s*)|(\s*$)/g, ""))) {
	        $(".telNum").addClass("light").parent().siblings(".tips").fadeIn();
	    } else {
	        $(".telNum").removeClass("light").parent().siblings(".tips").fadeOut();
	    }
	}
});
$(".password").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".password").removeClass("light").parent().siblings(".tips").fadeOut();
	}
});
$(".codeinput").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".codeinput").removeClass("light").parent().siblings(".tips").fadeOut();
	}
});
$(".forgetPassword").click(function(){
	if($(".telNum").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		window.location.href="../qcb/forgetPassword.jsp";
	}else{
		var phoneNumber = /^1(3|4|5|7|8)\d{9}$/; //手机号正则式
		if (!phoneNumber.test($(".telNum").val().replace(/(^\s*)|(\s*$)/g, ""))) {
	        $(".telNum").addClass("light").parent().siblings(".tips").fadeIn();
	        layer.msg("请填写正确的手机号");
	    } else {
	        $(".telNum").removeClass("light").parent().siblings(".tips").fadeOut();
	        $(".forgetPassword").attr("href","../qcb/forgetPassword.jsp?tel="+$(".telNum").val().replace(/(^\s*)|(\s*$)/g, ""));
	    }
	}
});
