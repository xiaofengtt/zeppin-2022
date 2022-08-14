var smsCode = true; //是否发送验证码标识
var tel = url('?tel');
$(function(){
	document.onkeydown = function(event){
		if(event.keyCode==13) {
			$(".completeBtn").click();
			return false;
		}
	}
	$(".code").click(function() {
        $(this).hide().attr('src', '../rest/backadmin/operator/kaptchaImage').fadeIn();
    });
    $(".smsCode").click(function() {
        sendCode();
    });
    if(tel!=null){
    	$(".mobile").val(tel);
    }
});
//短信验证码倒计时
var invokeSettimes;
function invokeSettime() {
    var delay = Number($(".smsCode span").html());
    invokeSettimes = setTimeout("invokeSettime()", 1000);
    if (delay > 1) {
        delay--;
        $(".smsCode span").html(delay);
    } else {
        clearTimeout(invokeSettimes);
        smsCode = true;
        $(".smsCode").html("重新获取验证码");
    }
}
//发送短信验证码接口
function sendCode() {
    var phoneNumber = /^1(3|4|5|7|8)\d{9}$/; //手机号正则式
    //验证手机号正确性
    if (!phoneNumber.test($(".mobile").val().replace(/(^\s*)|(\s*$)/g, ""))) {
        $(".mobile").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
        return false;
    } else {
        $(".mobile").removeClass("light").parent().siblings(".tipsBox").fadeOut();
    }
    //校验验证码非空
    if ($(".kaptcha").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
        $(".kaptcha").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
        return false;
    } else {
        $(".kaptcha").removeClass("light").parent().siblings(".tipsBox").fadeOut();
    }
    if (smsCode) {
        var form = $("#getCodesubmit");
        smsCode = false;
        $(".smsCode").html("<span>60</span>s后，重新获取");
        invokeSettime();
        $.post(form.attr("action"), "mobile=" + new Base64().encode($(".mobile").val().replace(/(^\s*)|(\s*$)/g, "")) +
            "&kaptcha=" + new Base64().encode($(".kaptcha").val().replace(/(^\s*)|(\s*$)/g, "")) +
            "&codeType=" + new Base64().encode("resetpwd") +
            "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
            function(data) {
                if (data.status == "SUCCESS") {
                    layer.msg(data.message);
                } else {
                    $(".code").hide().attr('src', '../rest/backadmin/operator/kaptchaImage').fadeIn();
                    layer.msg(data.message);
                    clearTimeout(invokeSettimes);
                    smsCode = true;
                    $(".smsCode").html("重新获取验证码");
                }
            });
        return false;
    }
}

$(".completeBtn").click(function(){
	if(checkNonempty($(".newPassword").val())&&checkRegistPassword($(".newPassword").val())){
		$(".newPassword").removeClass("light").parent().siblings(".tipsBox").fadeOut();			
	}else{
		$(".newPassword").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
		return false;
	}
	if($(".newPassword").val().replace(/(^\s*)|(\s*$)/g, "")==$(".confirmPassword").val().replace(/(^\s*)|(\s*$)/g, "")){
		$(".confirmPassword").removeClass("light").parent().siblings(".tipsBox").fadeOut();			
	}else{
		$(".confirmPassword").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
		return false;
	}
	 //密码强度验证
    var passwordLevel =3;
    var strongRegex = new RegExp("^(?=.{10,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
    var mediumRegex = new RegExp("^(?=.{9,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
    var enoughRegex = new RegExp("(?=.{8,}).*", "g"); 
    if (strongRegex.test($(".newPassword").val())){
    	passwordLevel = 5;
    }else if(mediumRegex.test($(".newPassword").val())){
    	passwordLevel = 3;
    }else if(enoughRegex.test($(".newPassword").val())){
    	passwordLevel = 1;
    }
	$(".secConfirm").addClass("loadingBtn").html("").removeClass("completeBtn");
	var form = $("#resetsubmit");
	$.post(form.attr("action"),form.serialize()+"&passwordLevel="+passwordLevel, function(data) {
		if (data.status == "SUCCESS") {
			$(".firstContent,.secContent").hide();
            $(".thirdContent").show();
            $(".thirdStep").addClass("light");
            setTimeout('delayURL()', 1000);
		} else {
			layer.msg(data.message);
		}
		$(".secConfirm").removeClass("loadingBtn").html("确定").addClass("completeBtn");
	});		
	return false;
});
//校验短信验证码正确性
$(".continueBtn").click(function() {
    var phoneNumber = /^1(3|4|5|7|8)\d{9}$/; //手机号正则式
    //验证手机号正确性
    if (!phoneNumber.test($(".mobile").val().replace(/(^\s*)|(\s*$)/g, ""))) {
        $(".mobile").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
        return false;
    } else {
        $(".mobile").removeClass("light").parent().siblings(".tipsBox").fadeOut();
    }
    //校验验证码非空
    if ($(".kaptcha").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
        $(".kaptcha").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
        return false;
    } else {
        $(".kaptcha").removeClass("light").parent().siblings(".tipsBox").fadeOut();
    }
    //校验短信验证码
    if ($(".smsCodeinput").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
        $(".smsCodeinput").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
        return false;
    } else {
        $(".smsCodeinput").removeClass("light").parent().siblings(".tipsBox").fadeOut();
    }
	$(".firstConfirm").addClass("loadingBtn").html("").removeClass("continueBtn");
    var form = $("#checkCodesubmit");
    $.post(form.attr("action"), "mobile=" + new Base64().encode($(".mobile").val().replace(/(^\s*)|(\s*$)/g, "")) +
        "&code=" + new Base64().encode($(".smsCodeinput").val().replace(/(^\s*)|(\s*$)/g, "")) +
        "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
        function(data) {
            if (data.status == "SUCCESS") {
                $(".firstContent,.thirdContent").hide();
                $(".secContent").show();
                $(".secStep").addClass("light");
            } else {
                layer.msg(data.message);
            }
            $(".firstConfirm").removeClass("loadingBtn").html("验证").addClass("continueBtn");
        });
    return false;
});
$(".mobile").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		var phoneNumber = /^1(3|4|5|7|8)\d{9}$/; //手机号正则式
	    //验证手机号正确性
	    if (!phoneNumber.test($(".mobile").val().replace(/(^\s*)|(\s*$)/g, ""))) {
	        $(".mobile").addClass("light").parent().siblings(".tipsBox").fadeIn();
	    } else {
	        $(".mobile").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	    }
	}	
});
$(".kaptcha").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".kaptcha").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}	
});
$(".smsCodeinput").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".smsCodeinput").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}	
});
$(".newPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".newPassword").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}	
});
$(".confirmPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".confirmPassword").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}	
});
//校验非空
function checkNonempty(str){
	if(str.replace(/(^\s*)|(\s*$)/g,"")==""){
		return false;
	}else{
		return true;
	}
}
//校验注册密码
function checkRegistPassword(str){
	var isPassword = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{8,20}/i
	if(isPassword.test(str.replace(/(^\s*)|(\s*$)/g,""))){
		return true;
	}else{
		return false;
	}	
}
//自动跳转倒计时
function delayURL() {
    var delay = $("#content .content-inner .progressContent .thirdContent a span").html();
    var t = setTimeout("delayURL()", 1000);
    if (delay > 0) {
        delay--;
        $("#content .content-inner .progressContent .thirdContent a span").html(delay);
    } else {
        clearTimeout(t);
        window.location.href = "login.jsp";
    }
}
