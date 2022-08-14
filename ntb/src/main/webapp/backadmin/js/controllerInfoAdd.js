$(document).ready(function() {
	//上传图片
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
		layer.msg('功能名称不能为空', {
			time: 2000 
		});      
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/controllerMethod/controlleradd',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
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