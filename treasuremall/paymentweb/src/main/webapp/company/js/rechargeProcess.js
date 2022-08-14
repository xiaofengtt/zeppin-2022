/**
 * 支付订单处理
 */
var type = url('?type');
var uuid = url('?uuid');
var statusObj = {
	'normal':'未付款',
	'checking':'待审核',
	'checked':'已审核',
	'fail':'处理失败',
	'close':'已关闭',
	'success':'已完成',
}
var transDataObj = {
	'01':{'title':'标题'},
	'02':{'orderid':'订单号'},
	'03':{'title':'标题'},
	'04':{'orderid':'转账单号'},
	'05':{'remark':'备注内容'},
	'06':{'remark':'备注内容'},
	'07':{'holder':'转账人'}
}
var typeObj = {
	'detail':'交易详情'
}
$(function(){
	$("#type").html(typeObj[type]);
	if(type =='detail'){
		$('#submitDiv').hide();
	}
	getDetail();
})
layui.use(['form','upload','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,upload = layui.upload
	,$ = layui.jquery
	,element = layui.element;
});
//获取详情
function getDetail(){
	$.ajax({
        url: '../store/userRecharge/get',
        type: 'get',
        async:false,
        data: {
        	'uuid':uuid
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$("#status").html(statusObj[r.data.status]);
			$("#orderNum").html(r.data.orderNum);
			$("#companyName").html(r.data.companyName);
			$("#companyCode").html(r.data.companyCode);
			$("#companyOrderNum").html(r.data.companyOrderNum);
			$("#channelName").html(r.data.channelName);
			$("#channelAccountName").html(stringValue(r.data.channelAccountName));
			$("#channelAccountNum").html(stringValue(r.data.channelAccountNum));
			$("#totalAmount").html((r.data.totalAmount/100).toFixed(2));
			$("#poundage").html((r.data.poundage/100).toFixed(2));
			$("#amount").html((r.data.amount/100).toFixed(2));
			$("#companyBalance").html((r.data.companyBalance/100).toFixed(2));
			$("#companyBalanceLock").html((r.data.companyBalanceLock/100).toFixed(2));
			var transData = '';
			var transDataMap = transDataObj[r.data.channelCode];
			for(var key in transDataMap){
				transData = transData + '<p><label>' + transDataMap[key] + '：</label>' + stringValue(r.data.transDataMap[key]) + '</p>'; 
			}
			$("#transData").html(transData);
			$("#createtime").html(formatDate(r.data.createtime));
			$("#operattime").html(r.data.operattime == null ? '' : formatDate(r.data.operattime));
			$("#failReason").html(stringValue(r.data.failReason));
			if(type == 'check'){
				getChannelAccountList(r.data.channel);
			}
			
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
        url: '../store/userRecharge/'+type,
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
