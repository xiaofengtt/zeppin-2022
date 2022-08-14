var windowHeight = $(window).height(),	//获得屏幕高度
	layerHeight = $(".layer").height();
$(function(){
	refresh();
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