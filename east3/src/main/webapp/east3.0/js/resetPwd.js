//提交
$('#formsubmit').submit(function() {
	var id = (url('?id') != null) ? url('?id') : '';
	$("#id").val(id);
	var password=$("#password").val().replace(/(^\s*)|(\s*$)/g, "");
	var passwordNew=$("#passwordNew").val().replace(/(^\s*)|(\s*$)/g, "");
	var passwordNewCheck=$("#passwordNewCheck").val().replace(/(^\s*)|(\s*$)/g, "");
	if(password==""){
		layer.msg('请输入原密码', {
			time: 2000 
		});    
	}else if(passwordNew==""){
		layer.msg('新密码不能为空', {
			time: 2000 
		});     
	}else if(passwordNewCheck==""){
		layer.msg('确认密码不能为空', {
			time: 2000 
		});   
	}else if(passwordNew!=passwordNewCheck){
		layer.msg('确认密码与新密码不一致，请修改!', {
			time: 2000 
		});    
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/operator/password',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.confirmMsg('恭喜你!<br>密码修改成功!', "修改密码", {
					time: 2000 
				}, function(){
					parent.$.colorbox.close();
					window.parent.location.href="../views/login.jsp";
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