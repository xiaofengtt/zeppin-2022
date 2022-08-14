var flagSubmit = true;
var base = new Base64();
var userid = url("?userid") != null ? url("?userid") : "";//用户id
$(document).ready(function() {
	var regTel = /^1(1|2|3|4|5|6|7|8|9)\d{9}$/;
	var message = {		//表单信息
		"bankcard": "",
//		"phone": "",
		"mobileCode": ""
	}
	getUserInfo();
	//获取用户信息
	function getUserInfo(){
		$.ajax({
			type: "get",
			url: "../rest/shbxWeb/user/get",
			async: true,
			data: {
				
			}
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				$("#name").val(r.data.realnameFull);
				$("#mobile").val(r.data.phoneFull);//用户姓名
				loginLight();
			}else {
				if(r.status == "ERROR"&&r.errorCode=="302"){
					location.replace("login.html");
				}else{
						
				}				
			}
		})
		.fail(function() {
			
		});
	}
		
	
	//本地存储读取页面跳转前填写的表单信息。
	if(localStorage.getItem("message")) {
		var messageJson = JSON.parse(localStorage.getItem("message"));
		$("#bankcard").val(messageJson.bankcard);
//		$("#mobile").val(messageJson.phone);
		$("#mobileCode").val(messageJson.mobileCode);
	}
	
	//本地存储读取选择的银行。
	if(localStorage.getItem("bankData")) {
		var bankJson = JSON.parse(localStorage.getItem("bankData"));
		$(".value").hide();
		$("#bankList").find(".bank-msg").find("span").html(bankJson.name);
		$("#bankList").find(".bank-msg").find("img").prop("src", ".." + bankJson.icon);
		$("#bankList").find("input").val(bankJson.uuid);
		$(".bank-msg").show();
		loginLight();
	}
	
	//每输入4位自动补全空格
	$('#bankcard').on('input',function(){
		var $this = $(this),
		v = $this.val();
		/\S{5}/.test(v) && $this.val(v.replace(/\s/g,'').replace(/(.{4})/g, "$1 "));
   });
    
    //银行卡号input获取焦点，删除按钮显示
	$('#bankcard').focus(function(){
		$("#icon-delete").fadeIn();
	});
	
	//银行卡号input失去焦点，删除按钮隐藏
	$('#bankcard').blur(function(){
		$("#icon-delete").fadeOut();
	});
	
	//点击删除按钮，晴空银行卡号的value
	$("#icon-delete").click(function(){
		$('#bankcard').val("");
	});
	
	//保存表单信息到本地存储,并跳转页面
	$("#bankList").click(function() {
//		if($("#bankcard").val().replace(/\s/g,"").length < 12 || $("#bankcard").val().replace(/\s/g,"").length > 21) {
//			layerIn("银行卡号位数需在12-21位之间");
//			return false
//		}
		message.bankcard = $("#bankcard").val();
//		message.phone = $("#mobile").val();
		message.mobileCode = $("#mobileCode").val();
		localStorage.setItem("message", JSON.stringify(message));
		window.location.href = "./selectBank.html";
	});

	//获取验证码
	$("#getCode").click(function() {
		if($("#bankcard").val() == "") {
			layerIn("请填写银行卡号");
			return false
		}
		if($("#bankcard").val().replace(/\s/g,"").length < 12 || $("#bankcard").val().replace(/\s/g,"").length > 21) {
			layerIn("银行卡号位数需在12-21位之间");
			return false
		}
		if($("#bankuuid").val() == "") {
			layerIn("请选择银行");
			return false
		}
		if(regTel.test($("#mobile").val()) == false) {
			layerIn("请输入正确的手机号码");
			return false
		}
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		getcode();
	})

	$("#allRight").click(function() {
		if($(this).hasClass("light")){
			if($("#bankcard").val().replace(/\s/g,"").length < 12 || $("#bankcard").val().replace(/\s/g,"").length > 21) {
				layerIn("银行卡号位数需在12-21位之间");
				return false
			}
			if(flagSubmit == false) {
				return false;
			}
			flagSubmit = false;
			setTimeout(function() {
				flagSubmit = true;
			}, 3000);
			
			$.ajax({
					type: "post",
					url: "../rest/shbxWeb/user/bindingCard",
					async: true,
					data: {
						"bankcard": base.encode($("#bankcard").val().replace(/\s/g,"")),
						"phone": base.encode($("#mobile").val()),
						"code": base.encode($("#mobileCode").val()),
						"bank": $("#bankuuid").val()
					},
					beforeSend:function(){
						loadingIn();
					}
				})
				.done(function(r) {
					if(r.status == "SUCCESS") {
						layerIn(r.message);
						//绑卡成功后，删除本地存储的表单信息，银行信息，当前选择银行列表的index
						localStorage.removeItem("message");
						localStorage.removeItem("bankData");
						localStorage.removeItem("selected");
						setTimeout(function(){
							if(userid!=""){
								window.location.href = localStorage.getItem("topPage")+"?userid="+localStorage.getItem("userid");
							}else{
								window.location.href = "./bankCardList.html";
							}
//							window.location.href = localStorage.getItem("topPage");
							
						},1500);
					} else {
						layerIn(r.message);
					}
					loadingOut();
				})
				.fail(function() {
					loadingOut();
					layerIn("服务器错误");
				});
		}else{
			if($("#bankcard").val() == "") {
				layerIn("请填写银行卡号");
				return false
			}
			
			if($("#bankuuid").val() == "") {
				layerIn("请选择银行");
				return false
			}
			if(regTel.test($("#mobile").val()) == false) {
				layerIn("请输入正确的手机号码");
				return false
			}
			if($("#mobileCode").val() == "") {
				layerIn("验证码输入有误");
				return false
			}
		}
		
		
		
	});

});
//发送验证码
function getcode(){
	delayURL();
	$.ajax({
		type: "post",
		url: '../rest/shbxWeb/user/bindingSendCode',
		async: true,
		data: {
			"bankcard": base.encode($("#bankcard").val().replace(/\s/g,"")),
			"phone":base.encode($("#mobile").val()),
			"bank": $("#bankuuid").val()
		},
		beforeSend:function(){
//			loadingIn();
		}
	})
	.done(function(r) {
		if(r.status == "SUCCESS") {
			layerIn(r.message);
		} else {
			layerIn(r.message);
			$("#numCode").hide();
			$("#getCode").show();
			clearInterval(t);
		}
	})
	.fail(function() {
		layerIn("服务器错误");
		$("#numCode").hide();
		$("#getCode").show();
		clearInterval(t);
	});
}
var t;
//倒计时
function delayURL() {
  var delay = $("#count").html();
  t = setTimeout("delayURL()", 1000);
  if (delay > 0) {
      delay--;
      $("#count").html(delay);
      $("#getCode").hide();
      $("#numCode").show();
  } else {
      clearTimeout(t);
      codeFlag = true;
      $("#getCode").show();
      $("#numCode").hide();
      $("#count").html('60');
  }
}
$('input').bind('input propertychange', function() {  
	loginLight();
});
function loginLight(){
	var bankcard = $("#bankcard").val().replace(/\s/g,"");
	var bankuuid = $("#bankuuid").val().replace(/\s/g,"");
	var mobile = $("#mobile").val().replace(/\s/g,"");
	var mobileCode = $("#mobileCode").val().replace(/\s/g,"");
	if(bankcard!=""&&bankuuid!=""&&mobile!=""&&mobileCode!=""){
		$("#allRight").addClass("light");
	}else{
		$("#allRight").removeClass("light");
	}
}




