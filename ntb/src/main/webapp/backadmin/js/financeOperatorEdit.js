$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/financeOperator/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#DataCnt').html(html);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(r){
		$("#"+r.data.role).attr("selected","selected");
	})
});
//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	var realname=$("#realname").val().replace(/(^\s*)|(\s*$)/g, "");
	var mobile=$("#mobile").val().replace(/(^\s*)|(\s*$)/g, "");
	var email=$("#email").val().replace(/(^\s*)|(\s*$)/g, "");
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
		$.post('../rest/backadmin/financeOperator/edit',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('修改成功', {
					time: 1000 
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