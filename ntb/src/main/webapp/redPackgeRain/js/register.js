$(document).ready(function() {
//	if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent) == false) {
//		return false;
//	}
	$(".bg").css({"height":$(window).height()+"px"});
	var ua = navigator.userAgent.toLowerCase(); 
//	if(ua.match(/MicroMessenger/i) != "micromessenger") {  
//		return false;
//	}
	var price = url("?price");
	var regTel = /^1(3|4|5|7|8)\d{9}$/;
	var device = "04";
	var time = "";
	var token = "";
	var md5 = "";
	var Key = "97fac19d29cbbd540ea16400be456b13";
	var realPrice = strDec(price, "fuck");
	var priceH = (Number(realPrice) * 100).toFixed(2);

	$("#price").html("<i>¥</i>"+realPrice+"<small>元</small>");

	$(".bg").find("a").tap(function(){
		$(".bg").hide();
	});

	function getTime() {
		$.ajax({
				type: "get",
				url: urlApi + "/web/product/getTime",
				async: false
			})
			.done(function(r) {
				time = r.data;
			});
	}
	$("#submit").tap(function() {
		$("#submit").prop("disabled", true);
		if(regTel.test($("#tel").val()) == false) {
			$(".bg").show().find("p").html("请输入正确的手机号码");
			$("#submit").prop("disabled", false);
			return false;
		}

		if($("#code").val() == "") {
			$(".bg").show().find("p").html("请输入正确的验证码");
			$("#submit").prop("disabled", false);
			return false;
		}

		getTime();

		md5 = hex_md5(Key + time + $("#tel").val() + $("#code").val() + priceH.toString());
		var base = new Base64();
		token = base.encode(device + time + md5);

		$.ajax({
				type: "post",
				url: urlApi + "/web/login/register",
				async: false,
				data: {
					"token": token,
					"phone": base.encode($("#tel").val()),
					"price": base.encode(priceH.toString())
				}
			})
			.done(function(r) {
				if(r.status == "SUCCESS") {
					window.location.href = "./registerSc.html?price=" + realPrice;
				} else {
					$(".bg").show().find("p").html(r.message);
					$("#submit").prop("disabled", false);
				}
			})
			.fail(function(){
				$(".bg").show().find("p").html("服务器错误");
			})

	});

	$("#get-code").tap(function() {
		var base = new Base64();
		var i = 60;
		var t;
		$("#get-code").hide();
		$("#re-get").show();
		$("#count").html(i);
		t = setInterval(function() {
			i--;
			$("#count").html(i);
			if(i < 0) {
				clearInterval(t);
				$("#get-code").show().html("获取验证码");
				$("#re-get").hide();
			}
		}, 1000);

		if(regTel.test($("#tel").val()) == false) {
			$(".bg").show().find("p").html("请输入正确的手机号码");
			$("#get-code").show().html("获取验证码");
			$("#re-get").hide();
			clearInterval(t);
			return false;
		}

		var tel = base.encode($("#tel").val());
		var codeType = base.encode("register");
		
		getTime();

		md5 = hex_md5(Key + time + $("#tel").val() + "register");
		var base = new Base64();
		token = base.encode(device + time + md5);
		//		请求验证码
		$.ajax({
				type: "get",
				url: urlApi + "/web/sms/sendCode",
				async: true,
				data: {
					"phone": tel,
					"codeType": codeType,
					"token":token
				}
			})
			.done(function(r) {
				if(r.status != "SUCCESS") {
					$(".bg").show().find("p").html(r.message);
					$("#get-code").show().html("获取验证码");
					$("#re-get").hide();
					clearInterval(t);
					return false;
				}
			})
			.fail(function() {
				$("#get-code").show().html("获取验证码");
				$("#re-get").hide();
				clearInterval(t);
				$(".bg").show().find("p").html("服务器错误");
				return false;

			});

	});
});