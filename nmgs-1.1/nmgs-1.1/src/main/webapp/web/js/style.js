$(function(){
	refresh();
	if(navigator.userAgent.indexOf('UCBrowser') > -1) {
		$("#header .header_inner a.nav").css({"width":"1.0rem"});
		$("#header .header_inner a.logo").css({"width":"2.11rem","height":"1.0rem"});
	}
})
function refresh(){
	
}
var deviceWidth = document.documentElement.clientWidth;
if(deviceWidth>750){
document.documentElement.style.fontSize=750/7.5+"px";
}else{
	document.documentElement.style.fontSize=deviceWidth/7.5+"px";
}
window.onresize=function(){
	refresh();
}
var Index;
function nav(){
	 $("ul.Index").slideToggle();
}








