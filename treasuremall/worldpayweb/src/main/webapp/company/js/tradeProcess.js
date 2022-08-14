/**
 * 
 */
var type = url('?type');
var uuid = url('?uuid');
var statusObj = {
	'checking':'checking',
	'checked':'checked',
	'fail':'fail',
	'close':'close',
	'success':'success',
}
var tradeTypeObj = {
	'recharge':'recharge',
	'withdraw':'withdraw'
}
var typeObj = {
	'detail':'Transaction details',
	'retry':'Retry',
	'close':'Transaction close'
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
			$("#companyChannel").html(r.data.companyChannel);
			$("#currency").html(r.data.currency);
			
			$("#totalAmount").html((r.data.totalAmount/100).toFixed(2));
			$("#poundage").html((r.data.poundage/100).toFixed(2));
			$("#amount").html((r.data.amount/100).toFixed(2));
			$("#balance").html((r.data.balance/100).toFixed(2));
			$("#balanceLock").html((r.data.balanceLock/100).toFixed(2));
			
			var bankcardHtml = '';
			if(r.data.companyBankcard != null){
				bankcardHtml = '<p><label>bank：</label>' + r.data.companyBankcardBankName +'</p>'
					+ '<p><label>bankcard：</label>' + r.data.companyBankcardNum +'</p>'
					+ '<p><label>area：</label>' + r.data.companyBankcardArea +'</p>'
					+ '<p><label>branch：</label>' + r.data.companyBankcardBranchBank +'</p>'
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
			layer.msg('Successful', {
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
