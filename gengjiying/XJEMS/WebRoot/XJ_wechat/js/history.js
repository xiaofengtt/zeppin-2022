$(document).ready(function(){
	var code = url('?code') == null ? '' : url('?code');
	$.ajax({
		type:"get",
		url:"../weixin/weixinHistoryInfo?pagenum=1&pagesize=100&code="+code,
		async:true,
		timeout:3000,
		success:function(r){
			if (r.Status == 'unlogin') {

				window.location.href =r.Records;
//				window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx2a3df3ca05c5be3f&redirect_uri=http://df3c45ab.ngrok.io/XJEMS/XJ_wechat/login.html?pagename=history&response_type=code&scope=snsapi_userinfo&state=xj#wechat_redirect';

//				window.location.href='login.html?pagename=history';
			}else if(r.Status=="success"){
				var template = $.templates("#tpl").render(r.Records);
				$("#show").html(template);
				var height = $(".content_right").height()/2*-1;
				$(".content_right").css({"margin-top":height+'px'});
			}else{   
				var template = $.templates("#tpl");
				$("#show").html("<p class='warning'>"+r.Message+"</p>");
			}
		},
		error:function(){
			alert("链接超时，请稍后重试");
		}
	});
})
