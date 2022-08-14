$(function(){
	refresh();
})
function refresh(){
	var deviceWidth = document.documentElement.clientWidth;
	if(deviceWidth>1920){
	document.documentElement.style.fontSize=1920/19.2+"px";
	}else{
		document.documentElement.style.fontSize=deviceWidth/19.2+"px";
	}
}

window.onresize=function(){
	refresh();
}