//数据加载
$(document).ready(function() {
	getRoomInfo();
})

// 二次确认
function confirm(id) {
	$.ajax({
		type : "post",
		url : "../weixin/weixinConfirm",
		async : true,
		timeout : 3000,
		data : {
			"id" : id
		},
		success : function(data) {
			if (data.Status == 'success') {
				window.location.reload(true)
			} else {
				alert(data.Message);
			}
		},
		error : function() {
			alert("链接超时，请稍后再试");
		}
	});
}


//function getCurrent() {
//	var mUrl = '../weixin/weixinGetCurrent?';
//	$.get(mUrl, function(r) {
//		if (r.Status == 'unlogin') {
//			window.location.href = 'login.html?pagename=roomMessage';
//		} else if (r.Status == 'success') { // 有考试
//			getRoomInfo();
//		} else if (r.Status == 'end') { // 考试结束
//			var txt1 = "<header><p>提示：</p></header>";
//			var txt2 = "<footer><p>考试已结束</p></footer>";
//			$("body").append(txt1, txt2);
//		} else if (r.Status == 'empty') { // 暂无考试
//			var txt1 = "<header><p class='remind'>提示：</p></header>";
//			var txt2 = "<footer><p class='remind'>暂无考试</p></footer>";
//			$("body").append(txt1, txt2);
//		} else { // 获取失败
//			var txt1 = "<header><p>提示：</p></header>";
//			var txt2 = "<footer><p class='remind'>数据加载失败</p></footer>";
//			$("body").append(txt1, txt2);
//		}
//	}).done(function(r) {});
//}

function getRoomInfo(){
	var id = url('?id') == null ? '' : url('?id');
	var code = url('?code') == null ? '' : url('?code');
	var mUrl = '../weixin/weixinGetRoomInfo?';
	mUrl += 'id=' + id;
	mUrl += '&code=' + code;
	$.get(mUrl, function(r) {
//		if (r.Status == 'unlogin') {
//			window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2a3df3ca05c5be3f&redirect_uri=http://df3c45ab.ngrok.io/XJEMS/XJ_wechat/login.html?pagename=roomMessage&params='+id+'&response_type=code&scope=snsapi_userinfo&state=xj#wechat_redirect';

//			window.location.href='login.html?pagename=roomMessage&params='+id;
//		}else 
			if (r.Status == 'success') {
			var template = $.templates('#queboxTpl');
			var html = template.render(r.Records);
			$('#queboxCnt').html(html);
		}else if(r.Status == 'unregistered'){ // 未注册{
			var txt1="<header><p>提示</p></header>";  
			var txt2="<main><div><br>"+r.Message+"</div></main>"; 
			$("body").append(txt1,txt2);     
			$("div").css("color","red");
			window.location.href = 'addTeacher.html?';
		}else{
			var txt1="<header><p>提示</p></header>";  
			var txt2="<main><div><br>"+r.Message+"</div></main>"; 
			$("body").append(txt1,txt2);     
			$("div").css("color","red");
		}
	}).done(function(r) {
	});
}