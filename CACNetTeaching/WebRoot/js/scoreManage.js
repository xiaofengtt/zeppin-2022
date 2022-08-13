$(document).ready(function(){
	var init=new Object();
	init.studentList=function()
	{
		$("div.holder").jPages({
		      containerID : "list",
		      previous : "←",
		      next : "→",
		      perPage :20,
		      delay : 20
		    });
		if($('#isSubmit').val()==1){
			$('#submit').attr("disabled",true);
			$(".submit").attr("disabled",true);
		}
		
		$(".submit").click(function(){
			var sid=$(this).next(".sid").val();
			var score=$(this).parent().parent().find('.score').val();
			var obj=this;
			//改变状态
			$.post("teacher_scoreInput.action",{courseId:courseId,sid:sid,score:score},function(data){
				if (data.Status == "fail"){
					alert("超过90分的不能多于20%")
				}else{
					$(obj).val("提交成功");
				}
			});
			
		});
		
	/*	$('#inputAll').click(function(){
			
			
		});*/
		$('#submit').click(function(){
			
			if(confirm("提交后成绩无法再更改，是否提交")){
			//改变状态
				$.post("teacher_scoreSubmit.action",{courseId:courseId},function(data){
					alert("提交完成");
					$('#submit').attr("disabled",true);
					$(".submit").attr("disabled",true);
				});
				
			}
			
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