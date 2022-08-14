$(document).ready(function() {
	var innerHeight = "",		//选择银行弹出框的高度
		mobileNumber = "",		//手机号
		priceNumber = "",		//Number类型的充值金额Value
		priceNumberStr = "",		//string类型的提现金额Value ＊100
		priceNumberFinal = "",		//被处理数字精度后的提现金额Value
		base = new Base64(),
		uuid = "",				//用户uuid
		bankListSelect = {		//底部银行列表弹出隐藏
		"in": function() {
			$("#select-bank").fadeIn();
			$(".inner").css({
				"-webkit-transition": "all 0.3s linear",
				"-webkit-transform": "translateY(" + innerHeight * -1 + "px)"
			});
		},
		"out": function() {
			$("#select-bank").fadeOut();
			$(".inner").css({
				"-webkit-transition": "all 0.3s linear",
				"-webkit-transform": "translateY(" + innerHeight * 1 + "px)"
			});
		}
	}
	getCardList();
	get();

	$(".inner").css({
		"top": windowHeight + "px"
	})
	$(".select").tap(function(event) {
		bankListSelect.in();
		event.stopPropagation();
	});
	$("#select-close").tap(function() {
		bankListSelect.out();
	});
	$(".inner").tap(function(event) {
		event.stopPropagation();
	});
	$(document).tap(function() {
		bankListSelect.out();
	}); 
	
	$("#bind-card-close").tap(function(){
//		$("#bind-card-box").fadeOut();
		window.location.href = "./myMessage.html?openId=" + openIdUrl;
	});
	
	//关闭验证码弹框后清空验证码输入框
	$("#code-box-close").tap(function(){
		$("#code-box").fadeOut(function(){
			$("#mobileCode").val("");
		});
	});
	
	$("#goBind").tap(function(){
		localStorage.setItem("topPage","./drawcash.html");
		window.location.href = "./bankCardAdd.html?openId=" + openIdUrl;
	});
	
	
	$("#price").on("input",function(){		//提现金额输入事件
		var this_value = $("#price").val();
		$("#drawcash").find("span").eq(1).html(formatNum(this_value));	//提现按钮显示输入金额
		$("#drawcash").find("span").eq(2).html("元");
		if($(this).val() == ""){		//如果输入框为空，清空提现按钮的金额和单位
			$("#drawcash").find("span").eq(1).html("");	
			$("#drawcash").find("span").eq(2).html("");	
		}
	});
	
	
	//请求账户信息
	function get() {
		$.ajax({
				type: "get",
				url: api + "/employee/get",
				async: true,
				data: {
					"token": token()
				},
				beforeSend:function(){
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					mobileNumber = r.data.mobile;
					uuid = r.data.uuid;
				}else{
					if(r.status != "ERROR"){
						layerIn(r.message);
					}else{
						isWechat();
					}
				}
				loadingOut();
			})
			.fail(function() {
				layerIn("服务器错误");
				loadingOut();
			});
	}
	
	
	//获取银行卡列表
	function getCardList(){
		$.ajax({
			type:"get",
			url:api + "/bankcard/list",
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
				var template = $.templates("#queboxTpl");
				var html = template.render(r.data);
				$(".list").html(html);
				$(".list").append("<li><img src='./img/icon_add_bank.png'/><span>添加银行卡</span><div class='more'></div><a class='link' href='./bankCardAdd.html?openId="+ openIdUrl +"'></a></li>");
				$(".link").tap(function(){
					localStorage.setItem("topPage","./drawcash.html");//记录绑卡成功后跳转的页面
				});
				
				//默认选择提现的银行
				$("#bankLogo").prop("src",".."+r.data[0].iconColor);		//银行logo
				$("#bankName").html(r.data[0].name);						//银行名称
				$("#bankNum").html("(" + r.data[0].bankcard + ")");		//银行卡号
				$("#bankuuid").val(r.data[0].uuid);						//银行uuid
				$(".check").eq(0).addClass("checked");				//银行列表默认选择第一个
				
				//为innerHeight赋值：模板渲染后的银行列表高度
				innerHeight = $(".inner").height();
				$("#select-bank").css({
					"height": windowHeight + "px",
					"display": "none"
				});
				$(".list .tap-bg").tap(function(){	//银行卡列表选择
					var _this = $(event.target);
					//点击选中
					_this.siblings(".check").addClass("checked").parents().siblings().find(".check").removeClass("checked");
					//改变当前logo
					$("#bankLogo").prop("src",".."+_this.siblings("img").attr("data-src"));
					//改变当前银行名称
					$("#bankName").html(_this.siblings(".bank-name").html());
					//改变当前银行卡号
					$("#bankNum").html(_this.siblings(".bank-num").html());
					//改变当前uuid
					$("#bankuuid").val(_this.siblings("input").val());
					bankListSelect.out();
				});
			}else{
				layerIn(r.message);
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	}
	
	$("#drawcash").tap(function(){
		priceNumber = Number($("#price").val());

		if($("#price").val() == ""){//输入框为空
			layerIn("请正确输入充值金额");
			return false;
		}
		if(!moneyReg.test($("#price").val()) || priceNumber <= 0){//输入金额是否合法或者小于等于0
			layerIn("请正确输入充值金额");
			return false;
		}
		if(priceNumber < 3){
			layerIn("充值金额不能少于3元");
			return false;
		}
	
		$("#price").blur();
		codeBoxIn();
	});
	
	//获取验证码
	$("#getCode").tap(function(){
		pay_first();
	});
	
	$("#submit").tap(function(){
		if($("#orderNum").val() == ""){
			layerIn("请先获取验证码");
			return false;
		}
		if($("#mobileCode").val() == ""){
			layerIn("验证码输入有误");
			return false;
		}
		if($("#this_bank_id").val() != $("#bankuuid").val()){
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
		
		priceNumberStr = floatTool.multiply(priceNumber,100).toString();
		if(priceNumberStr.indexOf(".") != -1){
			priceNumberFinal = priceNumberStr.substring(0,priceNumberStr.indexOf('.'));
		}else{
			priceNumberFinal = priceNumberStr;
		}
		
		$.ajax({
			type:"post",
			url:api+"/pay/rechargeByReapal",
			async:true,
			data:{
				"token":token(),
				"price":base.encode(priceNumberFinal),
				"code":base.encode($("#mobileCode").val()),
				"bankcard":$("#bankuuid").val(),
				"uuid":uuid,
				"type":"send"
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				//获取验证码成功后，保存orderNum
				$("#orderNum").val(r.data);
				$("#success-title").html("验证码已发送至" + mobileNumber);
				
				//保存发送验证码时的银行id
				$("#this_bank_id").val($("#bankuuid").val());
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
		
		
		
		$.ajax({
			type:"post",
			url:api+"/pay/rechargeByReapal",
			async:true,
			data:{
				"token":token(),
				"price":base.encode(priceNumberFinal),
				"code":base.encode($("#mobileCode").val()),
				"bankcard":$("#bankuuid").val(),
				"uuid":uuid,
				"type":"check",
				"orderNum":$("#orderNum").val()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				//充值成功跳转页面
				window.location.href = "./rechargeComplete.html?price="+priceNumber+"&uuid="+$("#bankuuid").val() + "&openId=" + openIdUrl;
			}else{
				layerIn(r.message);
				loadingOut();
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
});
$(window).resize(function(){
	$(window).scrollTop(0); 
});