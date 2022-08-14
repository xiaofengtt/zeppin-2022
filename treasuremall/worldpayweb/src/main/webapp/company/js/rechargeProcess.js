/**
 * 
 */
var type = url('?type');
var uuid = url('?uuid');
var statusObj = {
	'normal':'unpaid',
	'checking':'checking',
	'checked':'checked',
	'fail':'fail',
	'close':'close',
	'success':'success'
}
var transDataObj = {
	'01':{'title':'title'},
	'02':{'orderid':'orderid'}
}
var typeObj = {
	'detail':'Transaction details'
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
			$("#currency").html(r.data.currency);
			$("#channelAccountName").html(stringValue(r.data.channelAccountName));
			$("#channelAccountNum").html(stringValue(r.data.channelAccountNum));
			$("#totalAmount").html((r.data.totalAmount/100).toFixed(2));
			$("#poundage").html((r.data.poundage/100).toFixed(2));
			$("#amount").html((r.data.amount/100).toFixed(2));
			$("#balance").html((r.data.balance/100).toFixed(2));
			$("#balanceLock").html((r.data.balanceLock/100).toFixed(2));
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
