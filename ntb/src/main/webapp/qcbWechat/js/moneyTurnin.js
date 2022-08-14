var bankcardCount = null,
	base = new Base64(),
	priceNumber = "", //数字类型的买入金额 表单验证用的
	priceNumberStr = "",		//string类型的提现金额Value ＊100
 	priceNumber = "", //数字类型的买入金额 表单验证用的
	accountBalance = "", //账户余额
	minInvestAmount = 1;//起购金额
$(document).ready(function(){
	get();
	getMessage();
	//开始计息时间
	$(".rateTime span").html((new Date().getMonth()+1) + "月" + new Date().getDate()+"日");
	$(".completeTop b").html("开始计息时间&nbsp;"+(new Date().getMonth()+1) + "月" + new Date().getDate()+"日");
});
$("#rechargeBtn").tap(function(){
	if(bankcardCount > 0){
		window.location.href = "./recharge.html?openId="+openIdUrl;
	}else{
		$("#tip-boxs").show();
	}
});
$("#goBinds").tap(function(){
	localStorage.setItem("topPage","./moneyTurnin.html");
	window.location.href = "./bankCardAdd.html?openId="+openIdUrl;
});
$("#goBind").tap(function(){
	localStorage.setItem("topPage","./moneyTurnin.html");
	window.location.href = "./recharge.html?openId="+openIdUrl;
});
//点击转入按钮
$("#drawcash").tap(function(){
	//校验输入内容是否正确
	priceNumber = Number($("#price").val());
	if(!integermoneyReg.test(priceNumber) || priceNumber <= 0) { //输入金额是否合法或者小于等于0
		layerIn("请正确输入转入金额");
		return false;
	}
	if(priceNumber == 0) { //买入金额为0 
		layerIn("转入金额不能为0");
		return false;
	}
	if($("#price").val() == "") { //输入框为空
		layerIn("请正确输入转入金额");
		return false;
	}

	if(accountBalance < priceNumber) {
		tipIn();
		return false;
	}

	if(priceNumber < minInvestAmount) {
		layerIn("转入金额不能低于最小起购金额");
		return false;
	}

	$("#price").blur();
	codeBoxIn();
});
//获取银行卡数量
function getMessage(){
	$.ajax({
		type:"get",
		url:api+"/employee/get",
		async:false,
		data:{
			"token":token()
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			bankcardCount = Number(r.data.bankcardCount);
			accountBalance = Number(r.data.accountBalance);
		}else{
			layerIn(r.message);
		}
	})
	.fail(function(){
		layerIn("服务器错误");
	});
}
//获取账户信息
function get(){
	$.ajax({
		type:"get",
		url:api+"/employee/getAccountInfo",
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
			$(".accountBalance span").html(r.data.accountBalance);			
			loadingOut();
		}else{
			if(r.status != "ERROR"){
				layerIn(r.message);
				loadingOut();
			}else{
				loadingOut();
				isWechat(); 
			}
		}
	})
	.fail(function(){
		layerIn("服务器错误");
		loadingOut();
	});
}
$("#getCode").tap(function() {
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	getCodeToCheck("fundcode");
});
//关闭验证码弹框后清空验证码输入框
$("#code-box-close").tap(function(){
	$("#code-box").fadeOut(function(){
		$("#mobileCode").val("");
	});
});
//确认购买
$("#submit").tap(function() {
	if($("#mobileCode").val() == "") {
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
	
	priceNumberStr = floatTool.multiply(priceNumber,100).toString();
	if(priceNumberStr.indexOf(".") != -1){
		priceNumberFinal = priceNumberStr.substring(0,priceNumberStr.indexOf('.'));
	}else{
		priceNumberFinal = priceNumberStr;
	}
	
	$.ajax({
			type: "post",
			url: api + "/pay/currentPay",
			async: true,
			data: {
				"token": token(),
				"price": base.encode(priceNumberFinal),
				"code": base.encode($("#mobileCode").val())
			},
			beforeSend: function() {
				loadingIn();
			}
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				loadingOut();
				codeBoxOut();
				window.location.href = "./moneyTurninComplete.html?openId=" + openIdUrl+"&money="+priceNumber;
				localStorage.setItem("turnOut","true");
			} else {
				layerIn(r.message);
				loadingOut();
			}
		})
		.fail(function() {
			loadingOut();
			layerIn("服务器错误");
		})
});
$("#completeBtn").tap(function(){
	window.location.href = "./freeFinancing.html?openId=" + openIdUrl;
});
$(window).resize(function(){
	$(window).scrollTop(0); 
});