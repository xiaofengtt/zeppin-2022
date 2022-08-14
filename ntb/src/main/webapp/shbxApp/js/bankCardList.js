var realnameAuthFlag = false;//实名flag
$(function(){
	getBankList();
	localStorage.setItem("topPage","./bankCardList.html");
	getUserInfo();
});
//获取银行卡列表
function getBankList(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/getBindingCard",
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
			var listHtml = "";
			for(var i=0;i<r.data.length;i++){
				listHtml += '<div class="bank-item mt-28" style="background:'+r.data[i].color
				+';" data="'+r.data[i].uuid+'"><div class="item-top"><div class="item-left"><img src="..'+r.data[i].icon+
				'" /></div><div class="item-right"><p>'+r.data[i].shortName+
				'</p><span>储蓄卡</span></div><div class="clear"></div></div><b>****&nbsp;&nbsp;****&nbsp;&nbsp;****&nbsp;&nbsp;'
				+r.data[i].bankcard+'</b><img src="img/bg-bankList.png" class="bankBg"></div>';
			}
			$(".bank-list").html(listHtml);
			$(".bank-item").click(function(){
				window.location.href = "bankCardDelete.html?uuid="+$(this).attr("data");
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
		loadingOut();
		layerIn("服务器错误");
	});	
}
//获取用户信息
function getUserInfo(){
	$.ajax({
		type: "get",
		url: "../rest/shbxWeb/user/get",
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
			realnameAuthFlag = r.data.realnameAuthFlag;
				
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
		
	});
}

$(".addBtn").click(function(){
	if(realnameAuthFlag){
		window.location.href = "bankCardAdd.html";
	}else{
		confirmIn("您尚未提交证件信息审核");		
	}
});
$("#confirm").click(function(){
	window.location.href = "realnameAuthentication.html?realnameAuthFlag=false";
	localStorage.setItem("addBanktopPage","./bankCardAdd.html");
});
