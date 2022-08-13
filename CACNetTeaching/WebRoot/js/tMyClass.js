$(document).ready(function(){
	var init=new Object();
	init.courseList=function()
	{
		$("div.holder").jPages({
		      containerID : "courselst",
		      previous : "←",
		      next : "→",
		      perPage :4,
		      delay : 20
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
init.courseList();	
document.title="我的课程";	
});