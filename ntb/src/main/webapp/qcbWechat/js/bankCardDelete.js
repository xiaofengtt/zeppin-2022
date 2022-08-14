$(document).ready(function(){
	var uuid = url("?uuid");
	var j = url("?id");
	var base = new Base64();
	$(".bottom").tap(function(){
		codeBoxIn();
	});

	get();
	function get(){
		$.ajax({
			type:"get",
			url:api + "/bankcard/get",
			async:true,
			data:{
				"token":token(),
				"uuid":uuid
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				var html="";
					html+='<div class="top">'
						+'<div class="top-card card'+j+'">'
						+'<img src="..'+r.data.iconColor+'" class="small-logo" />'
						+'<h3 class="name">'+r.data.name+'</h3>'
						+'<span class="type">储蓄卡</span>'
						+'<p class="num">****&nbsp;&nbsp;****&nbsp;&nbsp;****&nbsp;&nbsp;'+r.data.bankcard+'</p>'
						+'</div>'
						+'</div>';
				$("#queboxCnt").html(html);
			}else{
				if(r.status != "ERROR"){
					layerIn(r.message);
				}else{
					isWechat(); 
				}
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	}
	
	//获取验证码
	$("#getCode").tap(function(){
		getCodeToCheck("emp_bankcard_delete");
	});
	
	$("#submit").tap(function(){
		if($("#mobileCode").val() == ""){
			layerIn("验证码输入有误");
			return false;
		}
		
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
			type:"post",
			url:api+"/bankcard/delete",
			async:true,
			data:{
				"token":token(),
				"mobileCode":base.encode($("#mobileCode").val()),
				"uuid":uuid
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				layerIn(r.message);
				setTimeout(function(){
					window.location.href = "./myMessage.html?openId=" + openIdUrl;
				},1500);
			}else{
				layerIn(r.message);
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		})
	});
	$(window).resize(function(){
		$(window).scrollTop(0); 
	});
});
