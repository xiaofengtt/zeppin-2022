var carousel;//轮播计时器
var num=0;//index值
$(function(){
	BrowserType();
	$(".line-box").css({"height":$(".content-line").height()});
	$(".coverDiv").css({"height":$(".content-line").height()});
	
	$("#conduct .conduct-inner .content-line .spot-box").hover(function(){
		$(this).addClass("light").siblings().removeClass("light").find("div").removeClass("pulse");
		$(this).find("div").addClass("pulse");
	});
	if(BrowserType()=="IE7"||BrowserType()=="IE8"||BrowserType()=="0"){
		$(".gtie9").hide();
		$(".ltie9").show();
	}else{
		$(".gtie9").show();
		$(".ltie9").hide();
	}
	//折线图轮播
	carousel = setInterval(autoChange, 3000);   
    $('#conduct .conduct-inner .content-line .spot-box').hover(function () {
        clearInterval(carousel);
    }, function () {
    		num = $(this).index();
        carousel = setInterval(autoChange, 3000);
    }).click(function () {
        num = $(this).index();
        $(this).addClass("light").siblings().removeClass("light").find("div").removeClass("pulse");
		$(this).find("div").addClass("pulse");
    });
    
});

$(window).resize(function(){
	$(".line-box").css({"height":$(".content-line").height()});
});

//自动轮播
function autoChange() {
	num = (num == 4) ? 0 : ++num;
	$("#conduct .conduct-inner .content-line .spot-box:eq(" + num + ")").addClass("light").siblings().removeClass("light").find("div").removeClass("pulse");
	$("#conduct .conduct-inner .content-line .spot-box:eq(" + num + ")").find("div").addClass("pulse");
}
//判断浏览器是否是ie以及版本
function BrowserType()  
{  
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器   
    if (isIE)   
    {  
         var reIE = new RegExp("MSIE (\\d+\\.\\d+);");  
         reIE.test(userAgent);  
         var fIEVersion = parseFloat(RegExp["$1"]);  
         if(fIEVersion == 7)  
         { return "IE7";}  
         else if(fIEVersion == 8)  
         { return "IE8";}  
         else if(fIEVersion == 9)  
         { return "IE9";}  
         else if(fIEVersion == 10)  
         { return "IE10";}  
         else if(fIEVersion == 11)  
         { return "IE11";}  
         else  
         { return "0"}//IE版本过低  
     }//isIE end 
     else{
     	return "no ie";
     }
 }//myBrowser() end  
$(window).scroll(function(){
	if($(".coverDiv").offset().top<$(this).scrollTop()+$(window).height()-100){
		$(".coverDiv").animate({"left":"200%"},2000);
	}
});
$(document).ready(function(){
	if($(".coverDiv").offset().top<$(this).scrollTop()+$(window).height()+300){
		$(".coverDiv").animate({"left":"200%"},2000);
	}
});



