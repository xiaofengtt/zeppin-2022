var cuuid='';
$(document).ready(function() {
	
	var uuid = (url('?controller') != null) ? url('?controller') : '';
	if(uuid != ''){
		cuuid = uuid;
		$.get('../rest/backadmin/controllerMethod/controllerget?uuid='+uuid,function(r) {
			if(r.status =='SUCCESS') {
				$("#cuuid").val(r.data.uuid);
				$("#controllerName").val(r.data.description);
				$("#methodInfoList").attr('href','methodInfoList.jsp?uuid='+r.data.uuid)
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}).done(function(){
			$("#parent").show();
		})
		
	}

});


//提交
$('#formsubmit').submit(function() {
	var name=$("#name").val().replace(/(^\s*)|(\s*$)/g, "");
	if(name==""){
		layer.msg('方法名称不能为空', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/controllerMethod/methodadd',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
//					window.parent.getList();
//					parent.$.colorbox.close()
					window.location.href="methodInfoList.jsp?uuid="+cuuid;
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