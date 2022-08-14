$(document).ready(function() {
	var regTel = /^1(1|2|3|4|5|6|7|8|9)\d{9}$/;
	var base = new Base64();
	var message = {		//表单信息
		"bankcard": "",
//		"phone": "",
		"mobileCode": ""
	}
	getAccountInfo();
	get();

	function getAccountInfo() {
		$.ajax({
				type: "get",
				url: api + "/employee/getAccountInfo",
				async: true,
				data: {
					"token": token()
				},
				beforeSend:function(){
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					$("#name").val(r.data.name);
					loadingOut();
				}else{
					if(r.status != "ERROR"){
						
						layerIn(r.message);
					}else{
						loadingOut();
						isWechat();
					}	
				}
			})
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
			});
	}
	
	function get() {
		$.ajax({
				type: "get",
				url: api + "/employee/get",
				async: true,
				data: {
					"token": token()
				},
				beforeSend:function(){
					loadingIn();
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					$("#mobile").val(r.data.mobileFull);
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
			.fail(function() {
				loadingOut();
				layerIn("服务器错误");
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
	$("#icon-delete").tap(function(){
		$('#bankcard').val("");
	});
	
	//保存表单信息到本地存储,并跳转页面
	$("#bankList").tap(function() {
//		if($("#bankcard").val().replace(/\s/g,"").length < 12 || $("#bankcard").val().replace(/\s/g,"").length > 21) {
//			layerIn("银行卡号位数需在12-21位之间");
//			return false
//		}
		message.bankcard = $("#bankcard").val();
//		message.phone = $("#mobile").val();
		message.mobileCode = $("#mobileCode").val();
		localStorage.setItem("message", JSON.stringify(message));
		window.location.href = "./selectBank.html?openId=" + openIdUrl;
	});

	//获取验证码
	$("#getCode").tap(function() {
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		getCode("/sms/sendCodeByMobile", "emp_bankcard_add");
	})

	$("#allRight").tap(function() {
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
		if($("#mobileCode").val() == "") {
			layerIn("验证码输入有误");
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
				url: api + "/bankcard/add",
				async: true,
				data: {
					"bankcard": base.encode($("#bankcard").val().replace(/\s/g,"")),
					"phone": base.encode($("#mobile").val()),
					"mobileCode": base.encode($("#mobileCode").val()),
					"bank": $("#bankuuid").val(),
					"token": token()
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
//						window.location.href = localStorage.getItem("topPage");
						window.location.href = "./myMessage.html?openId=" + openIdUrl;
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
	});

});
$('#bankcard,#mobileCode').on('blur',function(){
	window.scroll(0,0);
});