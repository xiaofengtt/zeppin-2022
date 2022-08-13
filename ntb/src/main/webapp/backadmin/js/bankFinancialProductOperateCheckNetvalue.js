var uuid = (url('?uuid') != null) ? url('?uuid') : '';

$(function(){
	getDate();	
});

//获取值
function getDate(){
	$.get('../rest/backadmin/bankFinancialProduct/operateNetvalueGet?uuid='+uuid,function(r) {
		if(r.status =='SUCCESS') {
			if(r.data.status=='unchecked'){
				$('.operateCheck').css("display","block")
			}
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

function checked(t) {
	var reason = $('#checkReason').val();
	$.get('../rest/backadmin/bankFinancialProduct/operateCheck?uuid='+uuid+'&status=checked&reason='+reason, function(r) {
		if (r.status == 'SUCCESS') {
			layer.msg('操作成功', {
				time: 1000 
			}, function(){
				window.location.href="bankFinancialProductOperateCheckList.jsp";
			});
		} else {
			alert('操作失败,' + r.message);
		}
	})
}

function unpassed(t) {
	var reason = $('#checkReason').val();
	$.get('../rest/backadmin/bankFinancialProduct/operateCheck?uuid='+uuid+'&status=unpassed&reason='+reason, function(r) {
		if (r.status == 'SUCCESS') {
			layer.msg('操作成功', {
				time: 1000 
			}, function(){
				window.location.href="bankFinancialProductOperateCheckList.jsp";
			});
		} else {
			alert('操作失败,' + r.message);
		}
	})
}