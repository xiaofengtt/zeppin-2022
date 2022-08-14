//提示框开启
function modalIn(msg){
	$("#modal-box").fadeIn();
	$("#modal-box").find("h4").html(msg);
}
//提示框关闭
function modalOut(){
	$("#modal-box").fadeOut();
}

$("#modal-box-close").tap(function(){
	$("#modal-box").fadeOut();
});

//---------------

//确认框开启
function confirmIn(msg){
	$("#confirm-box").fadeIn();
	$("#confirm-box").find("h4").html(msg);
}
//确认框关闭
function confirmOut(){
	$("#confirm-box").fadeOut();
}

$("#confirm-box-close").tap(function(){
	$("#confirm-box").fadeOut();
});

//---------------

//提示信息框开启
function tipIn(){
	$("#tip-box").fadeIn();
}
//提示信息框关闭
function tipOut(){
	$("#tip-box").fadeOut();
}

$("#tip-close").tap(function(){
	$("#tip-box").fadeOut();
});
$("#tip-closes").tap(function(){
	$("#tip-boxs").fadeOut();
});

//---------------

//验证码弹出框关闭

function codeBoxIn(){
	$("#code-box").fadeIn();
}
function codeBoxOut(){
	$("#code-box").fadeOut();
}
$("#code-box-close").tap(function(){
	$("#code-box").fadeOut();	
});

//---------------

//小黑框弹出
function layerIn(msg){
	if($(".layer").css("display") != "block"){
		$(".layer").html(msg);
		$('.layer').fadeIn(function(){
			setTimeout(function(){
				$(".layer").fadeOut();
			},1000);
		});
	}
}

//layer高度定位
$(".layer").css({
	"top":windowHeight/2,
	"margin-top":layerHeight * -4
});
//---------------

//转菊花弹出／隐藏
function loadingIn(){
	$("#loading-box").show();
}

function loadingOut(){
	$("#loading-box").hide();
}

//---------------