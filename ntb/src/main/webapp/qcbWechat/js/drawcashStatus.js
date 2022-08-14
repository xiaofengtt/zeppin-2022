$(document).ready(function() {
	var uuid = url("?uuid");
	var type = url("?type");
	
	getStatus();
	function getStatus(){
		$.ajax({
			type:"get",
			url:api + "/wx/getBillInfo",
			async:true,
			data:{
				"uuid":uuid,
				"type":type
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				if(type == "qcbCompany"){
					var template = $.templates("#companyTpl");
				}else{
					var template = $.templates("#employeeTpl");
				}
				var html = template.render(r.data);
				$(".main").html(html);
			}else{
				layerIn(r.message);
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	}
});