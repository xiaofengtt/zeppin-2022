var base = new Base64(),
	priceNumber = "", //数字类型的转出金额 表单验证用的
	priceNumberStr = "",		//string类型的提现金额Value ＊100
 	priceNumber = "", //数字类型的转出金额 表单验证用的
	accountBalance = ""; //账户余额
$(document).ready(function(){
	getMessage();

	$(".turnAll").tap(function(){
		$("#price").val(accountBalance);
	});
})
//获取余额
function getMessage(){
	$.ajax({
		type:"get",
		url:api+"/employee/getAccountInfo",
		async:false,
		data:{
			"token":token()
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			$("#price").attr("placeholder","本次最多可转出"+addCommas(r.data.currentAccount)+"元");
			accountBalance = Number(r.data.currentAccount.replace(/,/g,''));
		}else{
			layerIn(r.message);
		}
	})
	.fail(function(){
		layerIn("服务器错误");
	});
}
//点击转出按钮
$("#drawcash").tap(function(){
	//校验输入内容是否正确
	priceNumber = Number($("#price").val());
	if(!moneyReg.test($("#price").val()) || priceNumber <= 0) { //输入金额是否合法或者小于等于0
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
		layerIn("余额不足");
		return false;
	}

	$("#price").blur();
	codeBoxIn();
});
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
			url: api + "/pay/currentReturn",
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
				window.location.href = "./moneyTurnoutComplete.html?openId=" + openIdUrl+"&money="+priceNumber;
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