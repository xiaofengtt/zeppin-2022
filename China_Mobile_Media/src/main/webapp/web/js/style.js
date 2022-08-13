$(function(){
	refresh();
	
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
//	Index=layer.open({
//	  type: 1,
//	  title: false,
//	  skin: 'layui-layer-rim', 
//	  offset: ['0px', '0px'],
//	  shade: 0.6,
//	  shift: 5,
//	  area: ['100%', 'auto'],
//	  content: "<ul class='Index'><a class='closeUl' onclick='layer.close(Index)'><img src='images/Close.png' alt='导航'></a><a class='logo' href='index.html'><img src='images/logo2.png' alt='logo'></a><li><a href='javascript:'>首页</a></li><li><a href='javascript:'>新机发布</a></li><li class='light'><a href='javascript:'>评测</a></li><li><a href='javascript:'>业务办理</a></li><li><a href='javascript:'>明星Show</a></li></ul>"
//	});
//	$(".layui-layer-setwin .layui-layer-close2").css("display", "none");
//	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
	 $("ul.Index").slideToggle();
}








