/**
 * 商户财务统计
 */
var company = (url('?uuid') != null) ? url('?uuid') : '';
$(function(){
	$("#dailyChart").height(400)
});
layui.use(['table', 'layer','laydate','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;
	
	getCompany()
	
	var now = new Date().getTime();
	var before = now - (10 * 3600 * 24 * 1000);
	var yesterday = now - (3600 * 24 * 1000);
	var dateStr = formatToDate(before) + ' - ' + formatToDate(yesterday);
		
	//日期
	laydate.render({
		elem: '.createtime'
		,value: dateStr
		,max: -1
		,type: 'date'
		,range: '-'
		,done: function(value, date){
			getList();
		}
	});
	
	getList(dateStr)
});
function getCompany(){
	$.ajax({
        url: '../system/company/get',
        type: 'get',
        async:false,
        data: {
        		'uuid': company
        	}
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			$('#code').html(r.data.code);
			$('#name').html(r.data.name);
		} else {
			if(r.errorCode=="302"){
				layer.msg(r.message, {
		            time: 2000
		        },function(){
		        	parent.location.href="login.html";
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
	datas['company'] = company;
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('input[name=createtime]').val().split(" - ");
		datas['starttime'] = ctimes[0];
		datas['endtime'] = ctimes[1];
	}
	if(dateStr != null){
		var ctimes = dateStr.split(" - ");
		datas['starttime'] = ctimes[0];
		datas['endtime'] = ctimes[1];
	}
	$.ajax({
        url: '../system/statisticsCompany/dailyList',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">日期</th>'+
				'<th width="100px" class="text-center">注资总额</th>'+
				'<th width="100px" class="text-center">注资手续费</th>'+
				'<th width="100px" class="text-center">结算总额</th>'+
				'<th width="100px" class="text-center">结算手续费</th>'+
				'<th width="100px" class="text-center">充值总额</th>'+
				'<th width="100px" class="text-center">充值手续费</th>'+
				'<th width="100px" class="text-center">提现总额</th>'+
				'<th width="100px" class="text-center">提现手续费</th>'+
				'<th width="100px" class="text-center">总手续费</th>'+
				'</tr>';
			for(var i=0;i<r.data.dailyDate.length;i++){
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data.dailyDate[i]+'</td>'
					+'<td class="text-center">'+r.data.company_recharge_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.company_recharge_poundage[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.company_withdraw_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.company_withdraw_poundage[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.user_recharge_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.user_recharge_poundage[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.user_withdraw_amount[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data.user_withdraw_poundage[i].toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data.company_recharge_poundage[i]+r.data.company_withdraw_poundage[i]+r.data.user_recharge_poundage[i]+r.data.user_withdraw_poundage[i]).toFixed(2)+'</td>'
					+'</tr>'; 
			}
			if(r.data.length==0){
				tableHtml += '<tr><td colspan="10" align="center">暂无相关数据</td></tr>';
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
function exportFile(){
	var starttime = '';
	var endtime ='';
	if($('input[name=createtime]').val() != ''){
		var ctimes = $('input[name=createtime]').val().split(" - ");
		starttime = ctimes[0];
		endtime = ctimes[1];
	}
	window.open('../system/statisticsCompany/dailyExport?starttime='+starttime+'&endtime='+endtime);
}