$(function(){
	
})
function countChar(textareaName,spanName){
	document.getElementById(spanName).innerHTML =  document.getElementById(textareaName).value.length;
	document.getElementById(textareaName).value=document.getElementById(textareaName).value.substr(0,499); 		
} 
$(".firLevel li").click(function(){
	if($(this).children("img").attr("src")=="../img/rarrow-youhui.png"){
		$(this).children("ul").css("display","block");
		$(this).children("img").attr("src","../img/rarrow-xiahui.png");
		return false;
	}else{
		$(this).children("ul").css("display","none");
		$(this).children("img").attr("src","../img/rarrow-youhui.png");
		return false;
	}
	
});

$(".addressee").click(function(){
	if($(this).attr("name")=="no"){
		var name=$(this).children("a").html()+";";
		$(this).attr("name","yes");
		$("#recipient").val($("#recipient").val()+name);
	}else{
		
	}
})


