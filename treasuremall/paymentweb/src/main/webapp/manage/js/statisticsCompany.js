/**
 * 商户财务统计
 */
var statusObj = {
	'normal':'正常',
	'disable':'停用'
}
$(function(){
	$("#dailyChart").height(500)
});
layui.use(['table', 'layer','laydate','element'], function(){
	var form = layui.form
		,layer = layui.layer
		,laydate = layui.laydate
		,$ = layui.jquery
		,element = layui.element;

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

function getList(dateStr){
	var datas = {}
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
        url: '../system/statisticsCompany/companyList',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var tableHtml = '<tr>'+
				'<th width="100px" class="text-left">商户ID</th>'+
				'<th width="100px" class="text-center">商户名称</th>'+
				'<th width="100px" class="text-center">商户状态</th>'+
				'<th width="100px" class="text-center">注资总额</th>'+
				'<th width="100px" class="text-center">充值总额</th>'+
				'<th width="100px" class="text-center">代付总额</th>'+
				'<th width="100px" class="text-center">结算总额</th>'+
				'<th width="100px" class="text-center">总手续费</th>'+
				'<th width="100px" class="text-center">开户日期</th>'+
				'</tr>';
			for(var i=0;i<r.data.length;i++){
				tableHtml += '<tr class="data-row" data-url="statisticsCompanyDaily.html?uuid='+r.data[i].uuid+'">'
					+'<td class="text-left">'+r.data[i].code+'</td>'
					+'<td class="text-center">'+r.data[i].name+'</td>'
					+'<td class="text-center">'+statusObj[r.data[i].status]+'</td>'
					+'<td class="text-center">'+r.data[i].company_recharge_amount.toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].user_recharge_amount.toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].user_withdraw_amount.toFixed(2)+'</td>'
					+'<td class="text-center">'+r.data[i].company_withdraw_amount.toFixed(2)+'</td>'
					+'<td class="text-center">'+(r.data[i].company_recharge_poundage+r.data[i].company_withdraw_poundage+r.data[i].user_recharge_poundage+r.data[i].user_withdraw_poundage).toFixed(2)+'</td>'
					+'<td class="text-center">'+formatToDate(r.data[i].createtime)+'</td>'
					+'</tr>'; 
			}
			if(r.data.length==0){
				tableHtml += '<tr><td colspan="9" align="center">暂无相关数据</td></tr>';
			}
			$("table").html(tableHtml);
			
			$("tr.data-row").click(function(){
				location.href=$(this).attr('data-url');
			})
			
			var companyList = [];
			var companyRechargeList = [];
			var userRechargeList = [];
			var userWithdrawList = [];
			var companyWithdrawList = [];
			var poundageList = [];
			for(var i=0 ; i<r.data.length; i++){
				var poundage = r.data[i].company_recharge_poundage + r.data[i].company_withdraw_poundage + r.data[i].user_recharge_poundage +r.data[i].user_withdraw_poundage
				companyList.push(r.data[i].name);
				companyRechargeList.push(r.data[i].company_recharge_amount);
				userRechargeList.push(r.data[i].user_recharge_amount);
				userWithdrawList.push(r.data[i].user_withdraw_amount);
				companyWithdrawList.push(r.data[i].company_withdraw_amount);
				poundageList.push(poundage);
			}
			var myChart = echarts.init(document.getElementById('dailyChart'));
			option = {
				title: {
	                text: '商户财务报表'
	            },
			    color: ['#003366', '#006699', '#e54e4e', '#e10000', '#15aa3e'],
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'shadow'
			        }
			    },
			    legend: {
			        data: ['注资总额', '充值总额', '代付总额', '结算总额', '总手续费']
			    },
			    toolbox: {
        	        feature: {
        	            saveAsImage: {}
        	        }
        	    },
			    xAxis: [
			        {
			            type: 'category',
			            axisTick: {show: false},
			            data: companyList
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value'
			        }
			    ],
			    series: [
			        {
			            name: '注资总额',
			            type: 'bar',
			            barGap: 0,
			            data: companyRechargeList
			        },
			        {
			            name: '充值总额',
			            type: 'bar',
			            data: userRechargeList
			        },
			        {
			            name: '代付总额',
			            type: 'bar',
			            data: userWithdrawList
			        },
			        {
			            name: '结算总额',
			            type: 'bar',
			            data: companyWithdrawList
			        },
			        {
			            name: '总手续费',
			            type: 'bar',
			            data: poundageList
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
	window.open('../system/statisticsCompany/companyExport?starttime='+starttime+'&endtime='+endtime);
}