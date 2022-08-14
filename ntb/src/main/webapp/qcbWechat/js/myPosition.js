$(document).ready(function() {
	appendOpenId();
	get();
	getCouponList();
	getNetWorth();
	function get() {
		$.ajax({
				type: "get",
				url: api + "/employee/getAccountInfo",
				async: true,
				data: {
					"token": token()
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					//数字类型的活期理财总额
					var currentAccountNum = Number(r.data.currentAccount.replace(/,/g,""));
					
					//数字类型的定期理财总额
					var totalInvestNum =  Number(r.data.totalInvest.replace(/,/g,""));
					
					//活期理财总额 ＋ 定期理财总额
					var totalAmountNum = currentAccountNum + totalInvestNum;
					$("#totalAmount").html(formatNum(totalAmountNum.toString()));
					
					//活期理财总额
					$("#currentAccount").html(r.data.currentAccount + "元");
					
					//定期理财总额
					$("#totalInvest").html(r.data.totalInvest + "元");
					
					//昨日收益
					$("#totalReturn").html(r.data.totalReturnBuyDay);
					
					//最新收益
					$("#totalReturnBuyDay").html(r.data.totalReturnBuyDay);
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
	
	function getCouponList(){
		$.ajax({
				type: "get",
				url: api + "/employee/couponList",
				async: true,
				data: {
					"token": token(),
					"pageSize":1000,
					"pageNum":"1",
					"status":"use"
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					var i = 0;
					$.each(r.data, function() {
						i++;
					});
					$("#couponCount").html(i);
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
});
//净值
function getNetWorth(){
	$.ajax({
			type: "get",
			url: api + "/product/getCurrent",
			async: true,
			data: {
				"token": token()
			},
			beforeSend: function() {
				loadingIn();
			}
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				//净值
				if(r.data.dailyList[0].netValue){
					$("#netWorth").html(r.data.dailyList[0].netValue);
				}else{
					$("#netWorth").html('0.00');
				}
				
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