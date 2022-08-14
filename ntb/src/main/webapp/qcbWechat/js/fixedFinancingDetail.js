$(document).ready(function() {
	var uuid = url("?uuid");
	get();

	function get() {
		var status = $(".status-light").attr("data-status");
		$.ajax({
				type: "get",
				url: api + "/employee/getInfo",
				async: true,
				data: {
					"token": token(),
					"financial": uuid,
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
					
					$(".buy-price").tap(function() {
						if($(".coupon").css("display") == "none") {
							$(".coupon").show();
						} else {
							$(".coupon").hide();
						}
					});
					
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