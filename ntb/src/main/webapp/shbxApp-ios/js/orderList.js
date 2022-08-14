$(function(){
	$(".selectBar li").click(function(){
		document.body.scrollTop = document.documentElement.scrollTop = 0;
		$(this).addClass("light").siblings().removeClass("light");
		$(".order-list").hide();
		$("."+$(this).attr("data-url")).show();
	});
	
	getOrderList();
});
//订单列表
function getOrderList(){
	var allHtml = ""; //全部
	var unpayHtml = "";//待付款
	var normalHtml = "";//在缴
	var unprocessHtml = "";//待处理
	var noOrder = '<div class="noOrder"><p class="color-2E70">您还没有下过订单哦~</p><a class="gobtn paySocial" href="javascript:">去缴纳社保</a></div>';
	var noList ='<div class="noOrder"><p class="color-2E70">暂无相关数据</p></div>';
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/getShbxBill",
		async: true,
		data: {
			
		},
		beforeSend:function(){
			loadingIn();
		}
	})
	.done(function(r) {
		loadingOut();
		if(r.status == "SUCCESS") {
			//服务订单数量
			var number = 0;
			if(r.data.length!=0){				
				for(var i=0;i<r.data.length;i++){
					number +=r.data[i].dataList.length;
					for(var j=0;j<r.data[i].dataList.length;j++){
						if(r.data[i].dataList[j].status=="normal"){
							normalHtml+='<div class="order-item mt-28 doing" data-url="'+r.data[i].dataList[j].uuid
							+'"><p class="insuredName color-2E">'+r.data[i].dataList[j].shbxInsuredName
							+'('+r.data[i].dataList[j].durationCN
							+')</p><span class="insuredStatus">在缴</span><div class="clear"></div><p class="insuredPosition color-2E">'+
							r.data[i].dataList[j].shbxOrgCN+'</p><p class="insuredPay color-2E">费用:<span>'+r.data[i].dataList[j].totalAmountCN
							+'</span>元</p><div class="clear"></div></div>';
							allHtml +='<div class="order-item mt-28 doing" data-url="'+r.data[i].dataList[j].uuid
							+'"><p class="insuredName color-2E">'+r.data[i].dataList[j].shbxInsuredName
							+'('+r.data[i].dataList[j].durationCN+')</p><span class="insuredStatus">在缴</span><div class="clear"></div><p class="insuredPosition color-2E">'+
							r.data[i].dataList[j].shbxOrgCN+'</p><p class="insuredPay color-2E">费用:<span>'+r.data[i].dataList[j].totalAmountCN
							+'</span>元</p><div class="clear"></div></div>';
						}else if(r.data[i].dataList[j].status=="unprocess"||r.data[i].dataList[j].status=="payed"){
							unprocessHtml+='<div class="order-item mt-28 todo" data-url="'+r.data[i].dataList[j].uuid
							+'"><p class="insuredName color-2E">'+r.data[i].dataList[j].shbxInsuredName
							+'('+r.data[i].dataList[j].durationCN+
							')</p><span class="insuredStatus">'+r.data[i].dataList[j].statusCN+'</span><div class="clear"></div><p class="insuredPosition color-2E">'+
							r.data[i].dataList[j].shbxOrgCN+'</p><p class="insuredPay color-2E">费用:<span>'+r.data[i].dataList[j].totalAmountCN
							+'</span>元</p><div class="clear"></div></div>';
							allHtml +='<div class="order-item mt-28 todo" data-url="'+r.data[i].dataList[j].uuid
							+'"><p class="insuredName color-2E">'+r.data[i].dataList[j].shbxInsuredName
							+'('+r.data[i].dataList[j].durationCN+
							')</p><span class="insuredStatus">'+r.data[i].dataList[j].statusCN+'</span><div class="clear"></div><p class="insuredPosition color-2E">'+
							r.data[i].dataList[j].shbxOrgCN+'</p><p class="insuredPay color-2E">费用:<span>'+r.data[i].dataList[j].totalAmountCN
							+'</span>元</p><div class="clear"></div></div>';
						}else if(r.data[i].dataList[j].status=="unpay"||r.data[i].dataList[j].status=="fail"){
							unpayHtml+='<div class="order-item mt-28 closed" data-url="'+r.data[i].dataList[j].uuid
							+'"><p class="insuredName color-2E">'+r.data[i].dataList[j].shbxInsuredName
							+'('+r.data[i].dataList[j].durationCN+
							')</p><span class="insuredStatus">'+r.data[i].dataList[j].statusCN+'</span><div class="clear"></div><p class="insuredPosition color-2E">'+
							r.data[i].dataList[j].shbxOrgCN+'</p><p class="insuredPay color-2E">费用:<span>'+r.data[i].dataList[j].totalAmountCN
							+'</span>元</p><div class="clear"></div></div>';
							allHtml +='<div class="order-item mt-28 closed" data-url="'+r.data[i].dataList[j].uuid
							+'"><p class="insuredName color-2E">'+r.data[i].dataList[j].shbxInsuredName
							+'('+r.data[i].dataList[j].durationCN+
							')</p><span class="insuredStatus">'+r.data[i].dataList[j].statusCN+'</span><div class="clear"></div><p class="insuredPosition color-2E">'+
							r.data[i].dataList[j].shbxOrgCN+'</p><p class="insuredPay color-2E">费用:<span>'+r.data[i].dataList[j].totalAmountCN
							+'</span>元</p><div class="clear"></div></div>';
						}
						
					}
					
				}
				
			}
			if(r.totalResultCount==0){
				$(".order-list").html(noOrder);
			}else{
				if(unpayHtml==""){
					$(".doneList").html(noList);
				}else{
					$(".doneList").html(unpayHtml);
				}
				if(unprocessHtml==""){
					$(".pengdingList").html(noList);
				}else{
					$(".pengdingList").html(unprocessHtml);
				}
				if(normalHtml==""){
					$(".doingList").html(noList);
				}else{
					$(".doingList").html(normalHtml);
				}
				$(".allList").html(allHtml);
			}					
			$(".order-item").click(function(){
				window.location.href = "orderDetails.html?billid="+$(this).attr("data-url");
			});
			$(".paySocial").click(function(){
				getIndexUserInfo();
			});
		}else {
			if(r.status == "ERROR"&&r.errorCode=="302"){
				location.replace("login.html");
			}else{
				layerIn(r.message);	
			}				
		}
	})
	.fail(function() {
		
		layerIn("服务器错误");
	});
}