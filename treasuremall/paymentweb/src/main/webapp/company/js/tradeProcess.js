/**
 * 用户提现审核
 */
var type = url('?type');
var uuid = url('?uuid');
var statusObj = {
	'checking':'待审核',
	'checked':'已审核',
	'fail':'处理失败',
	'close':'已关闭',
	'success':'已完成',
}
var tradeTypeObj = {
	'recharge':'注资',
	'withdraw':'提现'
}
var typeObj = {
	'detail':'交易详情',
	'retry':'重新提交',
	'close':'交易关闭'
}
$(function(){
	$("#type").html(typeObj[type]);
	if(type =='detail'){
		$('#submitDiv').hide();
	}
	getDetail();
})
layui.use(['form','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
});
//获取详情
function getDetail(){
	$.ajax({
        url: '../store/companyTrade/get',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$("#status").html(statusObj[r.data.status]);
			$("#tradeType").html(tradeTypeObj[r.data.type]);
			$("#orderNum").html(r.data.orderNum);
			$("#companyName").html(r.data.companyName);
			$("#companyCode").html(r.data.companyCode);
			
			$("#totalAmount").html((r.data.totalAmount/100).toFixed(2));
			$("#poundage").html((r.data.poundage/100).toFixed(2));
			$("#amount").html((r.data.amount/100).toFixed(2));
			$("#companyBalance").html((r.data.companyBalance/100).toFixed(2));
			$("#companyBalanceLock").html((r.data.companyBalanceLock/100).toFixed(2));
			
			var bankcardHtml = '';
			if(r.data.companyBankcard != null){
				bankcardHtml = '<p><label>所属银行：</label>' + r.data.companyBankcardBankName +'</p>'
					+ '<p><label>银行卡号：</label>' + r.data.companyBankcardNum +'</p>'
					+ '<p><label>开户行地区：</label>' + r.data.companyBankcardArea +'</p>'
					+ '<p><label>开户支行：</label>' + r.data.companyBankcardBranchBank +'</p>'
			}
			
			$("#companyCardData").html(bankcardHtml);
			$("#data").html(r.data.data.replaceAll('\n','<br/>'));
			$("#createtime").html(formatDate(r.data.createtime));
			$("#operattime").html(r.data.operattime == null ? '' : formatDate(r.data.operattime));
			$("#failReason").html(stringValue(r.data.failReason));
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	parent.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    });   	
}

$(".lay-submit").click(function(){
	var dataMap = {'uuid': uuid};
	
	$.ajax({
        url: '../store/companyTrade/'+type,
        type: 'post',
        async:false,
        data: dataMap
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			layer.msg('处理成功', {
				time: 1000 
			}, function(){
				var index = parent.layer.getFrameIndex(window.name); 
				parent.location.reload();
				parent.layer.close(index);
			}); 
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	parent.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        });
    }); 
	return false;
})
