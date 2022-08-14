var priceNumber = "",		//Number类型的充值金额Value
	priceNumberStr = "",		//string类型的提现金额Value ＊100
	priceNumberFinal = "",		//被处理数字精度后的提现金额Value
	flagSubmit = true,
	base = new Base64();
var userid = url("?userid") != null ? url("?userid") : "";//用户id
$(function(){
	getBankList();
	$(".orderamount").html(localStorage.getItem("price")+"元");
	localStorage.setItem("userid",userid);
});
//获取银行卡列表
function getBankList(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/getBindingCard",
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
			if(r.data.length!=0){
				var listHtml = "";
				for(var i=0;i<r.data.length;i++){
					var classname = '';
					if(i==0){
						classname = 'light';
					}
					listHtml += '<li class="'+classname+'" data="'+r.data[i].uuid+
					'"><div class="item-left"><img src="..'+r.data[i].iconColor+
					'"></div><div class="item-right"><p>'+r.data[i].shortName
					+'(尾号'+r.data[i].bankcard+')</p><span>单笔限额5万，单日限额50万</span></div><div class="clear"></div></li>';
				}
				$(".payBottom ul").html(listHtml);
				$(".addBank").hide();
				$(".payBottom").show();
				$(".payBottom li").click(function(){
					$(this).addClass("light").siblings().removeClass("light");
				});
			}else{
				$(".addBank").show();
				$(".payBottom").hide();
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
//添加银行卡
$(".addBank").click(function(){
	localStorage.setItem("topPage","./paySuccess.html");
	window.location.href = "./bankCardAdd.html?userid=123";
});

//点击确认支付按钮
$(".sureBigBtn").click(function(){
	codeBoxIn();
});

//获取验证码
$("#getCode").click(function(){
	pay_first();
});

$("#submit").click(function(){
	if($("#orderNum").val() == ""){
		layerIn("请先获取验证码");
		return false;
	}
	if($("#mobileCode").val() == ""){
		layerIn("验证码输入有误");
		return false;
	}
	if($("#this_bank_id").val() != $(".payBottom li.light").attr("data")){
		layerIn("切换银行后请重新获取验证码");
		return false;
	}
	
	pay_next();

});

//充值获取验证码
function pay_first(){
	var i = 60,
	t;
	$("#getCode").hide();
	$("#numCode").show();
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
	priceNumber = Number(localStorage.getItem("price"));
	priceNumberStr = floatTool.multiply(priceNumber,100).toString();
	if(priceNumberStr.indexOf(".") != -1){
		priceNumberFinal = priceNumberStr.substring(0,priceNumberStr.indexOf('.'));
	}else{
		priceNumberFinal = priceNumberStr;
	}
	var housingFund,sourceCompany;
	if(localStorage.getItem("gjj")=="2"){
		housingFund = "";
	}else{
		housingFund = localStorage.getItem("housingFund");
	}
	if(localStorage.getItem("insuredType")=="new"){
		sourceCompany = "";
	}else{
		sourceCompany=localStorage.getItem("sourceCompany");
	}
	
	$.ajax({
		type:"post",
		url:"../rest/shbxWeb/pay/rechargeByReapal",
		async:true,
		data:{
			"price":base.encode(priceNumberFinal),
			"code":base.encode($("#mobileCode").val()),
			"bankcard":$(".payBottom li.light").attr("data"),
			"shbxInsured":localStorage.getItem("userid"),
			"type":"send",
			"orderNum":$("#orderNum").val(),
			"serviceFee":base.encode(localStorage.getItem("serviceFee")),
			"benefits":base.encode(localStorage.getItem("benefits")),
			"cardinalNumber":base.encode(localStorage.getItem("cardinalNumber")),
			"housingFund":base.encode(housingFund),
			"duration":localStorage.getItem("duration"),
			"sourceCompany":sourceCompany,
			"insuredType":localStorage.getItem("insuredType"),
			"starttime":localStorage.getItem("starttime")
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r){
		loadingOut();
		if(r.status == "SUCCESS"){
			layerIn("验证码发送成功");
			//获取验证码成功后，保存orderNum
			$("#orderNum").val(r.data);
//			$("#success-title").html("验证码已发送至" + mobileNumber + "的手机");
			
			//保存发送验证码时的银行id
			$("#this_bank_id").val($(".payBottom li.light").attr("data"));
		}else{
			$("#numCode").hide();
			$("#getCode").show();
			clearInterval(t);
			layerIn(r.message);
		}
		loadingOut();
	})
	.fail(function(){
		$("#numCode").hide();
		$("#getCode").show();
		clearInterval(t);
		loadingOut();
		layerIn("服务器错误");
	})
}

//充值确认
function pay_next(){
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	var housingFund,sourceCompany;
	if(localStorage.getItem("gjj")=="2"){
		housingFund = "";
	}else{
		housingFund = localStorage.getItem("housingFund");
	}
	if(localStorage.getItem("insuredType")=="new"){
		sourceCompany = "";
	}else{
		sourceCompany=localStorage.getItem("sourceCompany");
	}
	
	$.ajax({
		type:"post",
		url:"../rest/shbxWeb/pay/rechargeByReapal",
		async:true,
		data:{
			"price":base.encode(priceNumberFinal),
			"code":base.encode($("#mobileCode").val()),
			"bankcard":$(".payBottom li.light").attr("data"),
			"shbxInsured":localStorage.getItem("userid"),
			"type":"check",
			"orderNum":$("#orderNum").val(),
			"serviceFee":base.encode(localStorage.getItem("serviceFee")),
			"benefits":base.encode(localStorage.getItem("benefits")),
			"cardinalNumber":base.encode(localStorage.getItem("cardinalNumber")),
			"housingFund":base.encode(housingFund),
			"duration":localStorage.getItem("duration"),
			"sourceCompany":sourceCompany,
			"insuredType":localStorage.getItem("insuredType"),
			"starttime":localStorage.getItem("starttime")
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r){
		loadingOut();		
		if(r.status == "SUCCESS"){
			localStorage.removeItem("starttime");//缴费月份
			localStorage.removeItem("insuredType");//参保类型
			localStorage.removeItem("sourceCompany");//原公司名称
			localStorage.removeItem("duration");//参保时长
			localStorage.removeItem("housingFund");//公积金基数
			localStorage.removeItem("cardinalNumber");//社保基数	
			localStorage.removeItem("benefits");//代缴金额
			localStorage.removeItem("serviceFee");//服务费
			localStorage.removeItem("gjj");//是否缴纳公积金
			localStorage.removeItem("price");//合计金额
			//充值成功跳转页面
			$(".stepThree").hide();
			$(".authSuccess").show();
			$(".realname").html($("#orderNum").val());
			$(".idCard").html($(".payBottom li.light p").html());
			
//			$(".lookOrder").attr("href","orderDetails.html?billid="+$("#orderNum").val());
			codeBoxOut();
			_czc.push(["_trackEvent", "支付订单", "成功"]);
		}else{
			layerIn(r.message);
			$("#orderNum").val("");
			$("#this_bank_id").val("");
			setTimeout(function(){
				window.location.reload();
			},1500);
		}
	})
	.fail(function(){
		loadingOut();
		layerIn("服务器错误");
		$("#orderNum").val("");
		$("#this_bank_id").val("");
		setTimeout(function(){
			window.location.reload();
		},1500);
	})
}
$(".gobackBtn").click(function(){
	location.replace("index.html");
});