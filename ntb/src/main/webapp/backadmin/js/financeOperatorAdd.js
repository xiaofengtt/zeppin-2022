//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var role=$("#role").val()
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var realname=$("#realname").val().replace(/(^\s*)|(\s*$)/g, "");
	var mobile=$("#mobile").val().replace(/(^\s*)|(\s*$)/g, "");
	var email=$("#email").val().replace(/(^\s*)|(\s*$)/g, "");
	if(role==""){
		layer.msg('请选择角色', {
			time: 2000 
		});    
	}else if(name==""){
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
	}else if(!checkPhone(mobile)){
		layer.msg('手机号校验失败', {
			time: 2000 
		});    
	}else if(!checkEmail(email)){
		layer.msg('邮箱校验失败', {
			time: 2000 
		});    
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/financeOperator/add',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg(data.message, {
					time: 2000 
				}, function(){
					window.location.href="financeOperatorList.jsp";
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