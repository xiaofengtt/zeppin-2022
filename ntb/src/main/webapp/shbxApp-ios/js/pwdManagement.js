var isPassword = /((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*])|(?=.*\d)(?=.*[#@!~%^&*]))[a-z\d#@!~%^&*]{6,20}/i;
$(function(){
	getUserInfo();
});

//获取用户信息
function getUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/get",
		async: true,
		data: {
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			if(r.data.pwdFlag){
				$(".modifyPwd").show();
			}else{
				$(".setPwd").show();
				$(".dqzh").html(r.data.phoneFull);
			}			
		}else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);	
			}
						
		}
	})
	.fail(function() {
		loadingOut();
		layerIn("服务器错误");
	});
}
//设置新密码
$('.setPwd input').bind('input propertychange', function() {  
	loginLight();
}); 

function loginLight(){
	var szmm = $("input.szmm").val().replace(/(^\s*)|(\s*$)/g,"");
	var qrmm = $("input.qrmm").val().replace(/(^\s*)|(\s*$)/g,"");
	if(szmm!=""&&qrmm!=""){
		$(".setPwd .gobtn").addClass("light");
	}else{
		$(".setPwd .gobtn").removeClass("light");
	}
}
$(".setPwd .gobtn").click(function(){
	var szmm = $("input.szmm").val().replace(/(^\s*)|(\s*$)/g,"");
	var qrmm = $("input.qrmm").val().replace(/(^\s*)|(\s*$)/g,"");
	if($(this).hasClass("light")){
		if(szmm!=qrmm){
			layerIn("两次密码填写不一致");
			return false;
		}else if(!isPassword.test(szmm)){
			layerIn("密码格式为6-20位数字字母组合");
			return false;
		}else{
			setPwd(szmm,'');
		}		
	}else{
		if(szmm==''){
			layerIn("请填写登录密码");
			return false;
		}else if(qrmm==""){
			layerIn("请填写确认密码");
			return false;
		}		
	}
});

//修改密码
$('.modifyPwd input').bind('input propertychange', function() {  
	modifyloginLight();
}); 
$(".modifyPwd .gobtn").click(function(){
	var ymm = $("input.ymm").val().replace(/(^\s*)|(\s*$)/g,"");
	var xmm = $("input.xmm").val().replace(/(^\s*)|(\s*$)/g,"");
	var qrxmm = $("input.qrxmm").val().replace(/(^\s*)|(\s*$)/g,"");
	if($(this).hasClass("light")){
		if(xmm!=qrxmm){
			layerIn("两次密码填写不一致");
			return false;
		}else if(!isPassword.test(xmm)){
			layerIn("密码格式为6-20位数字字母组合");
			return false;
		}else{
			setPwd(xmm,ymm);
		}		
	}else{
		if(ymm==''){
			layerIn("请填写原登录密码");
			return false;
		}else if(xmm==""){
			layerIn("请填写新密码");
			return false;
		}else if(qrxmm==""){
			layerIn("请填写确认新密码");
			return false;
		}			
	}
});

function modifyloginLight(){
	var ymm = $("input.ymm").val().replace(/(^\s*)|(\s*$)/g,"");
	var xmm = $("input.xmm").val().replace(/(^\s*)|(\s*$)/g,"");
	var qrxmm = $("input.qrxmm").val().replace(/(^\s*)|(\s*$)/g,"");
	if(ymm!=""&&xmm!=""&&qrxmm!=""){
		$(".modifyPwd .gobtn").addClass("light");
	}else{
		$(".modifyPwd .gobtn").removeClass("light");
	}
}
//修改设置密码接口
function setPwd(xmm,ymm){
	var device = "06",
	Key = "acd50ce3f96114043e138b04074eeb40",
	token = "",
	time = "",
	base = new Base64();
	var time = getTime();
	if(ymm==""){
		md5 = hex_md5(Key + time + hex_md5(xmm) + ymm);
	}else{
		md5 = hex_md5(Key + time + hex_md5(xmm) + hex_md5(ymm));
	}
	token = base.encode(time + hex_md5(xmm) + md5);
	$.ajax({
		type: "post",
		url: "../rest/shbxWeb/user/resetpwd",
		async: true,
		data: {
			"encrypt": token
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			layerIn(r.message);
			setTimeout(function(){
				window.location.href = "myHome.html";
			},1500);
		} else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);	
			}
		}
	})
	.fail(function() {
		loadingOut();
		layerIn("服务器错误");
	});
}