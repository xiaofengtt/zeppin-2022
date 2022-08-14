var smsCode = true;//是否发送验证码标识
$(function(){
	$(".loadingOver").show();
	$(".loadingDiv").hide();
	$(".main-left-item:eq(4)").children("a").addClass("color-orange").children("img").attr("src","./img/r-5.png");
	$(".main-left-item:eq(4) ul li:eq(0) a").addClass("color-orange");
	$(".iframe").css({"height":$(window).height()});
	$(".closeIframe").click(function(){
		$(".iframe").hide();
		$(".iframe input").val("");
		$(".modifyPasswordIframe .sureBtn").addClass("modifyPasswordBtn");
		$(".bindPhoneIframe .sureBtn").addClass("bindPhoneBtn");
	});
	$(".modifyPassword").click(function(){
		if($("input[name='CSRFToken']").val().replace(/(^\s*)|(\s*$)/g, "")!=""){
			$(".modifyPasswordIframe").show();
		}else{
			layer.msg("数据异常，即将刷新页面", {
	            time: 2000
	        },function(){
	        	window.location.href = window.location.href;
	        });
		}
	});
	$(".bindPhone").click(function(){
		if($("input[name='CSRFToken']").val().replace(/(^\s*)|(\s*$)/g, "")!=""){
			$(".bindPhoneIframe").show();
		}else{
			layer.msg("数据异常，即将刷新页面", {
	            time: 2000
	        },function(){
	        	window.location.href = window.location.href;
	        });
		}
		
	});
	$(".tipsIcon").hover(function(){
		$(".tipsDiv").fadeIn();
	},function(){
		$(".tipsDiv").hide();
	});

	$(".modifyPasswordIframe .modifyPasswordBtn").click(function(){
		if(checkNonempty($(".oldPassword").val())){
			$(".oldPassword").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".oldPassword").addClass("light").focus().siblings(".tip").fadeIn();
			return false;
		}
		if(checkNonempty($(".newPassword").val())&&checkRegistPassword($(".newPassword").val())){
			$(".newPassword").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".newPassword").addClass("light").focus().siblings(".tip").fadeIn();
			return false;
		}
		if($(".newPassword").val().replace(/(^\s*)|(\s*$)/g, "")==$(".confirmPassword").val().replace(/(^\s*)|(\s*$)/g, "")){
			$(".confirmPassword").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".confirmPassword").addClass("light").focus().siblings(".tip").fadeIn();
			return false;
		}
		 //密码强度验证
        var passwordLevel =3;
        var strongRegex = new RegExp("^(?=.{10,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); 
        var mediumRegex = new RegExp("^(?=.{9,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g"); 
        var enoughRegex = new RegExp("(?=.{8,}).*", "g"); 
        if (strongRegex.test($(".newPassword").val())){
        	passwordLevel = 5;
        }else if(mediumRegex.test($(".newPassword").val())){
        	passwordLevel = 3;
        }else if(enoughRegex.test($(".newPassword").val())){
        	passwordLevel = 1;
        }
        $(".modifyPasswordIframe .sureBtn").html("").addClass("loadingBtn").removeClass("modifyPasswordBtn");
		$.ajax({
	        url: '../rest/qcb/admin/password',
	        type: 'post',
	        async:false,
	        data: {
	            "password":$("input[name='password']").val().replace(/(^\s*)|(\s*$)/g, ""),
	            "passwordNew":$("input[name='passwordNew']").val().replace(/(^\s*)|(\s*$)/g, ""),
	            "passwordNewCheck": $("input[name='passwordNewCheck']").val().replace(/(^\s*)|(\s*$)/g, ""),
	            "passwordLevel":passwordLevel,
	            "CSRFToken":$('input[name="CSRFToken"]').val()
	        }
	    }).done(function(data) {
	    	if (data.status == "SUCCESS") {
				layer.msg(data.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.jsp";
		        });
			} else {
				$(".modifyPasswordIframe .sureBtn").addClass("modifyPasswordBtn").removeClass("loadingBtn").html("保存");
				layer.msg(data.message);
			}
	    }).fail(function() {
	        layer.msg("error", {
	            time: 2000
	        },function(){
	        	window.location.href=window.location.href;
	        });
	    });		
		return false;
	});
	$(".bindPhoneIframe .bindPhoneBtn").click(function(){
		if(checkNonempty($(".smsCode").val())){
			$(".smsCode").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".smsCode").addClass("light").focus().siblings(".tip").fadeIn();
			return false;
		}
		if(checkNonempty($(".newPhone").val())&&checkPhone($(".newPhone").val())){
			$(".newPhone").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".newPhone").addClass("light").focus().siblings(".tip").fadeIn();
			return false;
		}
		$(".bindPhoneIframe .bindPhoneBtn").html("").addClass("loadingBtn").removeClass("bindPhoneBtn");
		$.ajax({
	        url: '../rest/qcb/admin/changeMobile',
	        type: 'post',
	        async:false,
	        data: {
	            "code":$("input[name='code']").val().replace(/(^\s*)|(\s*$)/g, ""),
	            "mobile":$("input[name='mobile']").val().replace(/(^\s*)|(\s*$)/g, ""),
	            "CSRFToken":$('input[name="CSRFToken"]').val()
	        }
	    }).done(function(data) {
	    	if (data.status == "SUCCESS") {
				layer.msg(data.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.jsp";
		        });
			} else {
				$(".bindPhoneIframe .sureBtn").addClass("bindPhoneBtn").removeClass("loadingBtn").html("修改");
				layer.msg(data.message);
			}
	    }).fail(function() {
	        layer.msg("error", {
	            time: 2000
	        },function(){
	        	window.location.href=window.location.href;
	        });
	    });				
		return false;
	});
	$(".bindPhoneIframe .getCode").click(function(){
//		if(checkNonempty($(".oldphone").val())){
//			$(".oldphone").removeClass("light").siblings(".tip").fadeOut();			
//		}else{
//			$(".oldphone").addClass("light").focus().siblings(".tip").fadeIn();
//			return false;
//		}
		if(smsCode){
			smsCode = false;
			var form = $("#sendCodeToCheck");
			$.post(form.attr("action"),"type=qcb_mobile_modify&CSRFToken="+$('input[name="CSRFToken"]').val(), function(data) {
				if (data.status == "SUCCESS") {
					layer.msg(data.message);
					$(".getCode").html("<span>60</span>s后，重新获取");
					invokeSettime();
				} else {
					layer.msg(data.message);
					smsCode = true;
				}
			});		
			return false;
		}
	});
	
});
$(window).resize(function(){
	$(".iframe").css({"height":$(window).height()});
});

