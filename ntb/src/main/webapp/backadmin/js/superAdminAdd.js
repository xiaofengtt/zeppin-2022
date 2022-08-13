//提交
$('#formsubmit').submit(function() {
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var realname=$("#realname").val().replace(/(^\s*)|(\s*$)/g, "");
	var mobile=$("#mobile").val().replace(/(^\s*)|(\s*$)/g, "");
	if(name==""){
		layer.msg('用户名不能为空', {
			time: 2000 
		});     
	}else if(realname==""){
		layer.msg('真实姓名不能为空', {
			time: 2000 
		});   
	}else if(mobile==""){
		layer.msg('手机不能为空', {
			time: 2000 
		});    
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/superAdmin/add',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
					window.location.href="superAdminList.jsp";
				}); 
			} else {
				layer.msg(data.message, {
					time: 2000 
				})
			}
		}) 
	}
	return false;
});