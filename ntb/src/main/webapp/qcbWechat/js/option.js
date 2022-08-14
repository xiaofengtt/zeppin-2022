$(document).ready(function(){
	$("#logout").tap(function(){
		confirmIn("确定要安全退出吗？");
	});
	get();

	//获取账户信息
	function get(){
		$.ajax({
			type:"get",
			url:api+"/employee/get",
			async:false,
			data:{
				"token":token()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				$("#mobile").html(r.data.mobile);
				$("#mobile").parent().prop("href","./bindPhone.html?mobile=" + r.data.mobile +"&openId=" + openIdUrl);
				$("#idConfirm").prop("href","./idConfirm.html?idcard="+r.data.idcard +"&openId=" + openIdUrl);
			}else{
				if(r.status != "ERROR"){
					layerIn(r.message);
				}else{
					isWechat();
				}
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	}
	
	$("#confirm").tap(function(){
		$.ajax({
			type:"get",
			url:api + "/employee/logout",
			async:true,
			data:{
				"token":token()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				localStorage.removeItem("openId");
				clearCookie("openId");
				window.location.replace("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=https://" + Url + "/qcbWechat/index.html?&response_type=code&scope=snsapi_base&state=qcb#wechat_redirect");
			}else{
				if(r.status != "ERROR"){
					layerIn(r.message);
				}else{
					isWechat();
				}
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		})
	});
});
