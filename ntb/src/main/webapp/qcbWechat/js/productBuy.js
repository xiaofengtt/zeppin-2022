window.addEventListener('pageshow', function(event) {
	if(event.persisted) { //判断是否是缓存中加载的页面
		window.location.reload();
	}
});
$(document).ready(function() {
	var uuid = url("?uuid"), //产品uuid
		base = new Base64(),
		couponUuid = localStorage.getItem("couponMessage") ? JSON.parse(localStorage.getItem("couponMessage")).couponUuid : "",
		priceNumber = "", //数字类型的买入金额 表单验证用的
		priceNumberStr = "",		//string类型的提现金额Value ＊100
		priceNumberFinal = "",		//被处理数字精度后的提现金额Value
		this_valueNum = "", //数字类型的买入金额 input事件用的
		targetAnnualizedReturnRateNum = "", //数字类型的预期年化利率
		totalReturn = "", //预期收益，
		userUuid = "", //用户id
		totalRaise = "", //产品剩余募集额度
		accountBalance = "", //账户余额
		minInvestAmount = "", //最小投资额
		minInvestAmountAdd = "", //最小递增，
		maxInvestAmount = "", //最大投资金额 ！！存在争议！！！
		t = "",
		message = { //购买的表单信息
			"uuid": "",
			"price": "",
			"return": "",
			"couponCount": ""
		};

	get();
	getAccount();

	function get() {
		$.ajax({
				type: "get",
				url: api + "/product/get",
				async: true,
				data: {
					"token": token(),
					"uuid": uuid
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					//最小投资额赋值
					minInvestAmount = r.data.minInvestAmount;

					//最小递增赋值
					minInvestAmountAdd = r.data.minInvestAmountAdd;

					//最大投资额赋值
					maxInvestAmount = r.data.maxInvestAmount;

					//数字类型产品剩余募集额度
					totalRaise = Number(r.data.totalRaise.replace(/,/g, ""));

					//logo
					$(".main-top-top-logo").find('img').prop("src", ".." + r.data.iconColorUrl)

					//产品名称
					$("#productName").html(" " + r.data.shortname);

					//产品银行和编号
					$("#productBankAndScode").html("【" + r.data.custodianCN + "】" + r.data.scode);

					//期限
					$("#term").html("期限/" + r.data.term + "天");

					//年化利率
					$("#targetAnnualizedReturnRate").html(r.data.targetAnnualizedReturnRate + "%");

					//期限预期收益
					$("#termReturn").html(r.data.term + "天预期收益");

					//买入金额 placeholder
					$("#price").prop("placeholder", r.data.minInvestAmount + "元起,递增" + r.data.minInvestAmountAdd + "元");

					if(localStorage.getItem("buyMessage")) {
						var messageJson = JSON.parse(localStorage.getItem("buyMessage"));

						//如果本地存储的产品id和url上的id不一样，就删除本地的优惠券信息和产品信息
						if(uuid != messageJson.uuid) {
							localStorage.removeItem("buyMessage");
							localStorage.removeItem("couponMessage");
						} else {
							//如果存储的id和url上的一样，那么执行以下操作
							$("#price").val(messageJson.price);

							//确定按钮显示金额
							if(messageJson.price != ""){
								$("#drawcash").find("span").eq(1).html(formatNum(messageJson.price)); //提现按钮显示输入金额
								$("#drawcash").find("span").eq(2).html("元");
							}else{
								$("#drawcash").find("span").eq(1).html(""); //提现按钮显示输入金额
								$("#drawcash").find("span").eq(2).html("");
							}
							//预期收益赋值
							$("#totalReturn").html(messageJson.return+"元");

							//判断本地存储是否有优惠券信息
							if(localStorage.getItem("couponMessage")) {
								var couponJson = JSON.parse(localStorage.getItem("couponMessage"));
								if(couponJson.couponType == "cash") {//如果是现金券，预期收益加上现金券的金额
									$("#couponCount").html("<i>" + couponJson.couponPrice + "元</i> 现金券");
									$("#totalReturn").html(Number(messageJson.return) + Number(couponJson.couponPrice) + "元");
								}

								if(couponJson.couponType == "interests") {//如果是加息券，预期收益率＋加息券利息 重新计算预期收益
									$("#couponCount").html("<i>" + couponJson.couponPrice + "%</i> 加息券");
									totalReturn = Number(messageJson.price) * (Number(r.data.targetAnnualizedReturnRate) + Number(couponJson.couponPrice)) * 0.01 / 365 * r.data.term
									$("#totalReturn").html(formatNum(totalReturn.toString()) + "元");
								}

								if(couponJson.couponType == "none") {
									$("#couponCount").html("未使用优惠券");
								}
								//如果本地存储有优惠券信息，为“我的优惠券”超链接赋值
								$(".product-msg").prop("href", "./selectCoupon.html?price=" + (Number(messageJson.price) * 100).toString() + "&uuid=" + uuid + "&openId=" + openIdUrl);
							} else {
								//没有优惠券信息，判断购买信息中的可选优惠券数量
								if(messageJson.couponCount != 0) {
									$("#couponCount").html("<i>" + messageJson.couponCount + "</i> 张可用");
									$(".product-msg").prop("href", "./selectCoupon.html?price=" + (Number(messageJson.price) * 100).toString() + "&uuid=" + uuid + "&openId=" + openIdUrl);
								} else {
									$("#couponCount").html("无可用");
								}
							}
						}
					}

					$("#price").on("input", function() { //提现金额输入事件
						var this_value = $("#price").val();
						if(this_value.length > 10){//输入金额大于10个字符，只保留10个字符
							this_value = this_value.substring(0,10);
						}
						if(moneyReg.test(this_value)) { //如果输入金额合法，显示手续费金额，隐藏余额
							//计算预计收益用变量
							this_valueNum = Number(this_value);
							targetAnnualizedReturnRateNum = Number(r.data.targetAnnualizedReturnRate);

							//计算预计收益
							totalReturn = this_valueNum * targetAnnualizedReturnRateNum * 0.01 / 365 * r.data.term;
							$("#totalReturn").html(formatNum(totalReturn.toString()) + "元");

							//请求可使用优惠券数量以及存储购买信息
							clearTimeout(t);
							t = setTimeout(function() {
								getCouponList((this_valueNum * 100).toString());
							}, 500);

						} else {
							$("#totalReturn").html("0元");
						}

						$("#drawcash").find("span").eq(1).html(formatNum(this_value)); //提现按钮显示输入金额
						$("#drawcash").find("span").eq(2).html("元");
						if($(this).val() == "") { //如果输入框为空，清空提现按钮的金额和单位
							$("#drawcash").find("span").eq(1).html("");
							$("#drawcash").find("span").eq(2).html("");
						}
					}); //提现金额输入事件结束
				} else {
					if(r.status != "ERROR") {
						layerIn(r.message);
					} else {
						isWechat();
					}
				}
				loadingOut();
			})
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
			});
	}

	function getCouponList(price) {
		$.ajax({
				type: "get",
				url: api + "/employee/couponList",
				async: false,
				cache: false,
				data: {
					"token": token(),
					"pageNum": "1",
					"pageSize": 1000,
					"status": "use",
					"price": base.encode(price)
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					var i = 0;
					if(r.totalResultCount > 0) {
						$.each(r.data, function() {
							i++;
						});
						$("#couponCount").html("<i>" + i + "</i> 张可用");
						$(".product-msg").prop("href", "./selectCoupon.html?price=" + price + "&uuid=" + uuid + "&openId=" + openIdUrl);
					} else {
						$("#couponCount").html("无可用");
						$(".product-msg").prop("href", "javascript:void(0);");
					}

					//金额变更后 删除上一次选中的优惠券信息
					localStorage.removeItem("couponMessage");

					//存储购买信息
					message.uuid = uuid;	//产品uuid
					message.price = $("#price").val();//购买金额
					message.couponCount = i;//优惠券数量
					message.return = formatNum(totalReturn.toString());//预期收益
					localStorage.setItem("buyMessage", JSON.stringify(message));//存储！！
				} else {
					if(r.status != "ERROR") {
						layerIn(r.message);
					} else {
						isWechat();
					}
				}
				loadingOut();
			})
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
			});
	}

	function getAccount() {
		$.ajax({
				type: "get",
				url: api + "/employee/get",
				async: true,
				cache: false,
				data: {
					"token": token()
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					accountBalance = Number(r.data.accountBalance);
					$("#accountBalance").find("i").html(formatNum(r.data.accountBalance) + "元");

					userUuid = r.data.uuid;
				} else {
					layerIn(r.message);
				}
				loadingOut();
			})
			.fail(function() {
				layerIn("服务器错误");
			});
	}

	//购买！
	$("#drawcash").tap(function() {
		priceNumber = Number($("#price").val());
		if(!moneyReg.test($("#price").val()) || priceNumber <= 0) { //输入金额是否合法或者小于等于0
			layerIn("请正确输入买入金额");
			return false;
		}
		if(priceNumber == 0) { //买入金额为0 
			layerIn("买入金额不能为0");
			return false;
		}
		if($("#price").val() == "") { //输入框为空
			layerIn("请正确输入买入金额");
			return false;
		}

		if(accountBalance < priceNumber) {
			tipIn();
			return false;
		}

		if(priceNumber < minInvestAmount) {
			layerIn("买入金额不能低于最小投资金额");
			return false;
		}

		if((priceNumber - minInvestAmount) % minInvestAmountAdd != 0) {
			layerIn("买入金额不符合投资递增金额要求");
			return false;
		}

		if(priceNumber > maxInvestAmount) {
			layerIn("买入金额不能大于最大投资金额" + maxInvestAmount + "元");
			return false;
		}

		if(totalRaise < priceNumber) {
			layerIn("买入金额不能超过剩余募集金额" + totalRaise + "元");
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
				url: api + "/pay/productPay",
				async: true,
				data: {
					"token": token(),
					"uuid": userUuid,
					"price": base.encode(priceNumberFinal),
					"code": base.encode($("#mobileCode").val()),
					"product": uuid,
					"type": "balance",
					"coupon": couponUuid
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					localStorage.removeItem("buyMessage");
					localStorage.removeItem("couponMessage");
					window.location.href = "./productPayComplete.html?openId=" + openIdUrl;
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

	$("#goBind").tap(function() {
		window.location.href = "./recharge.html?openId=" + openIdUrl;
	});
	$(window).resize(function(){
		$(window).scrollTop(0); 
	});
});