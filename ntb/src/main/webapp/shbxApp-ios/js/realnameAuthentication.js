var realnameAuthFlag = url("?realnameAuthFlag") != null ? url("?realnameAuthFlag") : "";//是否已认证
var base = new Base64();

$(function(){
	if(realnameAuthFlag=="true"){
		getUserInfo();
	}
});

$('input').bind('input propertychange', function() {  
	saveLight();
}); 

//保存按钮
function saveLight(){
	var xm = $("input.xm").val().replace(/(^\s*)|(\s*$)/g,"");
	var sfzh = $("input.sfzh").val().replace(/(^\s*)|(\s*$)/g,"");
	if(isIDnumber.test(sfzh)&&xm!=""){
		$(".saveBtn").addClass("light");
	}else{
		$(".saveBtn").removeClass("light");
	}
}
//点击保存按钮
$(".saveBtn").click(function(){
	var xm = $("input.xm").val().replace(/(^\s*)|(\s*$)/g,"");
	var sfzh = $("input.sfzh").val().replace(/(^\s*)|(\s*$)/g,"");
	if($(this).hasClass("light")){
		$.ajax({
			type: "post",
			url: "../rest/shbxWeb/user/certification",
			async: true,
			data: {
				"name":base.encode(xm),
				"idcard":base.encode(sfzh)
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r) {
			loadingOut();
			$(".authentication").hide();
			if(r.status == "SUCCESS") {
				$(".realname").html(xm.substring(0,1)+"**");
				$(".idCard").html(sfzh.substring(0,4)+"**********"+sfzh.substring(sfzh.length-4,sfzh.length));
				$(".authSuccess").show();
			}else {
				if(r.status == "ERROR"&&r.errorCode=="302"){
					location.replace("login.html");
				}else{
					$(".authFail").show();		
				}							
			}
		})
		.fail(function() {
			loadingOut();
			layerIn("服务器错误");
		});
	}else{
		if(xm==""){
			layerIn("姓名不能为空");
		}else{
			layerIn("请填写正确的身份证号");
		}
	}
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
			$(".orderRealname").show();
			$(".authentication").hide();
			$(".realname").html(r.data.realname);
			$(".idCard").html(r.data.idcard);		
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
//重新认证
$(".reAuthBtn").click(function(){
	$(".authFail").hide();	
	$(".authentication").show();
	$("input.xm").val("");
	$("input.sfzh").val("");
	$(".saveBtn").removeClass("light");
});
//认证成功
$(".gobackBtn").click(function(){
	if(localStorage.getItem("addBanktopPage")){
		window.location.href=localStorage.getItem("addBanktopPage");
		localStorage.removeItem("addBanktopPage");
	}else{
		location.replace("myHome.html");
	}
	
});