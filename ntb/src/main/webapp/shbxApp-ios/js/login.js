var codeFlag = false;
var base = new Base64();
var flagSubmit = true;
$(function(){
	
});

$('input').blur(function() {
	$(window).scrollTop(0);
});
$(".agreementCode").click(function(){
	if($("#agreeInput").hasClass("light")){
		$("#agreeInput").removeClass("light");
	}else{
		$("#agreeInput").addClass("light");
	}
	loginLight();
	return false;
});
$(".agreementPwd").click(function(){
	if($("#agreeInputPwd").hasClass("light")){
		$("#agreeInputPwd").removeClass("light");
	}else{
		$("#agreeInputPwd").addClass("light");
	}
	pwdloginLight();
	return false;
});
//监听手机号
$('#mobile').bind('input propertychange', function() {  
	if($(this).val()!=""){
		$(this).next("i").show();
	}else{
		$(this).next("i").hide();
	} 
	if(checkPhone($(this).val())){
		codeFlag = true;
		$(".sendCode").addClass("light");
	}else{
		codeFlag = false;
		$(".sendCode").removeClass("light");
	}
}); 
//监听手机号
$('#mobilePwd,#loginPwd').bind('input propertychange', function() {  
	if($(this).val()!=""){
		$(this).next("i").show();
	}else{
		$(this).next("i").hide();
	} 
});
$('.codeLogin input').bind('input propertychange', function() {  
	loginLight();
}); 
$('.pwdLogin input').bind('input propertychange', function() {  
	pwdloginLight();
}); 
//清除手机号
$(".cleatInputCode").click(function(){
	$("#mobile").val("");
	$(this).hide();
	codeFlag = false;
	$(".sendCode").removeClass("light");
	loginLight();
});
$(".cleatInputPwd").click(function(){
	$(this).parent().find("input").val("");
	$(this).hide();
	pwdloginLight();
});
//发送验证码
$(".sendCode").click(function(){
	if($(this).hasClass("light")){
		$(this).hide();
		$(".numCode").show();
		codeFlag=false;
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
	var mobile = $("input#mobile").val().replace(/(^\s*)|(\s*$)/g,"");
	var device = "06",
	Key = "27739700ee0bf2930cd62d72a80def0a",
	token = "",
	time = "",
	base = new Base64();
	var time = getTime();
	var code = 'register';
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
//登录按钮是否light
function loginLight(){
	var mobile = $("input#mobile").val().replace(/(^\s*)|(\s*$)/g,"");
	var mobileCode = $("input#mobileCode").val().replace(/(^\s*)|(\s*$)/g,"");
	var agree = $("#agreeInput").hasClass("light");
	if(mobile!=""&&mobileCode!=""&&agree){
		$("#loginBtn").addClass("light");
	}else{
		$("#loginBtn").removeClass("light");
	}
}
//验证码登录
$("#loginBtn").click(function(){
	var mobile = $("input#mobile").val().replace(/(^\s*)|(\s*$)/g,"");
	var mobileCode = $("input#mobileCode").val().replace(/(^\s*)|(\s*$)/g,"");
	var agree = $("#agreeInput").hasClass("light");
	if($(this).hasClass("light")){
		var device = "06",
		Key = "acd50ce3f96114043e138b04074eeb40",
		token = "",
		time = "",
		base = new Base64();
		var time = getTime();
		md5 = hex_md5(Key + time + mobile + mobileCode);
		token = base.encode(device + time + md5);
		$.ajax({
			type: "post",
			url: "../rest/shbxWeb/login/login",
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
			if(r.status == "SUCCESS") {
				loadingOut();
				//登录成功后保存openId，跳转页面  本地存储
//				localStorage.removeItem("openId");
//				localStorage.setItem("openId", r.data.openid);
//				
//				//登录成功后保存openId，跳转页面  cookie
//				delCookie("openId");
//				setCookie("openId",r.data.openid); 
				setTimeout("location.replace('./index.html')",500);
				if(judgePhone()=='isAndroid'||judgePhone()==''){
					window.JavascriptInterface.auth(mobile+r.data.uuid);
				}else if(judgePhone()=='isiOS'){
					window.webkit.messageHandlers.auth.postMessage(mobile+r.data.uuid);		
				}				
				_czc.push(["_trackEvent", "登录", "成功"]);
			} else {
				loadingOut();
				layerIn(r.message);
				$("#login").prop("disabled", false);
//				$(".sendCode").removeClass("light");
			}
		})
		.fail(function() {
			loadingOut();
			layerIn("服务器错误");
			$("#login").prop("disabled", false);
		});
		
	}else{
		if(mobile==''){
			layerIn("请填写手机号");
			return false;
		}else if(mobileCode==""){
			layerIn("请填写验证码");
			return false;
		}else if(agree==false){
			layerIn("请阅读并勾选用户注册协议");
			return false;
		}
		
	}
});
$(".agreementContent").click(function(){
	$(".agreementBox").show();
	return false;
});
$(".closeAgreement").click(function(){
	$(".agreementBox").hide();
});
//返回
$(".titleBack").click(function(){
	location.replace("./index.html");
});
function pwdloginLight(){
	var mobile = $("input#mobilePwd").val().replace(/(^\s*)|(\s*$)/g,"");
	var mobileCode = $("input#loginPwd").val().replace(/(^\s*)|(\s*$)/g,"");
	var agree = $("#agreeInputPwd").hasClass("light");
	if(mobile!=""&&mobileCode!=""&&agree&&checkPhone(mobile)){
		$("#pwdloginBtn").addClass("light");
	}else{
		$("#pwdloginBtn").removeClass("light");
	}
}
$(".skipCode").click(function(){
	$(".pwdLogin").show();
	$(".codeLogin").hide();
});
$(".skipPwd").click(function(){
	$(".pwdLogin").hide();
	$(".codeLogin").show();
});
//密码登录
$("#pwdloginBtn").click(function(){
	var mobile = $("input#mobilePwd").val().replace(/(^\s*)|(\s*$)/g,"");
	var mobileCode = $("input#loginPwd").val().replace(/(^\s*)|(\s*$)/g,"");
	var agree = $("#agreeInput").hasClass("light");
	if($(this).hasClass("light")){
		var device = "06",
		Key = "acd50ce3f96114043e138b04074eeb40",
		token = "",
		time = "",
		base = new Base64();
		var time = getTime();
		md5 = hex_md5(Key + time + mobile + hex_md5(mobileCode));
		token = base.encode(device + time + md5);
		$.ajax({
			type: "post",
			url: "../rest/shbxWeb/login/loginByPwd",
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
			if(r.status == "SUCCESS") {
				loadingOut();
				//登录成功后保存openId，跳转页面  本地存储
//				localStorage.removeItem("openId");
//				localStorage.setItem("openId", r.data.openid);
//				
//				//登录成功后保存openId，跳转页面  cookie
//				delCookie("openId");
//				setCookie("openId",r.data.openid);
				setTimeout("location.replace('./index.html')",500); 
				if(judgePhone()=='isAndroid'||judgePhone()==''){
					window.JavascriptInterface.auth(mobile+r.data.uuid);
				}else if(judgePhone()=='isiOS'){
					window.webkit.messageHandlers.auth.postMessage(mobile+r.data.uuid);
				}
				_czc.push(["_trackEvent", "登录", "成功"]);
			} else {
				loadingOut();
				layerIn(r.message);
				$("#login").prop("disabled", false);
//				$(".sendCode").removeClass("light");
			}
		})
		.fail(function() {
			loadingOut();
			layerIn("服务器错误");
			$("#login").prop("disabled", false);
		});
		
	}else{
		if(!checkPhone(mobile)){
			layerIn("请填写正确手机号");
			return false;
		}else if(mobileCode==""){
			layerIn("请填写密码");
			return false;
		}else if(agree==false){
			layerIn("请阅读并勾选用户注册协议");
			return false;
		}
		
	}
});
$(".fotgotPwd").click(function(){
	var mobile = $("input#mobilePwd").val().replace(/(^\s*)|(\s*$)/g,"");
	if(checkPhone(mobile)){
		window.location.href="forgotPwd.html?tel="+mobile;
	}else{
		window.location.href="forgotPwd.html";
	}
});