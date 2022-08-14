window.addEventListener('pageshow', function(event) {
		$.ajax({
			type:"get",
			url:api+"/employee/get",
			async:true,
			data:{
				"token":token()
			},
			beforeSend:function(){
				
			}
		})
		.done(function(r){
			if(r.status == "SUCCESS"){
				window.location.href = "./myMessage.html?openId=" + localStorage.getItem("openId");
			}else{
				return false;
			}
		})
		.fail(function(){
			return false;
		});
	
});

$(document).ready(function() {
	var str= navigator.userAgent.toLowerCase(); 
	var ver=str.match(/cpu iphone os (.*?) like mac os/);
	var regTel = /^1(1|2|3|4|5|6|7|8|9)\d{9}$/;
	var base = new Base64();
	var mobile = "";
	var mobileCode = "";
	var phoneFlag = false;
	var recordCodeFlag = true;
	$("#getCode,#regetCode").tap(function() {
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		if(phoneFlag){
			getcode("/sms/sendCode", "code");
		}else{
			layerIn("请输入正确的手机号码");
		}
		
	});
	
	
//	//登录
//	$("#login").tap(function() {
//		$("#login").prop("disabled", true);
//		$("#mobile").blur();
//		$("#mobileCode").blur();
//		if(regTel.test($("#mobile").val()) == false) {
//			layerIn("请输入正确的手机号码");
//			$("#login").prop("disabled", false);
//			return false;
//		}
//		if($("#mobileCode").val() == "") {
//			layerIn("验证码输入有误");
//			$("#login").prop("disabled", false);
//			return false;
//		}
//		
//		mobile = base.encode($("#mobile").val());
//		mobileCode = base.encode($("#mobileCode").val());
//		$.ajax({
//				type: "post",
//				url: api + "/employee/login",
//				async: true,
//				data: {
//					"mobile": mobile,
//					"mobileCode": mobileCode,
//					"code": url("?code")
//				},
//				beforeSend:function(){
//					loadingIn();
//				}
//			})
//			.done(function(r) {
//				if(r.status == "SUCCESS") {
//					loadingOut();
//					//登录成功后保存openId，跳转页面  本地存储
//					localStorage.removeItem("openId");
//					localStorage.setItem("openId", r.data.openid);
//					
//					//登录成功后保存openId，跳转页面  cookie
//					delCookie("openId");
//					setCookie("openId",r.data.openid);
//					
//					window.location.href = "./myMessage.html?openId=" + r.data.openid;
//				} else {
//					if(r.status != "ERROR"){
//						loadingOut();
//						layerIn(r.message);
//						$("#login").prop("disabled", false);
//					}else{
//						//返回信息为ERROR，返回登录页
//						isWechat();
//					}
//				}
//			})
//			.fail(function() {
//				loadingOut();
//				layerIn("服务器错误");
//				$("#login").prop("disabled", false);
//			});
//	});


	//登陆时发送验证码
	function getcode(src, code) {
		var i = 60,
			t,
			regTel = /^1(1|2|3|4|5|6|7|8|9)\d{9}$/,
			code = code,
			device = "05",
			Key = "acd50ce3f96114043e138b04074eeb40",
			token = "",
			time = "",
			base = new Base64();
		$("#count").html(i);
		t = setInterval(function() {
			i--;
			$("#count").html(i);
			if(i < 0) {
				clearInterval(t);
				$("#regetCode").css("display","block");
				$("#numCode").hide();
			}
		}, 1000);

		if(regTel.test($("#mobile").val()) == false) {
			layerIn("请输入正确的手机号码");
			$("#numCode").hide();
			$("#regetCode").css("display","block");
			clearInterval(t);
			return false;
		}
		var time = getTime();
		md5 = hex_md5(Key + time + $("#mobile").val() + code);
		token = base.encode(device + time + md5);
		$.ajax({
				type: "post",
				url: api + src,
				async: true,
				data: {
					"mobile": base.encode($("#mobile").val()),
					"codeType": base.encode(code),
					"token": token,
					"code": url("?code")
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					$(".phoneDiv").hide();
					if(ver&&ver[1].replace(/_/g,".").split('.')[0]<=11){
						$(".code-box").hide();
					}else{
						$(".code-box-input").hide();
					}
					$(".codeDiv").show();
					$("#numCode").show();
					$("#regetCode").hide();
					layerIn(r.message);
					$("#mobileCode").focus();
			        $(valCodeItems[0]).addClass('available');
			        $(window).scrollTop(0); 
				} else {
					layerIn(r.message);
					$("#numCode").hide();
					$("#regetCode").css("display","block");
					clearInterval(t);
				}
			})
			.fail(function() {
				layerIn("服务器错误");
				$("#numCode").hide();
				$("#regetCode").css("display","block");
				clearInterval(t);
			});
	}
	$('#mobile').bind('input propertychange', function() {  
		var value = $('#mobile').val().replace(/(^\s*)|(\s*$)/g, "");
		var isPhone = /^1(3|4|5|6|7|8|9)\d{9}$/;//手机号正则式	
		if(value!=""){
			$("#icon-delete").show();
			if(isPhone.test(value.replace(/(^\s*)|(\s*$)/g,""))){
				$(".big-btn").css("background","#FF7702");
				$(".sendPhone").html(value.replace(/(\d{3})(\d{4})/,"$1 $2 "));
				phoneFlag = true;
			}else{
				$(".big-btn").css("background","rgba(255,119,2,0.15)");
				$(".sendPhone").html('');
				phoneFlag = false;
			}
		}else{
			$("#icon-delete").hide();
			$(".big-btn").css("background","rgba(255,119,2,0.15)");
			$(".sendPhone").html('');
			phoneFlag = false;
		}
	}); 
	$("#mobile").focus(function(){
		$(window).scrollTop(0); 
	});
	$("#icon-delete").tap(function(){
		$('#mobile').val("");
		$(this).hide();
		$(".big-btn").css("background","rgba(255,119,2,0.15)");
		$(".sendPhone").html('');
		phoneFlag = false;
	});
	
	
	var valCodeInput = $("#mobileCode");
    var valCodeItems = $("div[name='val-item']");
    var regex = /^[\d]+$/;
    var valCodeLength = 0;
    $('.code-box').on('click',function(){
        valCodeInput.focus();
    })
//  input输入框即时反映
    valCodeInput.on('input propertychange change', function(e){
        valCodeLength = valCodeInput.val().length;
        for(var i=0;i<=valCodeLength;i++){
        	if(valCodeInput.val() && regex.test(valCodeInput.val())) {
                $(valCodeItems[i - 1]).removeClass('available');
                $(valCodeItems[i - 1]).addClass('available');
                $(valCodeItems[i - 1]).text(valCodeInput.val().substring(i - 1, i));
            }
        }
        
    })
//  点击获取验证码或点击第一个数字输入框时获取焦点,添加available类样式
    $("div[name='val-item']").on("tap",function(){
        $(valCodeInput).focus();
        $(valCodeItems[0]).addClass('available');
    })
//  删除键
    $(this).on('keyup', function(e){
        if(e.keyCode === 8) {
            $(valCodeItems[valCodeLength]).text("");
            if(valCodeLength !== 0){
                $(valCodeItems[valCodeLength]).removeClass('available');
            }
        }
    });
    $("#mobileCodeInput").on("input propertychange",function(){
    	if ($("#mobileCodeInput").val().length == 6&&recordCodeFlag) {
        	if(recordCodeFlag == false) {
    			return false;
    		}
        	recordCodeFlag = false;
    		setTimeout(function() {
    			recordCodeFlag = true;
    		}, 1000);
        	mobile = base.encode($("#mobile").val());
    		mobileCode = base.encode($("#mobileCodeInput").val());
    		$.ajax({
    				type: "post",
    				url: api + "/employee/login",
    				async: true,
    				data: {
    					"mobile": mobile,
    					"mobileCode": mobileCode,
    					"code": url("?code")
    				},
    				beforeSend:function(){
    					loadingIn();
    				}
    			})
    			.done(function(r) {
    				if(r.status == "SUCCESS") {
    					loadingOut();
    					//登录成功后保存openId，跳转页面  本地存储
    					localStorage.removeItem("openId");
    					localStorage.setItem("openId", r.data.openid);
    					
    					//登录成功后保存openId，跳转页面  cookie
    					delCookie("openId");
    					setCookie("openId",r.data.openid);
    					
    					window.location.href = "./myMessage.html?openId=" + r.data.openid;
    				} else {
    					if(r.status != "ERROR"){
    						loadingOut();
    						layerIn(r.message);
    						$("#login").prop("disabled", false);
    					}else{
    						//返回信息为ERROR，返回登录页
    						isWechat();
    					}
    				}
    			})
    			.fail(function() {
    				loadingOut();
    				layerIn("服务器错误");
    				$("#login").prop("disabled", false);
    			});
        }
    });
//  当验证码输入六位时直接跳转（在此验证验证码是否正确）
    $(valCodeInput).on("input propertychange",function(){
        if (valCodeInput.val().length == 6&&recordCodeFlag) {
        	if(recordCodeFlag == false) {
    			return false;
    		}
        	recordCodeFlag = false;
    		setTimeout(function() {
    			recordCodeFlag = true;
    		}, 1000);
        	mobile = base.encode($("#mobile").val());
    		mobileCode = base.encode($("#mobileCode").val());
    		$.ajax({
    				type: "post",
    				url: api + "/employee/login",
    				async: true,
    				data: {
    					"mobile": mobile,
    					"mobileCode": mobileCode,
    					"code": url("?code")
    				},
    				beforeSend:function(){
    					loadingIn();
    				}
    			})
    			.done(function(r) {
    				if(r.status == "SUCCESS") {
    					loadingOut();
    					//登录成功后保存openId，跳转页面  本地存储
    					localStorage.removeItem("openId");
    					localStorage.setItem("openId", r.data.openid);
    					
    					//登录成功后保存openId，跳转页面  cookie
    					delCookie("openId");
    					setCookie("openId",r.data.openid);
    					
    					window.location.href = "./myMessage.html?openId=" + r.data.openid;
    				} else {
    					if(r.status != "ERROR"){
    						loadingOut();
    						layerIn(r.message);
    						$("#login").prop("disabled", false);
    					}else{
    						//返回信息为ERROR，返回登录页
    						isWechat();
    					}
    				}
    			})
    			.fail(function() {
    				loadingOut();
    				layerIn("服务器错误");
    				$("#login").prop("disabled", false);
    			});
        }
    })

});
//把所有输入的不是数字的字符转换为空值
function checkForNum(obj) {
    obj.value = obj.value.replace(/[\D]/g, '');
}
