/**
 * 
 */
var size = (url('?pagesize') != null) ? url('?pagesize') : '';
var num = (url('?pagenum') != null) ? url('?pagenum') : '';
var statusObj = {
		'normal':'正常',
		'disable':'失效',
		'blacklist':'黑名单',
		'highrisk':'高危'
}
var levelObj = {
		'registered':'注册用户',
		'recharged':'充值用户',
		'VIP':'VIP用户',
		'demo':'测试用户'
}
$(function(){
	var data = JSON.parse(window.localStorage.getItem("userWithdraw"))
	var dataUuid = data.uuid
	var registerDay = formatDateDay(new Date().getTime()-data.registertime);
	window.localStorage.removeItem("userWithdraw")
	$(".frontUserAccountHolder").html(data.frontUserAccountHolder)
//	$(".frontUserAccountNumber").html(data.frontUserAccountNumber.replace(/(.{4})/g,'$1 '))
	$(".frontUserAccountNumber").html(data.frontUserAccountNumber)
	$(".orderNum").html(data.orderNum)
	$(".area").html(data.area)
	$(".amount").html(data.amount.toFixed(2))
	$(".poundage").html(data.poundage.toFixed(2))
	$(".actualAmount").html(data.actualAmount.toFixed(2))
	$(".frontUserBankName").html(data.frontUserBankName)
	$(".totalPayment").html(data.totalPayment)
	$(".registertime").html(formatDate(data.registertime))
	$(".createtime").html(formatDate(data.createtime))
	$(".frontUserBalance").html((data.frontUserBalance+data.frontUserBalanceLock).toFixed(2))
	$(".frontUserBalanceLock").html(data.frontUserBalanceLock.toFixed(2))
	
	$(".frontUserShowId").html(data.frontUserShowId)
	$(".ip").html(data.ip)
	$(".status").html(statusObj[data.status])
	$(".totalRecharge").html(data.totalRecharge.toFixed(2))
	$(".totalWithdraw").html(data.totalWithdraw.toFixed(2))
	
	$(".frontUserNickname").html(data.frontUserNickname)
	$(".frontUserLevel").html(levelObj[data.frontUserLevel])
	$(".scoreBalance").html(data.scoreBalance)
	
	if(registerDay<7){
		$(".registertime").parent().addClass("color-red")
	}
	if(data.totalPayment<data.totalWithdraw*1.5){
		$(".totalWithdraw").parent().addClass("color-red")
	}
	if(data.totalRecharge<data.totalWithdraw+data.frontUserBalance+data.totalDelivery){
		$(".totalRecharge").parent().addClass("color-red")
	}
	if(data.totalPayment<100){
		$(".totalPayment").parent().addClass("color-red")
	}
	$(".lay-nopass").click(function(){
		layer.prompt({
			title: '审核不通过',
			formType: 0,
			btn: ['确定','取消'],
			yes: function(value, index, elem){
				processSetting(dataUuid,'nopass',$(".layui-layer-input").val());
			},
			btn2: function(value, index, elem){
//				processSetting(dataUuid,'nopass',$(".layui-layer-input").val());
			}
		});
		return false;
	})
	$(".lay-submit").click(function(){
		layer.prompt({
			title: '审核通过',
			formType: 0,
			btn: ['确定','取消'],
			yes: function(value, index, elem){
				processSetting(dataUuid,'checked',$(".layui-layer-input").val());
			},
			btn2: function(value, index, elem){
//				processSetting(dataUuid,'nopass',$(".layui-layer-input").val());
			}
		});
		return false;
	})
})
function processSetting(uuid,status,remark){
	$.ajax({
        url: '../back/userWithdraw/finalCheck',
        type: 'post',
        data: {
        	'uuid': uuid,
        	'status': status,
        	'remark': remark
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			layer.msg('处理成功', {
    				time: 1000 
    			}, function(){
    				var index = parent.layer.getFrameIndex(window.name);
    				window.parent.getList(size,num);
//    				parent.location.reload();
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
}
layui.use(['form', 'element'], function(){
	var form = layui.form
	,layer = layui.layer
	,$ = layui.jquery
	,element = layui.element;
})