$(document).ready(function() {
	var innerHeight = "",		//选择银行弹出框的高度
		accountBalance = "",		//账户可提现余额
		accountBalanceFormat = "",	//格式化后的可提现余额
		poundge = 1;				//手续费
		mobileNumber = "",		//手机号
		priceNumber = "",		//Number类型的提现余额Value，
		priceNumberStr = "",		//string类型的提现金额Value ＊100
		priceNumberFinal = "",		//被处理数字精度后的提现金额Value
		base = new Base64(),
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
	
	
	
	$("#goBind").tap(function(){
		localStorage.setItem("topPage","./drawcash.html");
		window.location.href = "./bankCardAdd.html?openId=" + openIdUrl;
	});
	
	
	$("#price").on("input",function(){		//提现金额输入事件
		var this_value = $("#price").val();	
		if (this_value.indexOf(".") == -1) { 
			if (this_value.length > 6) { 
				this_value = this_value.substr(0, 6);
			} 
			if(this_value=="00"){
				this_value = "0";
			}else if(this_value.length==2&&this_value.substr(0, 1)=="0"){
				this_value = this_value.substr(1, 2)
			}
		} else { 
			var arr = this_value.split(".");
			var length = arr[0].length;
//			if (length > 5) { 
				var totalInt = arr[0];
				var totalDecimal = 0;
				if (arr[1].length >= 0&&arr[1].length<=2) { 
					totalDecimal = arr[1];
				}else{
					totalDecimal = arr[1].substr(0, 2);
				} 
				var bigDecimal = parseInt(totalInt.substr(0, 6))+'.' + totalDecimal;
				this_value = bigDecimal;
//			} 
		}
		if(moneyReg.test(this_value)){		//如果输入金额合法，显示手续费金额，隐藏余额
			$(".poundage").css("display","block");
			$(".balance").hide();
			$("#drawcashDelete").show();
		}else{
			$(".poundage").hide();
			$(".balance").show();
//			$("#drawcashDelete").hide();
		}
		$("#price").val(this_value);
		$("#drawcash").find("span").eq(1).html(formatNum(this_value));	//提现按钮显示输入金额
		$("#drawcash").find("span").eq(2).html("元");
		if($(this).val() == ""){		//如果输入框为空，清空提现按钮的金额和单位
			$("#drawcash").find("span").eq(1).html("");	
			$("#drawcash").find("span").eq(2).html("");	
			$("#drawcashDelete").hide();
		}else{
			$("#drawcashDelete").show();
		}
		
		
	});
	//清除输入金额
	$("#drawcashDelete").tap(function(){
		$("#drawcash").find("span").eq(1).html("");	
		$("#drawcash").find("span").eq(2).html("");	
		$(".poundage").hide();
		$(".balance").show();
		$("#drawcashDelete").hide();
		$("#price").val('');
	});
	
	
	//请求账户信息
	function get() {
		$.ajax({
				type: "get",
				url: api + "/employee/get",
				async: true,
				data: {
					"token": token()
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					if(Number(r.data.accountBalance) > poundge){	//如果账户余额大于手续费
						accountBalance = floatTool.subtract(Number(r.data.accountBalance),poundge);//可提现余额 = 账户余额 - 手续费
						accountBalanceFormat = formatNum(accountBalance.toString());//可提现余额格式化
						$("#accountBalance").html(accountBalanceFormat);
					}else{
						accountBalance = 0;	//如果账户余额小于手续费，可提现余额为 0 
						accountBalanceFormat = "0.00"	//格式化后的余额也是0
						$("#accountBalance").html("0.00");
					}
					mobileNumber = r.data.mobile;
					
					$("#drawcashAll").tap(function(){	//全部提现按钮点击事件
						$("#price").val(accountBalance);//可提现余额赋值输入框
						$("#drawcash").find("span").eq(1).html(accountBalanceFormat);	//提现按钮显示金额
						$("#drawcash").find("span").eq(2).html(" 元");
						$(".poundage").css("display","block");
						$(".balance").hide();
						$("#drawcashDelete").show();
					});
				}else{
					if(r.status != "ERROR"){
						layerIn(r.message);
					}else{
						isWechat();
					}
				}
			})
			.fail(function() {
				layerIn("服务器错误");
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
		if(accountBalance == 0){	//可提现余额为0 
			layerIn("当前没有可提现金额");
			return false;
		}
		if($("#price").val() == ""){//输入框为空
			layerIn("请正确输入提现金额");
			return false;
		}
		if(!moneyReg.test($("#price").val()) || priceNumber <= 0){//输入金额是否合法或者小于等于0
			layerIn("请正确输入提现金额");
			return false;
		}
		if(priceNumber > accountBalance){//输入金额大于可提现余额
			layerIn("提现金额不能大于可提现余额");
			return false;
		}
		if(priceNumber > 50000){//输入金额大于5万
			layerIn("单笔提现最高5万元");
			return false;
		}
	
		$("#price").blur();
		codeBoxIn();
	});
	
	//获取验证码
	$("#getCode").tap(function(){
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		getCodeToCheck("emp_withdraw");
	});
	
	$("#submit").tap(function(){
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
		
		priceNumberStr = floatTool.multiply(priceNumber,100).toString();
		console.log(priceNumberStr);
		if(priceNumberStr.indexOf(".") != -1){
			priceumberFinal = priceNumberStr.substring(0,priceNumberStr.indexOf('.'));
		}else{
			priceNumberFinal = priceNumberStr;
		}

		$.ajax({
			type:"post",
			url:api+"/employeeTransfer/withdraw",
			async:true,
			data:{
				"token":token(),
				"price":base.encode(priceNumberFinal),
				"mobileCode":base.encode($("#mobileCode").val()),
				"bankcard":$("#bankuuid").val()
			},
			beforeSend:function(){
				$("input").blur();
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				window.location.href = "./drawcashComplete.html?price="+priceNumber+"&uuid="+$("#bankuuid").val() + "&openId=" + openIdUrl;
			}else{
				layerIn(r.message);
				loadingOut();
			}
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		})
	});
	
});