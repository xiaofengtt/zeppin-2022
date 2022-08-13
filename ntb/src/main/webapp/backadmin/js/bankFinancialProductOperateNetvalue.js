var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function(){
	getDate();	
});

//获取值
function getDate(){
	$.get('../rest/backadmin/bankFinancialProduct/operateNetvalueGet?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			$('#titlename').html(r.data.bankFinancialProductName);
			$('#name').html(r.data.bankFinancialProductName);
			$('#custodian').html(r.data.custodian);
			$('#ascode').html(r.data.scode);
			if(r.data.dataList.length > 0){
				var template = $.templates('#queboxTpl');
				var html = template.render(r.data.dataList);
				$('#queboxCnt').html(html);
			}
		} else {
			layer.msg(r.message, {
				time: 2000 
			})
		}
	});
}