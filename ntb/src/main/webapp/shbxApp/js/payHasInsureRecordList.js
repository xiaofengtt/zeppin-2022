
//	$("ul li").each(function(index,value){
//		var remainder = index%3;
//		$(this).css({
//			"background-image":"../img/insure"+remainder+".png"
//		});
//	});

$(function(){
	getUserInfo();
});
//获取用户信息
function getUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/insured/list",
		async: true,
		data: {
			'pageSize':1000,
			'pageNum':1
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			var html = "";
			for(var i=0;i<r.data.length;i++){
				html += '<a href="payHasInsuredRecord.html?userid='+r.data[i].uuid+
				'"><li class="record'+i%3+'"><span>'+r.data[i].name+'</span><label>'+r.data[i].mobile+
				'</label><img src="img/icon_more.png" /><div class="clear"></div></li></a>';
			}
			$(".insureRecord ul").html(html);
		}else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);				
			}						
		}
	})
	.fail(function() {
		loadingOut();
		layerIn("服务器错误");
	});
}