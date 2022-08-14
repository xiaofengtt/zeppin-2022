/**
 * 
 */
var type = url('?type');
var uuid = url('?uuid');
var statusObj = {
	'normal':'pending',
	'checking':'checking',
	'checked':'checked',
	'fail':'fail',
	'close':'close',
	'success':'success',
}
var tradeTypeObj = {
	'recharge':'injection',
	'withdraw':'withdraw'
}
var typeObj = {
	'detail':'business_detail',
	'check':'business_check',
	'success':'business_success',
	'fail':'business_fail',
	'close':'business_close'
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
			$("#companyChannel").html(r.data.companyChannel);
			$("#currency").html(r.data.currency);
			
			$("#totalAmount").html((r.data.totalAmount/100).toFixed(2));
			$("#poundage").html((r.data.poundage/100).toFixed(2));
			$("#amount").html((r.data.amount/100).toFixed(2));
			$("#balance").html((r.data.balance/100).toFixed(2));
			$("#balanceLock").html((r.data.balanceLock/100).toFixed(2));
			
			var bankcardHtml = '';
			if(r.data.companyBankcard != null){
				bankcardHtml = '<p>bank：' + r.data.companyBankcardBankName +'</p>'
					+ '<p>bankCard：' + r.data.companyBankcardNum +'</p>'
					+ '<p>area：' + r.data.companyBankcardArea +'</p>'
					+ '<p>branch：' + r.data.companyBankcardBranchBank +'</p>'
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
			layer.msg("Please fill in the proof");
			return false;
		}
		dataMap['proof'] = $("input[name=proof]").val();
	}else if(type == 'fail'){
		if($("input[name=reason]").val()==""){
			layer.msg("Please fill in the failReason");
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
