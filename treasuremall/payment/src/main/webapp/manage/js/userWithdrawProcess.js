/**
 * 用户提现审核
 */
var type = url('?type');
var uuid = url('?uuid');
var statusObj = {
	'normal':'待处理',
	'checking':'待审核',
	'checked':'已审核',
	'fail':'处理失败',
	'close':'已关闭',
	'success':'已完成',
}
var transDataObj = {
	'10':{'title':'标题','bank':'所属银行','bankcard':'银行卡号','holder':'持卡人姓名'}
}
var typeObj = {
	'check':'交易审核',
	'success':'交易成功',
	'fail':'交易失败'
}
$(function(){
	$("#type").html(typeObj[type]);
	$('#'+type+'Div').css('display','block');
	getDetail();
})
layui.use(['form','upload','element'], function(){
	var form = layui.form
	,layer = layui.layer
	,upload = layui.upload
	,$ = layui.jquery
	,element = layui.element;
  
	upload.render({
		elem: '#uploadProof',
		url: '../system/resource/add',
		done: function(res){
			$('input[name="proof"]').val(res.data.uuid);
			$('#proofImages').attr('data-url','..' + res.data.url);
			$("#proofImages").show();
		}
	});
});
//渠道账户列表
function getChannelAccountList(channel){
	$.ajax({
        url: '../system/channelAccount/list',
        type: 'get',
        async:false,
        data: {
        	'status':'normal',
        	'channel':channel,
        	'pageSize':'1000',
        	'pageNum':1
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<option value="">请选择</option>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
			}
			$("select[name='channelAccount']").html(tableHtml);
		} 
    })
}
//获取详情
function getDetail(){
	$.ajax({
        url: '../system/userWithdraw/get',
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
			$("#channelAccountName").html(r.data.channelAccountName == null ? '' : r.data.channelAccountName);
			$("#channelAccountNum").html(r.data.channelAccountNum == null ? '' : r.data.channelAccountNum);
			$("#totalAmount").html((r.data.totalAmount/100).toFixed(2));
			$("#poundage").html((r.data.poundage/100).toFixed(2));
			$("#amount").html((r.data.amount/100).toFixed(2));
			$("#companyBalance").html((r.data.companyBalance/100).toFixed(2));
			$("#companyBalanceLock").html((r.data.companyBalanceLock/100).toFixed(2));
			var transData = '';
			var transDataMap = transDataObj[r.data.channelCode];
			for(var key in transDataMap){
				transData = transData + transDataMap[key] + '：' + r.data.transDataMap[key] + '</br>'; 
			}
			$("#transData").html(transData);
			$("#createtime").html(formatDate(r.data.createtime));
			$("#operattime").html(r.data.operattime == null ? '' : formatDate(r.data.operattime));
			$("#operatorName").html(r.data.operatorName == null ? '' : r.data.operatorName);
			if(type == 'check'){
				getChannelAccountList(r.data.channel);
			}
			
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    });   	
}

function showImage(obj){
	var url = $(obj).attr("data-url");
	window.open(url)
}

$(".lay-submit").click(function(){
	var dataMap = {'uuid': uuid};
	if(type == 'check'){
		if($("select[name=channelAccount]").val()==""){
			layer.msg("请选择渠道账户");
			return false;
		}
		dataMap['channelAccount'] = $("select[name=channelAccount]").val();
	}else if(type == 'success'){
		if($("input[name=proof]").val()==""){
			layer.msg("请上传交易凭证");
			return false;
		}
		dataMap['proof'] = $("input[name=proof]").val();
	}else if(type == 'fail'){
		if($("input[name=reason]").val()==""){
			layer.msg("请填写失败原因");
			return false;
		}
		dataMap['reason'] = $("input[name=reason]").val();
	}
	console.log(dataMap);
	$.ajax({
        url: '../system/userWithdraw/'+type,
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
		        	window.location.href="login.html";
		        });
			}else{
				layer.msg(r.message);
			}
		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    }); 
	return false;
})
