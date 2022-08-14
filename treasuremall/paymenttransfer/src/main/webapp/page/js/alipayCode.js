/**
 * 
 */
var token = (url('?token') != null) ? url('?token') : '';
var amount = '';
$(function(){
	var index = layer.load(1, {
	  shade: [0.1,'#fff'] 
	});
	$(".first-box").css("min-height",document.body.clientHeight)
	aliCodeGet();
})
function getUrl(e,param){
    analyticCode.getUrl(param,e,function(url1,url2){
    	if(url1.indexOf('http')>-1){
    		window.localStorage.setItem("url",url1)
    		window.location.href = 'alipayCodeSec.html?token='+token+'&amount='+amount
    	}else{
    		layer.msg("唤起支付宝失败，请采用二维码付款")
    	}
    });
}
$(".first-box button").click(function(){
	$(".hiddenImg").click()
})
//获取支付信息
function aliCodeGet(){
	$.ajax({
        url: '../transfer/recharge/aliCodeGet',
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
			amount = ((r.data.totalAmount)/100).toFixed(2)
			$(".timeout").html(formatDates(r.data.timeout))
			$(".img-code img,.hiddenImg").attr("src","data:image/png;base64,"+r.data.code)
			window.localStorage.setItem("returnUrl",r.data.returnUrl)
			$(".fixed-box .cancle-btn").click(function(){
				if(r.data.returnUrl==""){
					window.history.go(-1)
				}else{
					window.location.href = r.data.returnUrl
				}
			})
			$(".fixed-box .sure-btn").click(function(){
				window.location.href = 'alipayCodeSec.html?token='+token+'&amount='+amount
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

