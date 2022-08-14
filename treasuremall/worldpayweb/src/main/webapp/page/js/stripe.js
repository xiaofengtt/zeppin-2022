/**
 * 
 */
var orderid = (url('?orderid') != null) ? url('?orderid') : '';

$(function(){
	load();
})

function load(){
	$.ajax({
        url: '../support/stripe/load',
        type: 'get',
        async:false,
        data: {
        	'orderid':orderid
        }
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$("#formDiv").html(r.data.form);
			$("#orderNum").html(r.data.orderNum);
			$("#title").html(r.data.title);
			$("#amount").html(r.data.totalAmount);
			$("#currency").html(r.data.currency);
			$(".goback img").click(function(){
				if(r.data.returnUrl==""){
					window.history.go(-1)
				}else{
					window.location.href = r.data.returnUrl
				}
			})
		}else{
			layer.msg(r.message);
			$(".goback img").click(function(){
				window.history.go(-1)
			})
		}
    })
}