$(document).ready(function(){

var init=new Object();
init.formInit=function(){
	$('.form-horizontal').Validform();
	$('#startTime').datepicker({
		
		 showMonthAfterYear: true, // 月在年之后显示
		    changeMonth: true,     // 允许选择月份
		    changeYear: true,     // 允许选择年份
		    dateFormat:'yy-mm-dd',    // 设置日期格式
		    closeText:'关闭',     // 只有showButtonPanel: true才会显示出来
		    duration: 'fast',
		    showAnim:'fadeIn',
		    buttonText:'选择日期',
		    showButtonPanel: true,
		    showOtherMonths: true
	
	});
};

init.list=function(){
   $('.xx').click(function(){
	var obj=this;   
	var id= $(this).find("input").val();
	$.post("course_scheduleManage.action",{opt:"del",id:id},function(data){
		
		$(obj).parent().fadeOut("slow");
	});
	
   });
    
};

init.formInit();
init.list();

});