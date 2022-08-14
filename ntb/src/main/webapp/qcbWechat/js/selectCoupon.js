$(document).ready(function(){
	var price = url("?price"),
		uuid = url("?uuid"),
		base = new Base64();
		message = {
			"index":"",
			"couponType":"",
			"couponPrice":"",
			"couponUuid":""
		}
		
	getList();
	
	
	
	//不使用优惠券
	$("#no-coupon").tap(function(){
		$(".list-item").find(".list-item-top").removeClass("top-bg");
		$(".list-item").find(".list-item-bottom").removeClass("bottom-bg");
		$(".list-item").find(".check").hide();
		
		message.index = "";
		message.couponType = "none";
		message.couponPrice = "";
		message.couponUuid = "";
		localStorage.setItem("couponMessage", JSON.stringify(message));
		
		setTimeout(function(){
			window.location.href = "./productBuy.html?uuid=" + uuid + "&openId=" + openIdUrl;
		},500);
	});
	
	function getList() {
		$.ajax({
				type: "get",
				url: api + "/employee/couponList",
				async: true,
				data: {
					"token": token(),
					"pageNum":"1",
					"pageSize":1000,
					"status":"use",
					"price":base.encode(price)
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
						
						//优惠券默认选择
						if(localStorage.getItem("couponMessage")){
							var messageJson = JSON.parse(localStorage.getItem("couponMessage"));
							if(messageJson.index != ""){
								$(".list-item").eq(messageJson.index).find(".list-item-top").addClass("top-bg");
								$(".list-item").eq(messageJson.index).find(".list-item-bottom").addClass("bottom-bg");
								$(".list-item").eq(messageJson.index).find(".check").show();
							}
						}
						
						//选择优惠券效果
						$(".tap-bg").tap(function(){
							var _this = $(event.target);
							_this.parent().find(".list-item-top").addClass("top-bg");
							_this.parent().find(".list-item-bottom").addClass("bottom-bg");
							_this.parent().find(".check").show();
							
							_this.parent().siblings().find(".list-item-top").removeClass("top-bg");
							_this.parent().siblings().find(".list-item-bottom").removeClass("bottom-bg");
							_this.parent().siblings().find(".check").hide();
							
							//存值
							message.couponType = r.data[_this.attr("data-index")].couponType;
							message.couponPrice = r.data[_this.attr("data-index")].couponPriceCN;
							message.couponUuid = r.data[_this.attr("data-index")].uuid;
							message.index = _this.attr("data-index");
							localStorage.setItem("couponMessage", JSON.stringify(message));
							
							setTimeout(function(){
								window.location.href = "./productBuy.html?uuid=" + uuid + "&openId=" + openIdUrl;
							},500);
						});
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
});
