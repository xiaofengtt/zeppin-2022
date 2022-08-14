/**
 * 
 */
$(function(){
	$("#financeDaily,#statistics").height(document.documentElement.clientHeight-200)
	$(".lay-export").click(function(){
		exportList();
	});
	$(".lay-export-user").click(function(){
		exportUserList();
	});
	$(".financeDailyBox .worktimeBegin").val(formatDateBefore(new Date(),'7'))
	$(".financeDailyBox .worktimeEnd").val(formatDateBefore(new Date(),'0'))
	$(".statisticsBox .timeBegin").val(formatDateBefore(new Date(),'7'))
	$(".statisticsBox .timeEnd").val(formatDateBefore(new Date(),'0'))
})
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,$ = layui.jquery
	  ,upload = layui.upload
	  ,element = layui.element;	  
	  //日期
	  laydate.render({
	    elem: '.financeDailyBox .worktimeBegin'
	    ,value: formatDateBefore(new Date(),'7')
	    ,max:formatDateBefore(new Date(),'0')
	    ,type: 'date'
		,theme: '#3D99FF'
	  });
	  laydate.render({
	    elem: '.financeDailyBox .worktimeEnd'
	    ,value: formatDateBefore(new Date(),'0')
	    ,max:formatDateBefore(new Date(),'0')
	    ,type: 'date'
		,theme: '#3D99FF'
	  });
	  laydate.render({
	    elem: '.statisticsBox .timeBegin'
	    ,value: formatDateBefore(new Date(),'7')
	    ,max:formatDateBefore(new Date(),'0')
	    ,type: 'date'
		,theme: '#3D99FF'
	  });
	  laydate.render({
	    elem: '.statisticsBox .timeEnd'
	    ,value: formatDateBefore(new Date(),'0')
	    ,max:formatDateBefore(new Date(),'0')
	    ,type: 'date'
		,theme: '#3D99FF'
	  });
	  getListDaily()
	  getList()
});
$(".financeDailyBox .lay-submit").click(function(){	
	getListDaily();
	return false;
});
$(".statisticsBox .lay-submit").click(function(){	
	getList();
	return false;
});
function getListDaily(){
	$.ajax({
        url: '../back/statisticsFinanceDaily/list',
        type: 'get',
        async:false,
        data: $(".financeDailyBox form").serializeObject()
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var dailyDate = [],dPayment=[],delivery=[],recharge=[],winning=[],withdraw=[],totalPay=[];
			for(var i=0;i<r.data.length;i++){
				dailyDate.push(r.data[i].dailyDate) // 日期
				dPayment.push(r.data[i].dPayment) // 消耗
				delivery.push(r.data[i].delivery) // 总派奖金额
				totalPay.push(r.data[i].delivery+r.data[i].withdraw) //总支出=总提现+总派奖金额（实物领奖）
				recharge.push(r.data[i].recharge) //充值
				winning.push(r.data[i].winning) // 中奖
				withdraw.push(r.data[i].withdraw) //提现
			}
			var myChart = echarts.init(document.getElementById('financeDaily'));
	        var option = {
	            title: {
	                text: '消耗/充值/提现/中奖/总支出-趋势图'
	            },
	            tooltip: {
        	        trigger: 'axis'
        	    },
	            legend: {
	                data:['消耗/金币','充值/美元','提现/美元','中奖/美元','总支出/美元','实物领取/美元'],
	                top:'30'
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
	                data: dailyDate
	            },
	            yAxis: {},
	            series: [
		            {
		                name: '消耗/金币',// name应与legend一致
		                type: 'line',
		                data: dPayment,
		                smooth: true
		            },{
		                name: '充值/美元',
		                type: 'line',
		                data: recharge,
		                smooth: true
		            },{
		                name: '提现/美元',
		                type: 'line',
		                data: withdraw,
		                smooth: true
		            },{
		                name: '中奖/美元',
		                type: 'line',
		                data: winning,
		                smooth: true
		            },{
		                name: '总支出/美元',
		                type: 'line',
		                data: totalPay,
		                smooth: true
		            },{
		                name: '实物领取/美元',
		                type: 'line',
		                data: delivery,
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
        	
        });
    });   	
}
function getList(){
	$.ajax({
        url: '../back/userDaily/list',
        type: 'get',
        async:false,
        data: $(".statisticsBox form").serializeObject()
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var dailyDate = [],regist=[],first=[],active=[];
			for(var i=0;i<r.data.length;i++){
				dailyDate.push(r.data[i].dailyDate)
				regist.push(r.data[i].regist)
				first.push(r.data[i].first)
				active.push(r.data[i].active)
			}
			var myChart = echarts.init(document.getElementById('statistics'));
	        var option = {
	            title: {
	                text: '注册/首充/日活人数-趋势图'
	            },
	            tooltip: {
        	        trigger: 'axis'
        	    },
	            legend: {
	                data:['注册人数','首充人数','日活人数'],
	                top:'30'
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
	                data: dailyDate
	            },
	            yAxis: {},
	            series: [
		            {
		                name: '注册人数',// name应与legend一致
		                type: 'line',
		                data: regist,
		                smooth: true
		            },{
		                name: '首充人数',
		                type: 'line',
		                data: first,
		                smooth: true
		            },{
		                name: '日活人数',
		                type: 'line',
		                data: active,
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
        	
        });
    });   	
}
function exportList(){
	var layerIndex = layer.open({
		type: 3
	});
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href = "../back/statisticsFinanceDaily/export?"+$(".financeDailyBox form").serialize();
	link.click();
	return false;
}
function exportUserList(){
	var layerIndex = layer.open({
		type: 3
	});
	if(flagSubmit == false) {
		return false;
	}
	flagSubmit = false;
	setTimeout(function() {
		flagSubmit = true;
	}, 3000);
	var link = document.createElement('a');
	link.setAttribute("download", "");
	link.href = "../back/userDaily/export?"+$(".statisticsBox form").serialize();
	link.click();
	return false;
}