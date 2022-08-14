$(document).ready(function() {
	getList();	
	$(".status-tap").tap(function() {
		$(event.target).siblings(".status").addClass("status-light");
		$(event.target).parent().siblings().find(".status").removeClass("status-light");
		$(".item-box").hide();
		$("."+$(event.target).prev().attr("data-status")).show();
	});
});
//获取列表
function getList(){
	$.ajax({
		type:"get",
		url:api+ "/employee/getBill",
		async:true,
		data:{
			"token":token(),
			"type":'transfer'
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			var nodata = "<img class='no-data-img' src='./img/ic_empty.png'><p class='no-data-msg'>暂时没有任何数据</p>";
			var indata = "";//转入数据
			var outdata = "";//转出数据
			if(r.totalResultCount!=0){
				for(var i=0;i<r.data.length;i++){
					for(var j=0;j<r.data[i].dataList.length;j++){
						if(r.data[i].dataList[j].type=="cur_buy"){//转入
							indata +='<div class="item-list"><div class="item-list-mid"><p><small></small>活期盈产品</p><span class="color-gray">'+
							r.data[i].dataList[j].createtimeLessCN+'</span></div><div class="item-list-right"><p class="color-red"><small></small>'+
							r.data[i].dataList[j].price+'</p><span class="color-gray">转入成功</span></div></div>' 
						}else if(r.data[i].dataList[j].type=="cur_return"){//转出
							outdata +='<div class="item-list"><div class="item-list-mid"><p><small></small>活期盈产品</p><span class="color-gray">'+
							r.data[i].dataList[j].createtimeLessCN+'</span></div><div class="item-list-right"><p class="color-green"><small></small>'+
							r.data[i].dataList[j].price+'</p><span class="color-gray">转出成功</span></div></div>' 
						}
					} 
				}
				$(".item-box:eq(0)").html(indata==""?nodata:indata);
				$(".item-box:eq(1)").html(outdata==""?nodata:outdata).hide();
			}else{
				$(".item-box").html(nodata);
			}			
			
			loadingOut();
		}else{
			if(r.status != "ERROR"){
				layerIn(r.message);
				loadingOut();
			}else{
				loadingOut();
				isWechat();
			}
		}
	}).fail(function(){
		loadingOut();
		layerIn("服务器错误");
	});
}