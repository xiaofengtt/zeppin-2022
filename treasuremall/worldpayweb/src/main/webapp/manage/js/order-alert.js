/**
 * 
 */
document.writeln("			<script type='text/javascript' src='js/spop.min.js'></script>");
document.writeln("			<link rel='stylesheet' href='css/spop.min.css' />");
document.writeln("			<div id='messageAudio' style='display:none'	></div>");
$(function(){
	orderAlert()
	window.setInterval(orderAlert, 1000 * 20);
})

function orderAlert() {
	$.ajax({
        url: '../system/admin/getNotice',
        type: 'get',
        async:false,
        data:{}
    }).done(function(r) {
    	if (r.status == "SUCCESS") {
			if(r.data.recharge != null){
				spop({
					template: '<a href="userRechargeList.html">There are recharge orders waiting to be approved！</a>',
					group: 'recharge',
					position  : 'bottom-right',
					style: 'default'
				});
				audioPlay()
			}
			if(r.data.withdraw != null){
				spop({
					template: '<a href="userWithdrawList.html">There are withdrawal orders waiting to be approved！</a>',
					group: 'withdraw',
					position  : 'bottom-right',
					style: 'default'
				});
				audioPlay()
			}
		}
    })
}


function audioPlay(){
	var borswer = window.navigator.userAgent.toLowerCase();
	if(borswer.indexOf( "ie" ) >= 0){ 
		$('#messageAudio').html('<embed src="media/alert.mp3"/>'); 
	}else{ 
		$('#messageAudio').html('<audio autoplay="autoplay"><source src="media/alert.mp3" type="audio/mpeg"/></audio>'); 
	}
}