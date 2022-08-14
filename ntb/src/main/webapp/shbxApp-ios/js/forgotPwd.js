var isPassword = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{6,20}/i;
var tel = url('?tel');
var flagSubmit = true;
$(function(){
	if(tel){
		$(".dqzh").val(tel);
		$(".sendCode").addClass("light");
		$(".clearInput").show();
	}
});
//监听手机号
$('.dqzh').bind('input propertychange', function() {  
	if($(this).val()!=""){
		$(this).next("i").show();
	}else{
		$(this).next("i").hide();
	} 
	if(checkPhone($(this).val())){
		$(".sendCode").addClass("light");
	}else{
		$(".sendCode").removeClass("light");
	}
});
//清除手机号
$(".clearInput").click(function(){
	$(".dqzh").val("");
	$(this).hide();
	$(".sendCode").removeClass("light");
	loginLight();
});
$('input').bind('input propertychange', function() {  
	loginLight();
}); 
//登录按钮是否light
function loginLight(){
	var mobile = $("input.dqzh").val().replace(/(^\s*)|(\s*$)/g,"");
	var mobileCode = $("input#mobileCode").val().replace(/(^\s*)|(\s*$)/g,"");
	var xmm = $(".xmm").val().replace(/(^\s*)|(\s*$)/g,"");
	var qrmm = $(".qrmm").val().replace(/(^\s*)|(\s*$)/g,"");
	if(mobile!=""&&mobileCode!=""&&xmm!=""&&qrmm!=""){
		$(".gobtn").addClass("light");
	}else{
		$(".gobtn").removeClass("light");
	}
}
//发送验证码
$(".sendCode").click(function(){
	if($(this).hasClass("light")){
		$(this).hide();
		$(".numCode").show();
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		getcode();
	}else{
		layerIn("请填写正确的手机号");
	}
});
//获取验证码
function getcode(){
	delayURL();
	var mobile = $("input.dqzh").val().replace(/(^\s*)|(\s*$)/g,"");
	var device = "06",
	Key = "27739700ee0bf2930cd62d72a80def0a",
	token = "",
	time = "",
	base = new Base64();
	var time = getTime();
	var code = 'resetpwd';
	md5 = hex_md5(Key + time + mobile + code);
	token = base.encode(device + time + md5);
	$.ajax({
		type: "get",
		url: '../rest/shbxWeb/sms/sendCode',
		async: true,
		data: {
			"mobile": base.encode(mobile),
			"codeType": base.encode(code),
			"token": token
		},
		beforeSend:function(){
//			loadingIn();
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {
			layerIn(r.message);
		} else {
			layerIn(r.message);
			$(".numCode").hide();
			$(".sendCode").show();
			$(".numCode b").html("60");
			flagSubmit = true;
			clearInterval(t);
//			$(".sendCode").removeClass("light");
		}
	})
	.fail(function() {
		layerIn("服务器错误");
		$(".numCode").hide();
		$(".sendCode").show();
		clearInterval(t);
	});
}
var t;
//倒计时
function delayURL() {
    var delay = $(".numCode b").html();
    t = setTimeout("delayURL()", 1000);
    if (delay > 0) {
        delay--;
        $(".numCode b").html(delay);
    } else {
        clearTimeout(t);
        codeFlag = true;
        $(".sendCode").show();
        $(".numCode").hide();
        $(".numCode b").html('60');
    }
}
$(".gobtn").click(function(){
	var mobile = $("input.dqzh").val().replace(/(^\s*)|(\s*$)/g,"");
	var mobileCode = $("input#mobileCode").val().replace(/(^\s*)|(\s*$)/g,"");
	var xmm = $(".xmm").val().replace(/(^\s*)|(\s*$)/g,"");
	var qrmm = $(".qrmm").val().replace(/(^\s*)|(\s*$)/g,"");
	if($(this).hasClass("light")){
		if(xmm!=qrmm){
			layerIn("两次密码填写不一致");
			return false;
		}else if(!isPassword.test(xmm)){
			layerIn("密码格式为6-20位数字字母组合");
			return false;
		}else{
			var device = "06",
			Key = "acd50ce3f96114043e138b04074eeb40",
			token = "",
			time = "",
			base = new Base64();
			var time = getTime();			
			md5 = hex_md5(Key + time + mobile + hex_md5(xmm) + mobileCode);			
			token = base.encode(time + hex_md5(xmm) + md5);
			$.ajax({
				type: "post",
				url: "../rest/shbxWeb/login/forgotpwd",
				async: true,
				data: {
					"mobile": base.encode(mobile),
					"token": token
				},
				beforeSend:function(){
					loadingIn();
				}
			})
			.done(function(r) {
				loadingOut();
				if(r.status == "SUCCESS") {
					layerIn("重置密码成功");
					setTimeout(function(){
						window.location.href = "login.html";
					},1500);
				} else {
					layerIn(r.message);
				}
			})
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
			});
		}		
	}else{
		if(mobile==''){
			layerIn("请填写手机号");
			return false;
		}else if(mobileCode==""){
			layerIn("请填写验证码");
			return false;
		}else if(xmm==''){
			layerIn("请填写新密码");
			return false;
		}else if(qrmm==""){
			layerIn("请填写确认密码");
			return false;
		}		
	}
});
