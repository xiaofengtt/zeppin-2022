$(document).ready(function(){

var init=new Object();
init.formInit=function(){
	
};

init.list=function(){
   $('.xx').click(function(){
	var obj=this;   
	var id= $(this).find("input").val();
	$.post("teacher_delCourseWare.action",{id:id},function(data){
		
		$(obj).parent().fadeOut("slow");
	});
	
   });
   $('#submit').click(function(){
	  
	 if($('#file').val()==""){
		 alert("请选择文件后提交！");
		 return false;
	 }  
	   
   });
    
};




init.formInit();
init.list();
document.title="上传课件";
});