/**
 * 
 */
var orderid = (url('?orderid') != null) ? url('?orderid') : '';

$(function(){
	load();
})

function load(){
	layer.load(1, {
	  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	$.ajax({
        url: '../support/stripe/load',
        type: 'get',
        async:false,
        data: {
        	'orderid':orderid
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var stripe = Stripe(r.data.publicKey);
			stripe.redirectToCheckout({
		      sessionId: r.data.sessionUuid,
		    })
		    layer.closeAll()
		}else{
			layer.msg(r.message);
		}
    })
}