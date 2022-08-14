$(document).ready(function(){
	var uuid = url("?uuid");
	get();
	function get(){
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
					var template = $.templates("#queboxTpl");
					var html = template.render(r.data);
					$(".main").html(html);
					appendOpenId();
					
					//最小投资金额格式化
					$("#minInvestAmount").html(formatNum(r.data.minInvestAmount.toString()) + "元");
					
					//最小投资递增格式化
					$("#minInvestAmountAdd").html(formatNum(r.data.minInvestAmountAdd.toString()) + "元");
					
					$(".bottom-btn").tap(function() {
						window.location.href = "./productBuy.html?openId=" + openIdUrl +"&uuid="+r.data.uuid;
					});
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
