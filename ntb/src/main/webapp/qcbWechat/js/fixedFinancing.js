$(document).ready(function() {

	$(".status-tap").tap(function() {
		$(event.target).siblings(".status").addClass("status-light");
		$(event.target).parent().siblings().find(".status").removeClass("status-light");
		getList();
	});
	get();
	getList();

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
					var currentAccountNum = Number(r.data.currentAccount.replace(/,/g, ""));
					console.log(currentAccountNum);

					//数字类型的定期理财总额
					var totalInvestNum = Number(r.data.totalInvest.replace(/,/g, ""));
					console.log(totalInvestNum);

					//活期理财总额 ＋ 定期理财总额
					var totalAmountNum = currentAccountNum + totalInvestNum;
					$("#totalAmount").html(formatNum(totalAmountNum.toString()));

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

	function getList() {
		var status = $(".status-light").attr("data-status");
		$.ajax({
				type: "get",
				url: api + "/employee/getList",
				async: true,
				data: {
					"token": token(),
					"pageNum": "1",
					"stage": status,
					"pageSize": 1000,
					
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					if(r.totalResultCount != 0) {
						if(status == "profit"){
							var template = $.templates("#profitTpl");
						}
						if(status == "transaction"){
							var template = $.templates("#transactionTpl");
						}
						if(status == "finished"){
							var template = $.templates("#finishedTpl");
						}
						var html = template.render(r.data);
						$(".main-bottom").html(html);
					} else {
						$(".main-bottom").html("<img class='no-data-img' src='./img/ic_empty.png'><p class='no-data-msg'>暂时没有任何数据</p>");
					}

					appendOpenId();
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