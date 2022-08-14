var api = "../rest/qcbWechat",	//后台接口前缀
	appId = "wxae71d4aafb72f0aa",
	Url = "wechat.qicaibao.cc",
	openIdUrl = url("?openId") != null ? url("?openId") : "",
	ua = navigator.userAgent.toLowerCase(),//获取设备苹果或安卓
	moneyReg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,//输入金额验证正则
	integermoneyReg = /^[1-9]\d*$/,//正整数金额验证正则
	windowHeight = $(window).height(),	//获得屏幕高度
	layerHeight = $(".layer").height();
	pageshow();
//--------------
	
//判断是否微信,是：重定向页面，不是，跳转空页面
function isWechat(){
	localStorage.removeItem("openId");
	clearCookie("openId");
	if(ua.match(/MicroMessenger/i) == "micromessenger"){
		window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=https://" + Url + "/qcbWechat/index.html?&response_type=code&scope=snsapi_base&state=qcb#wechat_redirect";	
	}else{
		window.location.href = "./blank.html";
	}
}

//--------------
//判断是否微信,是：重定向页面，不是，跳转空页面
function judgeWechat(){
	if(ua.match(/MicroMessenger/i) == "micromessenger"){
		
	}else{
		window.location.href = "./blank.html";
	}
}

//提示框高度赋值
$("#modal-box").css({
	"height": windowHeight
});

//确认框高度赋值
$("#confirm-box").css({
	"height": windowHeight
});

//提示信息框：为绑卡等等....
$("#tip-box,#tip-boxs").css({
	"height": windowHeight
});

//验证码弹出框高度赋值
$("#code-box").css({
	"height": windowHeight
});

//转菊花弹出框高度赋值
$("#loading-box").css({
	"height": windowHeight
});
//--------------

$('#mobileCode').on('blur',function(){
	window.scroll(0,0);
});

//获取时间戳函数
function getTime() {
	var time = "";
	$.ajax({
			type: "get",
			url: api + "/wx/getTime",
			async: false
		})
		.done(function(r) {
			time = r.data;
		});
	return time;
}

//--------------

