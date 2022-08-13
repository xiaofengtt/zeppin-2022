$(document).ready(function(){
var init=new Object();
init.msgInit=function(){
$('.form-horizontal').Validform();
$('.xxxviewinfo a').click(function(){
var obj=this;	
var id=$(this).parent().find('input').val();
$.post("teacher_delMsg.action",{id:id},function(){
	
 $(obj).parent().parent().parent().fadeOut("slow");
});

	
});	
	
};

init.msgInit();
});