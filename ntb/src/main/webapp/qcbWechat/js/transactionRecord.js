$(document).ready(function(){
	var type = url("?type") ? url("?type") : "";
	getList();
	function getList(){
		$.ajax({
			type:"get",
			url:api+ "/employee/getBill",
			async:true,
			data:{
				"token":token(),
				"type":type
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				if(r.totalResultCount > 0){
					var template = $.templates("#queboxTpl");
					var html = template.render(r.data);
					$(".main").html(html);
					if(/android/.test(ua)){
						$(".fuckWechat").css({
							"height":"0.2rem"
						});	
					}
					$(".openId-url").each(function(index,el){
						var _thisProp = $(".openId-url").eq(index).prop("href");
						$(".openId-url").eq(index).prop("href",_thisProp + "&openId="+openIdUrl);
					});
				}else{
					$(".main").html("<img class='no-data-img' src='./img/ic_empty.png'><p class='no-data-msg'>暂时没有任何数据</p>");
				}
				loadingOut();
			}else{
				if(r.status != "ERROR"){
					loadingOut();
					layerIn(r.message);
				}else{
					loadingOut();
					isWechat();
				}
			}
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	}
	
});
