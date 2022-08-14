$(document).ready(function() {
	var bankData = {
		"icon":"",
		"name":"",
		"uuid":""
	};
	
	$.ajax({
			type: "get",
			url: api + "/bank/list",
			async: true,
			data: {
				"pageNum": "1",
				"pageSize": 1000
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				var template = $.templates("#queboxTpl");
				var html = template.render(r.data);
				$(".list").html(html);
				//从本地存储拿到上一次选择的index。
				if(localStorage.getItem("selected")){
					$(".list li").eq(localStorage.getItem("selected")).addClass("light").siblings().removeClass("light");
				}
				$(".tap-bg").tap(function(event) {
					$(event.target).parent().addClass("light").siblings().removeClass("light");
					bankData.icon = $(event.target).siblings("img").attr("data-url");
					bankData.name = $(event.target).siblings("span").html();
					bankData.uuid = $(event.target).siblings("input").val();
					
					//将选择的银行的数据存储。
					localStorage.setItem("bankData",JSON.stringify(bankData));
					
//					点击列表项,保存index，下次进入页面时获取index
					localStorage.setItem("selected",$(event.target).parent().attr("data-number"));
					setTimeout(function(){
						window.location.href = "./bankCardAdd.html?openId=" + openIdUrl;
					},500);
				});
				loadingOut();
			}else{
				if(r.status != "ERROR"){
					layerIn(r.message);
					loadingOut();
				}else{
					isWechat();
				}
			}
		})
		.fail(function() {
			layerIn("服务器错误");
			loadingOut();
		});
});