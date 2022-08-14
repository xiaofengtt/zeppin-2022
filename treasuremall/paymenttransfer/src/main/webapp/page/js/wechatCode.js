/**
 * 
 */
var token = (url('?token') != null) ? url('?token') : '';
$(function(){
	var index = layer.load(1, {
	  shade: [0.1,'#fff'] 
	});
	$(".first-box").css("min-height",document.body.clientHeight)
	aliCodeGet();
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
function getUrl(e,param){
    analyticCode.getUrl(param,e,function(url1,url2){
    	if(url1.indexOf('http')>-1){
    		window.top.location.href = url1
    	}else{
    		layer.msg("唤起微信失败，请采用二维码付款")
    	}
    });
}
$(".first-box button").click(function(){
	$(".hiddenImg").click()
})
$(".button-box .sure-btn").click(function(){
	var orderid = $(".orderNumber").val().replace(/(^\s*)|(\s*$)/g, "")
	if(orderid==""){
		layer.msg("请输入帐单号")
		return false
	}else{
		if(orderid.length<5){
			layer.msg("帐单号过短")
			return false
		}else{
			$.ajax({
		        url: '../transfer/recharge/wechatCodeFinish',
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
							$(".fixed-box .cancle-btn").click()
						});
				}else{
					layer.msg(r.message);
				}
		    })
		}
	}
})
//获取支付信息
function aliCodeGet(){
	$.ajax({
        url: '../transfer/recharge/wechatCodeGet',
        type: 'post',
        async:false,
        data: {
        	'token':token
        }
    }).done(function(r) {
    	layer.closeAll();
    	$(".wrap").show()
		if (r.status == "SUCCESS") {
			$(".amount").html(((r.data.totalAmount)/100).toFixed(2))
			$(".timeout").html(formatDates(r.data.timeout))
			$(".img-code img,.hiddenImg").attr("src","data:image/png;base64,"+r.data.code)
			$(".fixed-box .cancle-btn").click(function(){
				if(r.data.returnUrl==""){
					window.history.go(-1)
				}else{
					window.location.href = r.data.returnUrl
				}
			})
			$(".fixed-box .sure-btn").click(function(){
				$(".first-box").hide()
				$(".second-box").show()
				document.body.scrollTop = document.documentElement.scrollTop = 0;
			})
			$(".button-box .cancle-btn").click(function(){
				document.body.scrollTop = document.documentElement.scrollTop = 0;
				$(".first-box").show()
				$(".second-box").hide()
			})
		}else{
			layer.msg(r.message);
			$(".fixed-box .cancle-btn").click(function(){
				window.history.go(-1)
			})
		}
    })
}
//时间戳转化--只要时分
function formatDates(now) {
	var now = new Date(now);
	var hour="0"+now.getHours();
	var minute="0"+now.getMinutes();
	var second="0"+now.getSeconds();
	return hour.substring(hour.length-2,hour.length)+
	":"+minute.substring(minute.length-2,minute.length);	
} 

