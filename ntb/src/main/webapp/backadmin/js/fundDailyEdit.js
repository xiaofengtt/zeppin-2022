$(function(){
	
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/fund/netvalueGet?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			$('#statistime').val(r.data.statistime);
			$('#netValue').val(r.data.netValue);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
})
//提交
$('#formsubmit').submit(function() {
	if (flagSubmit == false) {
        return false;
    }
    flagSubmit = false;
    setTimeout(function() {
        flagSubmit = true;
    }, 3000);
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	var netValue=$("#netValue").val().replace(/(^\s*)|(\s*$)/g, "");
	var statistime=$("#statistime").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(netValue==""){
		layer.msg('净值不能为空', {
			time: 2000 
		});      
	}else if(statistime==""){
		layer.msg('添加日期不能为空', {
			time: 2000 
		});      
	}else{
		var str = $(this).serialize();
		str += "&uuid="+uuid;
		$.post('../rest/backadmin/fund/netvalueEdit',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('修改成功', {
					time: 1000 
				}, function(){
					window.parent.getList();
					parent.$.colorbox.close()
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