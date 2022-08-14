$(document).ready(function(){
	$.ajax({
		type:"get",
		url:api + "/bankcard/list",
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
			var html="";
			for(var i=0;i<r.data.length;i++){
				var j=i%6;
				html+='<a href="./bankCardDelete.html?uuid='+r.data[i].uuid+'&id='+j+'" class="card-list-box openId-url">'
				+'<div class="card-list-item card-list-item'+j+'">'
				+'<p class="name">'
				+'<img src="..'+r.data[i].iconColor+'" />'
				+'<span>'+r.data[i].name+'</span>'
				+'<b class="type">储蓄卡</b>'
				+'</p>'
				+'<h2 class="number">'
				+'****&nbsp;*****&nbsp;****&nbsp;'+r.data[i].bankcard+''
				+'</h2>'
				+'</div>'
				+'</a>';
			}
			$(".card-list").html(html);
			$(".card-list").append("<a href='./bankCardAdd.html?openId="+ openIdUrl +"' class='add'>＋ 添加银行卡</a>");
			
			$(".openId-url").each(function(index,el){
				var _thisProp = $(".openId-url").eq(index).prop("href");
				$(".openId-url").eq(index).prop("href",_thisProp + "&openId="+openIdUrl);
			});
			
			//点击保存绑卡入口页面路径，绑卡成功后跳转该路径
			$(".add").tap(function(){
				localStorage.setItem("topPage","./myMessage.html");
			});
			
			
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
});
