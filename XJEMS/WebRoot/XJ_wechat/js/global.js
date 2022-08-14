var ua = navigator.userAgent.toLowerCase();//获取设备苹果或安卓
if(ua.match(/MicroMessenger/i) == "micromessenger"){
//	window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=http://" + Url + "/qcbWechat/index.html?&response_type=code&scope=snsapi_base&state=qcb#wechat_redirect";	
}else{
	window.location.href = "./blank.html";
}
//判断是否微信,是：重定向页面，不是，跳转空页面
