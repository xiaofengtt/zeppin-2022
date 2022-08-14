$(document).ready(function(){
	var uuid = url("?uuid");
	var content = "";
	$("#back-box").css({
		"height":windowHeight
	});
	getFeedBackList();
	get();
	function get(){
		$.ajax({
			type:"get",
			url:api + "/payroll/get",
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
				var template = $.templates("#queboxTpl");
				var html = template.render(r.data);
				$(".msg").html(html);
				$(".name").html(r.data.title);
				$(".price").html(r.data.priceCN);
				$(".qcbCompanyName").html(r.data.qcbCompanyName);
				$.each(r.data.valueList, function(index,el) {
					if(el != ""){
						$(".value").eq(index).html(el);
					}else{
						$(".value").eq(index).html("无");
					}
				});
				if(r.data.status == "success"){
					$("#allRight").show();
					$("#checked").hide();
				}else{
					$("#allRight").hide();
					$("#checked").show();
				}
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
	
	//请求历史反馈信息
	function getFeedBackList(){
		$.ajax({
			type:"post",
			url:api + "/payroll/feedbackList",
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
				if(r.data.length == 0){
					$("#back").show();
					$("#backed").hide();
				}else{
					$("#back").hide();
					$("#backed").show();
				}
				
				if(r.totalResultCount != 0){
					//反馈信息赋值
					content = r.data[0].content;
				}
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
	
	$("#allRight").tap(function(){
		confirmIn("确认无误？");
	});
	
	$("#confirm").tap(function(){
		$.ajax({
			type:"get",
			url:api + "/payroll/confirm",
			async:true,
			data:{
				"uuid":uuid,
				"token":token()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				get();
				confirmOut();
			}else{
				layerIn(r.message);
				loadingOut();
			}
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	});
	
	//反馈信息淡入淡出
	$("#back").tap(function(){
		$("#back-box").fadeIn();
	});
	$("#back-box-close").tap(function(){
//		$(".back-box-inner-mid textarea").blur();
		$("#back-box").fadeOut();
		$(window).scrollTop(0); 
	});
	
	//查看反馈信息
	$("#backed").tap(function(){
		modalIn(content);	
	});
	
	
	//提交反馈信息
	$("#submit").tap(function(){
		if($("#content").val() == ""){
			layerIn("请填写反馈信息");
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
			url:api + "/payroll/feedback",
			async:true,
			data:{
				"token":token(),
				"uuid":uuid,
				"content":$("#content").val()
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r){
			$(".back-box-inner-mid textarea").blur();
			if(r.status == "SUCCESS"){
				$("#back-box").fadeOut();
				getFeedBackList();
				layerIn(r.message);
			}else{
				layerIn(r.message);
				loadingOut();
			}
			loadingOut();
		})
		.fail(function(){
			loadingOut();
			layerIn("服务器错误");
		});
	});
	$(window).resize(function(){
		$(window).scrollTop(0); 
	});
	$('textarea').on('blur',function(){
		window.scroll(0,0);
	});
});
