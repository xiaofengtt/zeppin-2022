$(document).ready(function() {
	var list = [];
	var price = url("?price");
	$(".bg").css({"height":$(window).height()+"px"});
	$("#price").html("<i>¥</i>"+price+"<small>元</small>");

	$(".bg").find("a").tap(function(){
		$(".bg").hide();
	});

	$.ajax({
		type:"get",
		url:"../rest/web/product/list",
		async:true
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			if(r.totalResultCount >= 2){
				list.push(r.data[0]);
				list.push(r.data[1]);
			}else{
				list.push(r.data[0]);
			}			
			var template = $.templates("#queboxTpl");
			var html = template.render(list);
			$("#queboxCnt").html(html);
		}else{
			alert(r.message);
			$(".bg").show().find("p").html(r.message);
		}
	})
	.fail(function(){
		$(".bg").show().find("p").html("服务器错误");
	});
});