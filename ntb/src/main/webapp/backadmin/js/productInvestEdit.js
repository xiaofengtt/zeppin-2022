$(function(){
	var uuid = (url('?uuid') != null) ? url('?uuid') : '';
	$.get('../rest/backadmin/bankFinancialProductInvest/get?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			$('#amount').val(r.data.amount);
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	})
})
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
		str += "&uuid="+uuid;
		$.post('../rest/backadmin/bankFinancialProductInvest/edit',str, function(data) {
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