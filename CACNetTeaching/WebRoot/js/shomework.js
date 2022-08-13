$(document).ready(function(){
	var init=new Object();
	init.Main=function(){
	$('#scheduleContent').hide();
	$('#scheduleHead').click(function(){
    $('#scheduleContent').slideToggle("slow");		
	});
	  $('.submit').click(function(){
		 var obj=$(this);
		 var id = obj.attr("id");
		 if($("#uploadFile"+id).val()==""){
			 alert("请选择文件后提交！");
			 return false;
		 }  
			   
	  });
	};	
 	
	
	init.Main();
	
});