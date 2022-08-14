$(document).ready(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/controllerMethod/controllerget?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			var template = $.templates('#DataTpl');
		    var html = template.render(r.data);
		    $('#DataCnt').html(html);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	}).done(function(){
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
	
	if(name==""){
		layer.msg('银行名称不能为空', {
			time: 2000 
		});      
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/controllerMethod/controlleredit',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('修改成功', {
					time: 1000 
				}, function(){
//					window.parent.getList();
//					parent.$.colorbox.close()
					window.location.href="controllerInfoList.jsp";
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