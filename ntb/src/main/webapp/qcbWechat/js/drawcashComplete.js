$(document).ready(function() {
	var price = url("?price");
	var uuid = url("?uuid");

	var now = Number(Date.now());
	var tomorrow = now + 86400000;
	$("#price").html(formatNum(price));
	$("#targetDate").html("预计" + timestampToTime(tomorrow) + "分到账")
	$.ajax({
			type: "get",
			url: api + "/bankcard/get",
			async: true,
			data: {
				"token": token(),
				"uuid": uuid
			},
			beforeSend:function(){
				loadingIn();
			}
		})
		.done(function(r) {
			if(r.status == "SUCCESS") {
				$("#name").html(r.data.name + " 尾号" + r.data.bankcard);
			}else{
				isWechat();
			}
			loadingOut();
		})
		.fail(function() {
			layerIn("服务器错误");
		});

	function timestampToTime(timestamp) {
		var date = new Date(timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
		Y = date.getFullYear() + '年';
		M = (date.getMonth() + 1 < 10 ? (date.getMonth() + 1) : date.getMonth() + 1) + '月';
		D = date.getDate() + '日 ';
//		h = date.getHours() + ':';
		h = (date.getHours() + 1 < 10 ? '0' + (date.getHours() + 1) : date.getHours() + 1) + ':'
//		m = date.getMinutes() + ':';
//		m = date.getMinutes();
		m = (date.getMinutes() + 1 < 10 ? '0' + (date.getMinutes() + 1) : date.getMinutes() + 1)
		s = date.getSeconds();
//		return Y + M + D + h + m + s;
		return Y + M + D + h + m;
	}
	
	$("#allRight").tap(function(){
		window.location.href = "./myMessage.html?openId="+openIdUrl;
	});
});