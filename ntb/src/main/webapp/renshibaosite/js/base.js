//随机在线咨询
function consulte(){
	var urls = new Array();
	urls[0] = "http://wpa.qq.com/msgrd?v=3&uin=44255428&site=qq&menu=yes";
	urls[1] = "http://wpa.qq.com/msgrd?v=3&uin=2494299642&site=qq&menu=yes";
	n = Math.floor(Math.random()*2);
	/* $("#onclineConsulte").attr("href",urls[n]); */
	$("#onclineConsulte").attr("href","http://wpa.qq.com/msgrd?v=3&uin=2128762975&site=qq&menu=yes");
	document.getElementById("onclineConsulte").click();
}

//返回顶部
function goTop(){
	$("html,body").animate({scrollTop:0},500);
}
