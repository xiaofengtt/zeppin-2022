var islogin = url("?islogin");
$(document).ready(function(){
	//全局企业账户ID
	var companyID = "";
	getAccountList();
	//获取企业账户列表
	function getAccountList(){
		$.ajax({
			type:"get",
			url:"",
			async:false,
			data:{
				"timestamp":new Date().getTime() 
			}
		})
		.done(function(r){
			  
		})
		.fail(function(){
			
		});
	};
	
	//获取企业账户列表
	function flushinfo(){
		$.ajax({
			type:"get",
			url:"../rest/qcb/companyAccount/flush",
			async:false,
			data:{
				"timestamp":new Date().getTime() 
			}
		});
	};
	$(".change").click(function(){
		var company = $(this).parent().attr("uuid");
		$.ajax({
			type:"get",
			url:"../rest/qcb/admin/changeCompany",
			async:false,
			data:{
				'company':company,
	            "timestamp":new Date().getTime() 
			}
		})
		.done(function(r){
			if (r.status == "SUCCESS") {
				window.location.href="index.jsp";
				$.cookie($("#currentUserId").val(),company,{ expires: 365, path: '/' });
			}else{
				if(r.errorCode=="301"){
					layer.msg(r.message, {
			            time: 2000
			        },function(){
			        	window.location.href="login.jsp";
			        });
				}else{
					layer.msg(r.message, {
			            time: 2000
			        });
				}
				
			}
		})
		.fail(function(){
			layer.msg("error", {
	            time: 2000
	        });
		});
	});
	var name = $("#name").html();
	if (name == ''){
		window.location.href = "../qcb/login.jsp";
	}
	$("main").css({"min-height":$(window).height()-134+"px"});
	flushinfo();
	//查看是否有cookie值
	if($.cookie($("#currentUserId").val())&&islogin){
		$.ajax({
			type:"get",
			url:"../rest/qcb/admin/changeCompany",
			async:false,
			data:{
				'company':$.cookie($("#currentUserId").val()),
	            "timestamp":new Date().getTime() 
			}
		})
		.done(function(r){
			if (r.status == "SUCCESS") {
				window.location.href="index.jsp";			
			}else{
				
			}
		})
		.fail(function(){
		});
	}
});
$(window).resize(function(){
	$("main").css({"min-height":$(window).height()-194+"px"});
});
