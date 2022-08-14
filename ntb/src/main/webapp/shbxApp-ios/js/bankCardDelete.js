var uuid = url("?uuid");
var base = new Base64();
var flagSubmit = true;
$(document).ready(function(){
	var base = new Base64();
	get();
	$(".bottom").click(function(){
		codeBoxIn();
	});
});
function get(){
	$.ajax({
		type:"get",
		url:"../rest/shbxWeb/user/bindingCardInfo",
		async:true,
		data:{
			"bankcard":uuid
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			$(".bank-item").css({"background":r.data.color});
			$(".item-right p").html(r.data.name);
			$(".bank-item b").html("****&nbsp;&nbsp;****&nbsp;&nbsp;****&nbsp;&nbsp;****&nbsp;&nbsp;"+r.data.bankcard);
			$(".item-top img").attr("img",".."+r.data.icon);
			$(".singleValue").html(separateComma(r.data.singleLimit));
			$(".dailyValue").html(separateComma(r.data.dailyLimit));
		}else{
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);	
			}				
		}
		loadingOut();
	})
	.fail(function(){
		loadingOut();
		layerIn("服务器错误");
	});
}
//获取验证码
$("#getCode").click(function(){
	$("#getCode").hide();
	$("#numCode").show();
	var base = new Base64();
		i = 60,
		mobileNumber = "";
	$("#count").html(i);
	t = setInterval(function() {
		i--;
		$("#count").html(i);
		if(i < 0) {
			clearInterval(t);
			$("#getCode").show().html("获取验证码");
			$("#numCode").hide();
		}
	}, 1000);
	
	$.ajax({
		type:"post",
		url:"../rest/shbxWeb/user/unbindSendCode",
		async:false,
		data:{
			"bankcard":uuid
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			layerIn(r.message);
		}else{
			layerIn(r.message);
			$("#numCode").hide();
			$("#getCode").show();
			clearInterval(t);
			return false;
		}
	})
	.fail(function(){
		layerIn("服务器错误");
		$("#numCode").hide();
		$("#getCode").show();
		clearInterval(t);
		return false;
	});
});

$("#submit").click(function(){
	if($("#mobileCode").val() == ""){
		layerIn("验证码输入有误");
		return false;
	}
	
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	$.ajax({
		type:"post",
		url:"../rest/shbxWeb/user/unbindBankcard",
		async:true,
		data:{
			"code":base.encode($("#mobileCode").val()),
			"bankcard":uuid
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			layerIn(r.message);
			setTimeout(function(){
				window.location.href = "myHome.html";
			},1500);
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