//短信验证码倒计时
function invokeSettime() {	
	var delay = Number($(".getCode span").html());
	var t = setTimeout("invokeSettime()", 1000);	
	if (delay > 1) {
	    delay--;
	 	$(".getCode span").html(delay);
	 } else {
		clearTimeout(t); 
		smsCode = true;
		$(".getCode").html("重新获取验证码");
	}       
}
$(".bindWeChat").click(function(){
	$(".bindWeChatIframe p").html("微信绑定中。。。");
	$(".bindWeChatIframe").show();
	document.getElementById("wechatBtn").click();
	wechatFlag();
});
$(".bindWeChatAgain").click(function(){
	$(".bindWeChatIframe p").html("微信解绑中。。。");
	$(".bindWeChatIframe").show();
	document.getElementById("wechatAgainBtn").click();
	wechatFlagUnbundling();
});

$(".oldPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(checkNonempty($(".oldPassword").val())){
			$(".oldPassword").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".oldPassword").addClass("light").focus().siblings(".tip").fadeIn();
			return false;
		}
	}
});
$(".confirmPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if($(".newPassword").val().replace(/(^\s*)|(\s*$)/g, "")==$(".confirmPassword").val().replace(/(^\s*)|(\s*$)/g, "")){
			$(".confirmPassword").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".confirmPassword").addClass("light").siblings(".tip").fadeIn();
		}
	}
});
$(".newPassword").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(checkNonempty($(".newPassword").val())&&checkRegistPassword($(".newPassword").val())){
			$(".newPassword").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".newPassword").addClass("light").siblings(".tip").fadeIn();
		}
	}
});

$(".smsCode").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(checkNonempty($(".smsCode").val())){
			$(".smsCode").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".smsCode").addClass("light").siblings(".tip").fadeIn();
		}
	}	
});
$(".newPhone").blur(function(){
	if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		if(checkNonempty($(".newPhone").val())&&checkPhone($(".newPhone").val())){
			$(".newPhone").removeClass("light").siblings(".tip").fadeOut();			
		}else{
			$(".newPhone").addClass("light").siblings(".tip").fadeIn();
			return false;
		}
	}	
});

var t;		
function wechatFlag() {	
	t = setTimeout("wechatFlag()", 1000);		
	 $.ajax({
	        url: '../rest/qcb/admin/getWechatFlag',
	        type: 'get',
	        async:false,
	        data: {
	            "timestamp":new Date().getTime() 
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
		    	if(r.data==true){
					clearTimeout(t); 
					$(".iframe").hide();
					layer.msg("绑定成功，即将刷新页面", {
			            time: 2000
			        },function(){
			        	window.location.href=window.location.href;
			        });
					
				}else{
					/*  layer.msg("绑定失败，请重新绑定");  */
				}
	    	}else{
	    		layer.msg(r.message);
	    	}	
	    }).fail(function(r){
	    	 layer.msg("error", {
	             time: 2000
	         });
	    });
	
}
function wechatFlagUnbundling() {	
	t = setTimeout("wechatFlagUnbundling()", 1000);		
	 $.ajax({
	        url: '../rest/qcb/admin/getWechatFlag',
	        type: 'get',
	        async:false,
	        data: {
	            "timestamp":new Date().getTime()  
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
		    	if(r.data==false){
					clearTimeout(t); 
					$(".iframe").hide();
					/* layer.msg("解绑成功，请重新绑定", {
			            time: 2000
			        },function(){
			        	window.location.href=window.location.href;
			        }); */
					layer.confirm('解绑成功，请继续绑定', {
				        btn: ['取消', '确定'] //按钮
				    }, function() {
				    	window.location.href=window.location.href;
				    }, function() {
				    	layer.closeAll();
				    	$(".iframeInner p").html("微信绑定中。。。");
				    	$(".bindWeChatIframe").show();
						document.getElementById("wechatBtn").click();
						wechatFlag();
				    }); 					
					
				}else{
					/*  layer.msg("绑定失败，请重新绑定");  */
				}
	    	}else{
	    		layer.msg(r.message);
	    	}	
	    }).fail(function(r){
	    	 layer.msg("error", {
	             time: 2000
	         });
	    });
	
}
$(".bindWeChatIframe .closeIframe").click(function(){
	clearTimeout(t); 
	$(".iframe").hide();
});
