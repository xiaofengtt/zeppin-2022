$(document).ready(function(){
	getList();
	function getList(){
		$.ajax({
			type:"get",
			url:api + "/payroll/list",
			async:true,
			data:{
				"token":token()
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
					
					$(".openId-url").each(function(index,el){
						var _thisProp = $(".openId-url").eq(index).prop("href");
						$(".openId-url").eq(index).prop("href",_thisProp + "&openId="+openIdUrl);
						var month = "0"+$(this).find(".item-left span").html();
						$(this).find(".item-left span").html(month.substring(month.length-2,month.length));
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
