var cuuid='';
$(document).ready(function() {
	
	var uuid = (url('?pid') != null) ? url('?pid') : '';
	if(uuid != ''){
		cuuid = uuid;
		$("#menuInfo").attr('href','menuInfoList.jsp?pid='+uuid);
		$.get('../rest/backadmin/menu/menuget?uuid='+uuid,function(r) {
			if(r.status =='SUCCESS') {
				$("#pid").val(r.data.uuid);
				$("#pids").val(r.data.title);
			} else {
				layer.msg(r.message, {
					time: 2000 
				})
			}
		}).done(function(){
			$("#parent").show();
		})
		
	} else {
		$("#menuInfo").attr('href','menuInfoList.jsp');
	}

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
	var title=$("#title").val().replace(/(^\s*)|(\s*$)/g, "");
	var scode=$("#scode").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(name==""){
		layer.msg('菜单名称不能为空', {
			time: 2000 
		});      
	}else if(title==""){
		layer.msg('菜单标题不能为空', {
			time: 2000 
		});
	}else if (scode == ""){
		layer.msg('菜单编码不能为空', {
			time: 2000 
		});
	}else{
		var str = $(this).serialize();
		$.post('../rest/backadmin/menu/menuadd',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
					time: 1000 
				}, function(){
					if(cuuid != null){
						window.location.href="menuInfoList.jsp?pid="+cuuid;
					}else{
						window.location.href="menuInfoList.jsp";
					}
					
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