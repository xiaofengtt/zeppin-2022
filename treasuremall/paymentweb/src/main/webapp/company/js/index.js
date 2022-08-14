/**
 * 商户首页
 */
var pageNum = 1,
    pageSize = 50;
var statusObj = {
	'normal':'正常',
	'disable':'停用'
}
$(function(){
	getCompany();
	$("#dailyChart").height(400)
});
layui.use(['table', 'laydate', 'layer' ,'element'], function(){
	var form = layui.form
		,layer = layui.layer
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
	
	var now = new Date().getTime();
	var before = now - (10 * 3600 * 24 * 1000);
	var yesterday = now - (3600 * 24 * 1000);
	var dateStr = formatToDate(before) + ' - ' + formatToDate(yesterday);
	
	getList(dateStr)
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
			$('input[name=balance]').val((r.data.balance/100).toFixed(2));
			$('input[name=balanceLock]').val((r.data.balanceLock/100).toFixed(2));
			if($('#hideImg').attr('src') == 'img/money-hide.png'){
				$('#balance').html('******');
				$('#balanceLock').html('******');
			}else{
				$('#balance').html($('input[name=balance]').val().replace(/(\d)(?=(\d{3})+\.)/g, '$1,')+'元');
				$('#balanceLock').html($('input[name=balanceLock]').val().replace(/(\d)(?=(\d{3})+\.)/g, '$1,')+'元');
			}
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

function getList(dateStr){
	var datas = {}
	if(dateStr != null){
		var ctimes = dateStr.split(" - ");
		datas['starttime'] = ctimes[0];
		datas['endtime'] = ctimes[1];
	}
	$.ajax({
        url: '../store/statisticsCompany/dailyList',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">日期</th>'+
				'<th width="100px" class="text-center">注资总额</th>'+
				'<th width="100px" class="text-center">结算总额</th>'+
				'<th width="100px" class="text-center">充值总额</th>'+
				'<th width="100px" class="text-center">提现总额</th>'+
				'<th width="100px" class="text-center">总手续费</th>'+
				'</tr>';
			for(var i=0;i<r.data.dailyDate.length;i++){
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data.dailyDate[i]+'</td>'
					+'<td class="text-center">'+r.data.company_recharge_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.company_withdraw_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.user_recharge_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.user_withdraw_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data.company_recharge_poundage[i]+r.data.company_withdraw_poundage[i]+r.data.user_recharge_poundage[i]+r.data.user_withdraw_poundage[i]).toFixed(2)+'</td>'
					+'</tr>'; 
			}
			if(r.data.length==0){
				tableHtml += '<tr><td colspan="6" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			
			var poundageList = [];
			for(var i=0 ; i<r.data.dailyDate.length; i++){
				var poundage = r.data.company_recharge_poundage[i] + r.data.company_withdraw_poundage[i] + r.data.user_recharge_poundage[i] +r.data.user_withdraw_poundage[i]
				poundageList.push(poundage);
			}
			
			
			var myChart = echarts.init(document.getElementById('dailyChart'));
	        var option = {
	            title: {
	                text: '十日财务报表'
	            },
	            tooltip: {
        	        trigger: 'axis'
        	    },
	            legend: {
	                data:['注资总额','结算总额','充值总额','提现总额','总手续费']
	            },
        	    grid: {
        	        left: '3%',
        	        right: '4%',
        	        bottom: '3%',
        	        containLabel: true
        	    },
        	    toolbox: {
        	        feature: {
        	            saveAsImage: {}
        	        }
        	    },
	            xAxis: {
	                data: r.data.dailyDate
	            },
	            yAxis: {},
	            series: [
		            {
		                name: '注资总额',
		                type: 'line',
		                data: r.data.company_recharge_amount,
		                smooth: true
		            },{
		                name: '结算总额',
		                type: 'line',
		                data: r.data.company_withdraw_amount,
		                smooth: true
		            },{
		                name: '充值总额',
		                type: 'line',
		                data: r.data.user_recharge_amount,
		                smooth: true
		            },{
		                name: '提现总额',
		                type: 'line',
		                data: r.data.user_withdraw_amount,
		                smooth: true
		            },{
		                name: '总手续费',
		                type: 'line',
		                data: poundageList,
		                smooth: true
		            }
	            ]
	        };
	        myChart.setOption(option);
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

function hideMoney(obj){
	if($(obj).find('#hideImg').attr('src') == 'img/money-hide.png'){
		$(obj).find('#hideImg').attr('src','img/money-show.png');
		$('#balance').html($('input[name=balance]').val().replace(/(\d)(?=(\d{3})+\.)/g, '$1,')+'元');
	}else{
		$(obj).find('#hideImg').attr('src','img/money-hide.png')
		$('#balance').html('******');
	}
}
function hideMoneys(obj){
	if($(obj).find('#hideImg').attr('src') == 'img/money-hide.png'){
		$(obj).find('#hideImg').attr('src','img/money-show.png');
		$('#balanceLock').html($('input[name=balanceLock]').val().replace(/(\d)(?=(\d{3})+\.)/g, '$1,')+'元');
	}else{
		$(obj).find('#hideImg').attr('src','img/money-hide.png')
		$('#balanceLock').html('******');
	}
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
		area: ['800px', '550px'],
		content:['tradeWithdraw.html']
	}); 
})

$('.rechargeBtn').click(function(){
	layer.open({
		type: 2, 
		title:false,
		area: ['800px', '550px'],
		content:['tradeRecharge.html']
	}); 
})
