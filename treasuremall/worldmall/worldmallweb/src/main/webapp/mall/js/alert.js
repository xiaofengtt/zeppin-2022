/**
 * 
 */
$(function(){
	orderAlert()
	alertFuc = window.setInterval(orderAlert, 1000 * 20);
});

//声音提示
document.writeln("			<script type='text/javascript' src='js/spop.min.js'></script>");
document.writeln("			<link rel='stylesheet' href='css/spop.min.css' />");
document.writeln("			<div id='messageAudio' style='display:none'	></div>");

function orderAlert() {
	$.ajax({
        url: '../back/admin/getNotice',
        type: 'get',
        async:false,
        data:{}
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
			if(r.data.recharge != null){
				spop({
					template: '<a href="userRechargeCheckList.html">有充值订单正等待审核！</a>',
					group: 'recharge',
					position  : 'bottom-right',
					style: 'default'
				});
				audioPlay()
			}
			if(r.data.withdraw != null){
				spop({
					template: '<a href="userWithdrawCheckList.html">有提现订单正等待审核！</a>',
					group: 'withdraw',
					position  : 'bottom-right',
					style: 'default'
				});
				audioPlay()
			}
		}else{
			window.clearInterval(alertFuc);
		}
    })
}


function audioPlay(){
	var borswer = window.navigator.userAgent.toLowerCase();
	if(borswer.indexOf( "ie" ) >= 0){ 
		$('#messageAudio').html('<embed src="media/alert.mp3"/>'); 
	}else{ 
		//IE9+,Firefox,Chrome均支持<audio/> 
		$('#messageAudio').html('<audio autoplay="autoplay"><source src="media/alert.mp3" type="audio/mpeg"/></audio>'); 
	}
}