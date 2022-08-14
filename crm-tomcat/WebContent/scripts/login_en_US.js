function $(objId) {
	return document.getElementById(objId);
}
function $v(objId) {
	return document.getElementById(objId).value;
}
function doSubmit() {
	if($v("username") == '') {
		alert("Please write userName!");//请完整填写用户名
		$('username').focus();
		return false;
	}
	if($v("password") == '') {
		alert("Please write password!");//请完整填写密码
		$('password').focus();
		return false;
	}
	
	if(document.getElementById("checkimg") != null){
		String.prototype.trim=function(){ return this.replace(/(^\s*)|(\s*$)/g, '')}; 
		
		if($v("checkimg").trim() == '') {
			alert("Please write authcode!");//请完整填写验证码
			$('checkimg').focus();
			return false;
		}
	}
	
	if($("remMe").checked) {
		CookieManager.setCookie("remMeState", "checked");
		CookieManager.setCookie("username", $v("username"));
	} else {
		CookieManager.delCookie("remMeState");
		CookieManager.delCookie("username");
	}
	if($("remPwd").checked) {
		CookieManager.setCookie("remPwdState", "checked");
		CookieManager.setCookie("password", $v("password"));
	} else {
		CookieManager.delCookie("password");
		CookieManager.delCookie("remPwdState");
	}
	return true;
}
window.onload = function() {
	$('username').value = CookieManager.getCookie("username") == null?'':CookieManager.getCookie("username");
	$("password").value = CookieManager.getCookie("password") == null?'':CookieManager.getCookie("password");
	$("remMe").checked = CookieManager.getCookie("remMeState") == null?'':CookieManager.getCookie("remMeState");
	$("remPwd").checked = CookieManager.getCookie("remPwdState") == null?'':CookieManager.getCookie("remPwdState");
	$("username").focus();
}