/**
 * 
 */
var token = (url('?token') != null) ? url('?token') : '';
var amount = (url('?amount') != null) ? url('?amount') : '';
$(function(){
	var index = layer.load(1, {
	  shade: [0.1,'#fff'] 
	});
	layer.closeAll();
	$(".wrap").show()
	$(".amount").html(amount)
	$(".button-box .cancle-btn").click(function(){
		window.location.href = 'alipayCode.html?token='+token
	})
	$(".orderNumber").bind('input porpertychange',function(){
		if($(this).val().replace(/(^\s*)|(\s*$)/g, "")!=""){
			$(".order-clear").show()
		}else{
			$(".order-clear").hide()
		}
	});
	$(".order-clear").click(function(){
		$(".orderNumber").val("")
		$(".order-clear").hide()
	})
})
window.onload=function(){
	if(window.localStorage.getItem("url")){
		var url = window.localStorage.getItem("url")
		window.localStorage.removeItem("url")
		setTimeout(function(){ 
			window.location.href = url
		}, 10);
	}
}
$(".button-box .sure-btn").click(function(){
	var orderid = $(".orderNumber").val().replace(/(^\s*)|(\s*$)/g, "")
	if(orderid==""){
		layer.msg("请输入订单号")
		return false
	}else{
		if(orderid.length<5){
			layer.msg("订单号过短")
			return false
		}else{
			$.ajax({
		        url: '../transfer/recharge/aliCodeFinish',
		        type: 'post',
		        async:false,
		        data: {
		        	'token':token,
		        	'orderid':orderid
		        }
		    }).done(function(r) {
				if (r.status == "SUCCESS") {
					layer.confirm('支付成功，审核人员正在核对订单，预计15分钟内到账', {
						  btn: ['我知道了'],
						  closeBtn: 0,
						  title:false
						}, function(){
							if(window.localStorage.getItem("returnUrl")==""){
								window.history.go(-2)
							}else{
								window.location.href = window.localStorage.getItem("returnUrl")
							}
							window.localStorage.removeItem("returnUrl")
						});
				}else{
					layer.msg(r.message);
				}
		    })
		}
	}
})

