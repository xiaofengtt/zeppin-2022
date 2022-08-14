/**
 * 
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'normal',
	'disable':'disable'
}
$(function(){
	getCompany();
});
layui.use(['table', 'laydate', 'layer' ,'element'], function(){
	var form = layui.form
		,layer = layui.layer
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
});

function getCompany(){
	$.ajax({
        url: '../store/company/get',
        type: 'get',
        async:false,
        data: {}
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$('#name').html(r.data.name);
			$('#code').html(r.data.code);
			$('#status').html(statusObj[r.data.status]);
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
        });
    });   	
}

$('.editBtn').click(function(){
	layer.open({
		type: 2, 
		title:false,
		area: ['800px', '350px'],
		content:['companyParamsDetail.html']
	}); 
})

$('.withdrawBtn').click(function(){
	layer.open({
		type: 2, 
		title:false,
		area: ['800px', '600px'],
		content:['tradeWithdraw.html']
	}); 
})

$('.rechargeBtn').click(function(){
	layer.open({
		type: 2, 
		title:false,
		area: ['800px', '600px'],
		content:['tradeRecharge.html']
	}); 
})
