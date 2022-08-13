//提交
$('#formsubmit').submit(function() {
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	var amount=$("#amount").val().replace(/(^\s*)|(\s*$)/g, "");
	
	if(amount==""){
		layer.msg('投资金额不能为空', {
			time: 2000 
		});      
	}else{
		var str = $(this).serialize();
		str += "&bankFinancialProductPublish="+uuid;
		$.post('../rest/backadmin/bankFinancialProductInvest/add',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('添加成功', {
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