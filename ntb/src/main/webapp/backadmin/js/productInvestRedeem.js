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
	var redeemAmount=$("#redeemAmount").val().replace(/(^\s*)|(\s*$)/g, "");
	var investIncome=$("#investIncome").val().replace(/(^\s*)|(\s*$)/g, "");
	var returnCapital=$("#returnCapital").val().replace(/(^\s*)|(\s*$)/g, "");
	var returnInterest=$("#returnInterest").val().replace(/(^\s*)|(\s*$)/g, "");
	var platfomIncome=$("#platfomIncome").val().replace(/(^\s*)|(\s*$)/g, "");
	if(redeemAmount==""){
		layer.msg('投资金额不能为空', {
			time: 2000 
		});      
	}else if(investIncome==""){
		layer.msg('投资收益不能为空', {
			time: 2000 
		});      
	}else if(returnCapital==""){
		layer.msg('返还用户本金不能为空', {
			time: 2000 
		});      
	}else if(returnInterest==""){
		layer.msg('返还用户利息不能为空', {
			time: 2000 
		});      
	}else if(platfomIncome==""){
		layer.msg('平台收益不能为空', {
			time: 2000 
		});      
	}else{
		var str = $(this).serialize();
		str += "&uuid="+uuid;
		$.post('../rest/backadmin/bankFinancialProductInvest/redeem',str, function(data) {
			if (data.status == "SUCCESS") {
				layer.msg('提交审核成功', {
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