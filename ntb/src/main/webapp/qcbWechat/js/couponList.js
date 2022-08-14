$(document).ready(function() {
	
	getList();
	
	function getList() {
		var status = $(".status-light").attr("data-status");
		$.ajax({
				type: "get",
				url: api + "/employee/couponList",
				async: true,
				data: {
					"token": token(),
					"pageNum":"1",
					"pageSize":1000,
					"status":status
				},
				beforeSend: function() {
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					if(r.totalResultCount != 0){
						var template = $.templates("#queboxTpl");
						var html = template.render(r.data);
						$(".list").html(html);
					}else{
						$(".list").html("<img class='no-data-img' src='./img/empty_coupon.png'><p class='no-data-msg'>暂时没有任何数据</p>");
					}
					

					appendOpenId();
				} else {
//					if(r.status != "ERROR") {
//						layerIn(r.message);
//					} else {
//						isWechat();
//					}
				}
				loadingOut();
			})
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
			});
	}

	$(".status-tap").tap(function() {
		$(event.target).siblings(".status").addClass("status-light");
		$(event.target).parent().siblings().find(".status").removeClass("status-light");
		getList();
	});
});