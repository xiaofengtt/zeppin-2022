$(document).ready(function(){

var init=new Object();
init.formInit=function(){
	
	$('.form-horizontal').Validform();

	$('#endDate').datepicker({
		
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
	$('#submit').click(function(){
		if($('#endDate').val()==""){
			
			alert('请填写截止日期');
			return false;
		}
		
	});
};

init.list=function(){
   $('.xx').click(function(){
	var obj=this;   
	var id= $(this).find("input").val();
	$.post("teacher_delHomework.action",{opt:"del",id:id},function(data){
		
		$(obj).parents('.mod').fadeOut("slow");
	});
	
   });
    
};

init.formInit();
init.list();

});