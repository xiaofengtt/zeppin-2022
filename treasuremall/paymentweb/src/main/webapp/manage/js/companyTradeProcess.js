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
var tradeTypeObj = {
	'recharge':'注资',
	'withdraw':'提现'
}
var typeObj = {
	'detail':'交易详情',
	'check':'交易审核',
	'success':'交易成功',
	'fail':'交易失败',
	'close':'交易关闭'
}
$(function(){
	$("#type").html(typeObj[type]);
	$('#'+type+'Div').css('display','block');
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
  
//	upload.render({
//		elem: '#uploadProof',
//		url: '../system/resource/add',
//		done: function(res){
//			$('input[name="proof"]').val(res.data.uuid);
//			$('#proofImages').attr('data-url','..' + res.data.url);
//			$("#proofImages").show();
//		}
//	});
});
//获取详情
function getDetail(){
	$.ajax({
        url: '../system/companyTrade/get',
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
				bankcardHtml = '<p>所属银行：' + r.data.companyBankcardBankName +'</p>'
					+ '<p>银行卡号：' + r.data.companyBankcardNum +'</p>'
					+ '<p>开户行地区：' + r.data.companyBankcardArea +'</p>'
					+ '<p>开户支行：' + r.data.companyBankcardBranchBank +'</p>'
			}
			
			$("#companyCardData").html(bankcardHtml);
			$("#data").html(r.data.data.replaceAll('\n','<br/>'));
			$("#createtime").html(formatDate(r.data.createtime));
			$("#operattime").html(r.data.operattime == null ? '' : formatDate(r.data.operattime));
			$("#operatorName").html(stringValue(r.data.operatorName));
			
			$("#failReason").html(stringValue(r.data.failReason));
			$("#proof").html(stringValue(r.data.proof));
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

function showImage(obj){
	var url = $(obj).attr("data-url");
	window.open(url)
}

$(".lay-submit").click(function(){
	var dataMap = {'uuid': uuid};
	if(type == 'success'){
		if($("input[name=proof]").val()==""){
			layer.msg("请填写交易凭证");
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
	
	$.ajax({
        url: '../system/companyTrade/'+type,
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
