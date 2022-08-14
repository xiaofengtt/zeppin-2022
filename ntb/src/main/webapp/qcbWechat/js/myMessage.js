$(document).ready(function(){
	var wechatCode = url("?code");
	var totalAmount = "";		//总金额
	var accountBalance = "";		//可提现余额
	var bankcardCount = null;

	get();
	getMessage();

	$(".main").show();
	$(".null").tap(function(){
		layerIn("敬请期待！");
	});
	
	//获取账户信息
	function get(){
		$.ajax({
			type:"get",
			url:api+"/employee/getAccountInfo",
			async:false,
			data:{
				"token":token()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				//总金额，可用余额，用户名,头像路径赋值。
				$("#totalAmount").html(r.data.totalAmount);
				totalAmount = r.data.totalAmount;
				accountBalance = r.data.accountBalance;
				$("#accountBalance").html(r.data.accountBalance);
				$(".name").html(r.data.name);
				//本地存储取参数，判断是否显示金额
				if(localStorage.getItem('totalAmount-flag') == "true"){
					var _this = $("#eye");
					_this.prop("src","./img/eye-o.png");
					$(".all-price-box").find("p").html(totalAmount);
					$("#accountBalance").html(r.data.accountBalance);
					_this.attr("data-flag","true");
					localStorage.removeItem('totalAmount-flag');
					localStorage.setItem('totalAmount-flag',"true");
				}else{
					var _this = $("#eye");
					_this.prop("src","./img/eye-c.png");
					$(".all-price-box").find("p").html("****");
					$("#accountBalance").html("****");
					_this.attr("data-flag","false");
					localStorage.removeItem('totalAmount-flag');
					localStorage.setItem('totalAmount-flag',"false");
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
		})
		.fail(function(){
			layerIn("服务器错误");
			loadingOut();
		});
	}
	
	
	//获取银行卡数量
	function getMessage(){
		$.ajax({
			type:"get",
			url:api+"/employee/get",
			async:false,
			data:{
				"token":token()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				bankcardCount = Number(r.data.bankcardCount);
				$(".head-photo").prop("src",r.data.wechatIcon);
			}else{
				layerIn(r.message);
			}
			loadingOut();
		})
		.fail(function(){
			layerIn("服务器错误");
			loadingOut();
		});
	}
	
	//点击眼睛，显示，隐藏金额
	$(".all-price-box").tap(function(){
		var _this = $("#eye");
		if(_this.attr("data-flag") == "true"){
			_this.prop("src","./img/eye-c.png");
			$(".all-price-box").find("p").html("****");
			$("#accountBalance").html("****");
			_this.attr("data-flag","false");
			localStorage.removeItem('totalAmount-flag');
			localStorage.setItem('totalAmount-flag',"false");
		}else{
			_this.prop("src","./img/eye-o.png");
			$(".all-price-box").find("p").html(totalAmount);
			$("#accountBalance").html(accountBalance);
			_this.attr("data-flag","true");
			localStorage.removeItem('totalAmount-flag');
			localStorage.setItem('totalAmount-flag',"true");
		}
	});
	
	$(".rechargeDiv").tap(function(){
		if(bankcardCount > 0){
			window.location.href = "./drawcash.html?openId="+openIdUrl;
		}else{
			tipIn();
		}
	});
	$("#rechargeBtn").tap(function(){
		if(bankcardCount > 0){
			window.location.href = "./recharge.html?openId="+openIdUrl;
		}else{
			tipIn();
		}
	});
	
	$("#goBind").tap(function(){
		localStorage.setItem("topPage","./myMessage.html");
		window.location.href = "./bankCardAdd.html?openId="+openIdUrl;
	});
	
	
	$(".openId-url").each(function(index,el){
		var _thisProp = $(".openId-url").eq(index).prop("href");
		$(".openId-url").eq(index).prop("href",_thisProp + "?openId="+openIdUrl);
	});
});