//除登录外，获取验证码;
function getCode(src,code) {
	var i = 60,
	t,
	regTel = /^1(1|2|3|4|5|6|7|8|9)\d{9}$/,
	code = code,
	device = "05",
	Key = "acd50ce3f96114043e138b04074eeb40",
	time = "",
	base = new Base64();
	$("#getCode").hide();
	$("#numCode").show();
	$("#count").html(i);
	t = setInterval(function() {
		i--;
		$("#count").html(i);
		if(i < 0) {
			clearInterval(t);
			$("#getCode").show().html("获取验证码");
			$("#numCode").hide();
		}
	}, 1000);

	if(regTel.test($("#mobile").val()) == false) {
		layerIn("请输入正确的手机号码");
		$("#numCode").hide();
		$("#getCode").show();
		clearInterval(t);
		return false;
	}
	//var time = getTime();
	//md5 = hex_md5(Key + time + $("#mobile").val() + code);
	//token = base.encode(device + time + md5);
	$.ajax({
			type: "post",
			url: api + src,
			async: true,
			data: {
				"mobile": base.encode($("#mobile").val()),
				"codeType": base.encode(code),
				"token": token()
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
//--------------

//提现，解绑银行卡等发送验证码
function getCodeToCheck(type){
	$("#getCode").hide();
	$("#numCode").show();
	var base = new Base64();
		i = 60,
		mobileNumber = "";
	$("#count").html(i);
	t = setInterval(function() {
		i--;
		$("#count").html(i);
		if(i < 0) {
			clearInterval(t);
			$("#getCode").show().html("获取验证码");
			$("#numCode").hide();
		}
	}, 1000);
	$.ajax({
			type: "get",
			url: api + "/employee/get",
			async: false,
			data: {
				"token": token()
			}
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				mobileNumber = r.data.mobile;
			}
		})
		.fail(function() {
			layerIn("服务器错误");
		});
	
	$.ajax({
		type:"post",
		url:api+"/sms/sendCodeToCheck",
		async:false,
		data:{
			"token":token(),
			"codeType":base.encode(type)
		}
	})
	.done(function(r){
		if(r.status == "SUCCESS"){
			$("#success-title").html("验证码已发送至" + mobileNumber);
		}else{
			layerIn(r.message);
			$("#numCode").hide();
			$("#getCode").show();
			clearInterval(t);
			return false;
		}
	})
	.fail(function(){
		layerIn("服务器错误");
		$("#numCode").hide();
		$("#getCode").show();
		clearInterval(t);
		return false;
	});
}

//--------------

//对openId 进行token加密
function token() {
	var base = new Base64(),
		device = "05",
		Key = "acd50ce3f96114043e138b04074eeb40",
		openId = "",
		md5 = "",
		token = "";
		
	//判断本地存储中有没有openid
	if(localStorage.getItem("openId") != "" && localStorage.getItem("openId") != null){
		//有，使用
		openId = localStorage.getItem("openId");
	}else{
		//没有，从url拿到openId赋值到本地存储
		localStorage.setItem("openId",openIdUrl);
		if(getCookie("openId") != null){		//判断cookie中有没有openID
			openId = getCookie("openId");	//有，使用
		}else{
			setCookie("openId",openIdUrl);	//没有，从url拿到openID赋值到cookie
			openId = openIdUrl;		//本地存储和cookie都没有，使用url上的openId
		}
	}

	$.ajax({
			type: "get",
			url: api + "/wx/getTime",
			async: false,
			cache:false
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				md5 = hex_md5(Key + r.data + openId);
				token = base.encode(device + r.data + openId + md5);
			}
		});
	return token;
}

//--------------

//测试当前登录状态用的请求
function pageGet(){
	$.ajax({
		type:"get",
		url:api+"/employee/get",
		async:false,
		cache:false,
		data:{
			"token":token()
		}
	})
	.done(function(r){
		if(r.status == "ERROR"){
			loadingOut();
			isWechat();
		}
	})
	.fail(function(){
		layerIn("服务器错误");
	});
}
//--------------


//给每个页面绑定事件，测试当前登录状态
function pageshow(){
	window.addEventListener('pageshow', function(event) {
		judgeWechat();
	});
}


//为每一个.openId-url超链接后面加上openID
function appendOpenId(){
	$(".openId-url").each(function(index,el){
		var _thisProp = $(".openId-url").eq(index).prop("href");
		if(_thisProp.indexOf("?") == -1){
			$(".openId-url").eq(index).prop("href",_thisProp + "?openId="+openIdUrl);	
		}else{
			$(".openId-url").eq(index).prop("href",_thisProp + "&openId="+openIdUrl);
		}
	});
}

//整数部分三位加一个逗号
function addCommas(nStr)
{
 nStr += '';
 x = nStr.split('.');
 x1 = x[0];
 x2 = x.length > 1 ? '.' + x[1] : '';
 var rgx = /(\d+)(\d{3})/;
 while (rgx.test(x1)) {
  x1 = x1.replace(rgx, '$1' + ',' + '$2');
 }
 return x1 + x2;
}
//返回首页
$("#goIndex").tap(function(){
	window.location.href = "./myMessage.html?openId=" + openIdUrl;
});
//拖动返回首页按钮
var flag = false;
     var cur = {
         x:0,
         y:0
    }
     var nx,ny,dx,dy,x,y ;
     function down(){
        flag = true;
         var touch ;
         if(event.touches){
             touch = event.touches[0];
         }else {
             touch = event;
         }
         cur.x = touch.clientX;
         cur.y = touch.clientY;
//         dx = div2.offsetLeft;
         dy = div2.offsetTop;
     }
     function move(){
         if(flag){
             var touch ;
             if(event.touches){
                 touch = event.touches[0];
             }else {
                 touch = event;
             }
             nx = touch.clientX - cur.x;
             ny = touch.clientY - cur.y;
             x = dx+nx;
             y = dy+ny;
//             div2.style.left = x+"px";
             div2.style.top = y +"px";
             //阻止页面的滑动默认事件
             document.addEventListener("touchmove",function(){
                 event.preventDefault();
             },false);
         }
     }
     //鼠标释放时候的函数
     function end(){
         flag = false;
     }
     var div2 = document.getElementById("goIndex");
     div2.addEventListener("mousedown",function(){
         down();
     },false);
     div2.addEventListener("touchstart",function(){
         down();
     },false)
     div2.addEventListener("mousemove",function(){
         move();
     },false);
     div2.addEventListener("touchmove",function(){
        move();
     },false)
     document.body.addEventListener("mouseup",function(){
         end();
     },false);
     div2.addEventListener("touchend",function(){
         end();
     },false);