$(document).ready(function(){
	var init=new Object();
	init.studentList=function()
	{
		$("div.holder").jPages({
		      containerID : "homeworkList",
		      previous : "←",
		      next : "→",
		      perPage :20,
		      delay : 20
		    });
		
		
	
	  $('select').change(function(){
		  var obj=this;
		  var id=$(this).parent().find('input').val();
		  var val=$(this).val();
		  //alert(id+$(this).val());
		  $.post("course_updateHomewrokState.action",{id:id,val:val},function(data){
			  alert('提交成功');
			  location.reload(); 
		  });
	  });
	};
	// 获取url参数
	init.getUrlPara=function(name){
		
		
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象

			var r = window.location.search.substr(1).match(reg); // 匹配目标参数

			if (r != null)
				return unescape(r[2]);

			return null; // 返回参数值

		
		
	};
var courseId=init.getUrlPara("coursedesignId");	
init.studentList();	
document.title="学生成绩管理";	
});