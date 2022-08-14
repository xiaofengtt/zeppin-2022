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
			serviceOrder();
			$(".info-top p").html(r.data.publicName!=''?r.data.publicName:r.data.phone);//用户姓名
			$(".yhk b").html(r.data.bankcardCount+"张");//银行卡数
			//实名认证
			if(r.data.realnameAuthFlag){
				$(".smrz b").html("已认证");
			}else{
				$(".smrz b").html("未认证").addClass("color-red");
			}
			$(".smrz").parent().attr("href","realnameAuthentication.html?realnameAuthFlag="+r.data.realnameAuthFlag);
			$(".fwdd,.myOrder").parent().attr("href","orderList.html");
			$(".yhk").parent().attr("href","bankCardList.html");
			$(".layout").show();
			if(r.data.pwdFlag){
				$(".pwdManagement b").hide();
			}else{
				$(".pwdManagement b").show();
			}
			$(".pwdManagement").parent().attr("href","pwdManagement.html");
		}else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
//				location.replace("login.html");
				$(".info-top p").html("未登录").click(function(){
					location.replace("login.html");
				});//用户姓名
				$(".fwdd,.myOrder,.yhk,.smrz,.pwdManagement").click(function(){
					location.replace("login.html");
				});
				$(".layout").hide();
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
//服务订单数量
function serviceOrder(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/getShbxBill",
		async: true,
		data: {
			
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {
			//服务订单数量
			$(".fwdd b").html(r.totalResultCount);
		}else {
							
		}
	})
	.fail(function() {
		
		layerIn("服务器错误");
	});
}
//退出登录
$(".layout").click(function(){
	$("#confirm-box-logout").fadeIn();
});
//安全退出
$("#confirm-logout").click(function(){
	confirmOut();
	$.ajax({
		type:"get",
		url:"../rest/shbxWeb/login/logout",
		async:true,
		data:{
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){	
			if(judgePhone()=='isAndroid'||judgePhone()==''){
				window.JavascriptInterface.logout();
			}else if(judgePhone()=='isiOS'){
				window.webkit.messageHandlers.logout.postMessage('');	
			}		
			location.replace("./index.html");
			_czc.push(["_trackEvent", "退出登录", "成功"]);
		}else{
			layerIn(r.message);
		}
		loadingOut();
	})
	.fail(function(){
		loadingOut();
		layerIn("服务器错误");
	})
});


