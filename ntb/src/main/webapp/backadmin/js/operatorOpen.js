var flagSubmit = true;
//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var password=$("#password").val();
	var confirmPassword=$("#confirmPassword").val();
	
	if(password==""){
		layer.msg('密码不能为空', {
			time: 2000 
		});      
	}else if(confirmPassword==""){
		layer.msg('请确认密码', {
			time: 2000 
		});
	}else if(confirmPassword != password){
		layer.msg('两次输入密码不一致', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/operator/open',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('密码修改成功', {
					time: 1000 
				}, function(){
					window.location.href="index.jsp"
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