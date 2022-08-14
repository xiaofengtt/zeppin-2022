$(document).ready(function() {
	var uuid = url("?uuid");
	 get();
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
					var template = $.templates("#queboxTpl");
					var html = template.render(r.data);
					$(".main").html(html);
					appendOpenId();
					
					//数字类型的剩余募集金额
					var totalRaiseNum = Number(r.data.totalRaise.replace(/,/g,""));
					//打印募集百分比进度
					console.log((r.data.collectAmount - totalRaiseNum) / r.data.collectAmount * 100 + "%");
					$(".progress-bar").css({
						"width":(r.data.collectAmount - totalRaiseNum) / r.data.collectAmount * 100 + "%"
					})
					
					//剩余募集金额
					$("#totalRaise").html(formatNum(r.data.totalRaise.replace(/,/g,"")));
					
					
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