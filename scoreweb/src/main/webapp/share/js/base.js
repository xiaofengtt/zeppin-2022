var windowHeight = $(window).height(),	//获得屏幕高度
	layerHeight = $(".layer").height();
var isIDnumber = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;//身份证号
var regularNumber = /^\d+(\.\d+)?$|\d+(\.)?$/;//声明只包含数字和小数点的正则
var realnameAuthFlag = false;//实名flag
$(function(){
	refresh();
	$(".layerDiv").height(windowHeight);
	$(".payBox").css("min-height",windowHeight);
	$("#code-box").height(windowHeight);
	$(".agreementBox").height(windowHeight);
})
function refresh(){
	var deviceWidth = document.documentElement.clientWidth;
	if(deviceWidth>750){
	document.documentElement.style.fontSize=750/7.5+"px";
	}else{
		document.documentElement.style.fontSize=deviceWidth/7.5+"px";
	}
}

window.onresize=function(){
	refresh();
}
function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
};
