var smsCode = true; //是否发送验证码标识
$(function() {
	document.onkeydown = function(event){
		if(event.keyCode==13) {
			$(".completeBtn").click();
			return false;
		}
	}
    $(".code").click(function() {
        $(this).hide().attr('src', '../rest/backadmin/operator/kaptchaImage').fadeIn();
    });
    $(".piaochecked").on("click", function() {
        if (!$(this).hasClass("on_check")) {
            $(this).css("border", "1px solid #c6c6c6");
            $(".secConfirm").removeClass("light");
        } else {
            $(this).css("border", "none");
            $(".secConfirm").addClass("light");
        }
        $(this).toggleClass("on_check");
    });
    $(".smsCode").click(function() {
        sendCode();
    });
});
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
//继续填写企业信息
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
                getArea("province", "1", "", "");
            } else {
                layer.msg(data.message);
            }
        	$(".firstConfirm").removeClass("loadingBtn").html("继续填写企业信息").addClass("continueBtn");
        });
    return false;
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
            "&codeType=" + new Base64().encode("register") +
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

//完成注册
$(".completeBtn").click(function() {
    if ($(this).hasClass("light")) {
        //校验企业名称非空
        if ($(".companyName").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
            $(".companyName").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
            return false;
        } else {
            $(".companyName").removeClass("light").parent().siblings(".tipsBox").fadeOut();
        }
        //校验地区
        if ($(".province").val() == "0" || $(".city").val() == "0" || $(".county").val() == "0") {
            $(".province").parent().siblings(".tipsBox").fadeIn();
            if ($(".province").val() == "0") {
                $(".province").addClass("light");
                $(".city").removeClass("light");
                $(".county").removeClass("light");
            } else if ($(".city").val() == "0") {
                $(".city").addClass("light");
                $(".province").removeClass("light");
                $(".county").removeClass("light");
            } else {
                $(".county").addClass("light");
                $(".province").removeClass("light");
                $(".city").removeClass("light");
            }
            return false;
        } else {
            $(".province").removeClass("light").parent().siblings(".tipsBox").fadeOut();
            $(".city").removeClass("light");
            $(".county").removeClass("light");
        }
        //校验管理密码非空并且8-20位数字字母组合
//        var isPassword = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
        var isPassword = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{8,20}/i
        if ($(".companyPassword").val().replace(/(^\s*)|(\s*$)/g, "") == "" || !isPassword.test($(".companyPassword").val().replace(/(^\s*)|(\s*$)/g, ""))) {
            $(".companyPassword").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
            return false;
        } else {
            $(".companyPassword").removeClass("light").parent().siblings(".tipsBox").fadeOut();
        }
        //校验联系人姓名非空
        if ($(".contactName").val().replace(/(^\s*)|(\s*$)/g, "") == "") {
            $(".contactName").addClass("light").focus().parent().siblings(".tipsBox").fadeIn();
            return false;
        } else {
            $(".contactName").removeClass("light").parent().siblings(".tipsBox").fadeOut();
        }
        //密码强度验证
        var passwordLevel =3;
        var strongRegex = new RegExp("^(?=.{10,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
        var mediumRegex = new RegExp("^(?=.{9,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
        var enoughRegex = new RegExp("(?=.{8,}).*", "g"); 
        if (strongRegex.test($(".companyPassword").val())){
        	passwordLevel = 5;
        }else if(mediumRegex.test($(".companyPassword").val())){
        	passwordLevel = 3;
        }else if(enoughRegex.test($(".companyPassword").val())){
        	passwordLevel = 1;
        }
    	$(".secConfirm").addClass("loadingBtn").html("").removeClass("completeBtn");
        var form = $("#registersubmit");
        $.post(form.attr("action"), "mobile=" + new Base64().encode($(".mobile").val().replace(/(^\s*)|(\s*$)/g, "")) +
            "&company=" + encodeURI($(".companyName").val().replace(/(^\s*)|(\s*$)/g, "")) +
            "&area=" + $(".county").val() +
            "&password=" + new Base64().encode($(".companyPassword").val()) +
            "&contacts=" + encodeURI($(".contactName").val().replace(/(^\s*)|(\s*$)/g, "")) +
            "&passwordLevel="+passwordLevel+
            "&CSRFToken=" + $('input[name="CSRFToken"]').val(),
            function(data) {
                if (data.status == "SUCCESS") {
                    $(".firstContent,.secContent").hide();
                    $(".thirdContent").show();
                    $(".thirdStep").addClass("light");
                    setTimeout('delayURL()', 1000);
                } else {
                    layer.msg(data.message);
                }
            	$(".secConfirm").removeClass("loadingBtn").html("完成注册").addClass("completeBtn");
            });
        return false;
    }else{
    	return false;
    }
});

//获取地区
function getArea(className, level, pid, scode) {
    $.ajax({
        url: '../rest/qcb/area/list',
        type: 'get',
        data: {
            "level": level,
            "pid": pid,
            "scode": scode,
            "pageNum": 1,
            "pageSize": 1000,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
    	if(r.status == "SUCCESS"){
    		$("." + className).html("<option value='0'>请选择</option>");
            $.each(r.data, function(index, el) {
                if (el.level == 1 && el.name == "全国") {

                } else {
                    $("." + className).append("<option value=" + el.uuid + " scode = " + el.scode + ">" + el.name + "</option>");
                }
            });

            $(".province").change(function(event) {
                getArea("city", "2", $(this).val(), $(this).attr("scode"));
                $(".county").html("<option value='0'>请选择</option>");
            });
            $(".city").change(function(event) {
                getArea("county", "3", $(this).val(), $(this).attr("scode"));
            });
    	}else{
    		layer.msg(r.message);
    	}
        
    }).fail(function() {
        layer.msg("error", {
            time: 2000
        });
    });
}

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
$(".companyName").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".companyName").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}	
});
$(".province").blur(function(){
	if($(this).val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
        $(".province").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".city").blur(function(){
	if($(".province").val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
        $(".province").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".county").blur(function(){
	if($(".province").val()!="0"&&$(".city").val()!="0"&&$(".county").val()!="0"){
		$(".province").removeClass("light");
        $(".city").removeClass("light");
        $(".county").removeClass("light");
        $(".province").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}else if($(this).val()!="0"){
		$(this).removeClass("light");
	}
});
$(".companyPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		var isPassword = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{8,20}/i
	        if ($(".companyPassword").val().replace(/(^\s*)|(\s*$)/g, "") == "" || !isPassword.test($(".companyPassword").val().replace(/(^\s*)|(\s*$)/g, ""))) {
	            $(".companyPassword").addClass("light").parent().siblings(".tipsBox").fadeIn();
	            return false;
	        } else {
	            $(".companyPassword").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	        }
	}
});
$(".contactName").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		$(".contactName").removeClass("light").parent().siblings(".tipsBox").fadeOut();
	}	
});
