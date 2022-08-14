$(function(){
	$("#dailyChart").height(400)
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
		
	laydate.render({
		elem: '.createtime'
		,value: dateStr
		,max: -1
		,type: 'date'
		,range: '-'
		,lang: 'en'
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
        url: '../system/statisticsCompany/dailyList',
        type: 'get',
        async:false,
        data: datas
    }).done(function(r) {
		if (r.status == "SUCCESS") {
			var titleArray = [];
			var tableHtml = '<tr><th width="100px" class="text-left">Date</th>';
			for(var key in r.data){
				if('dailyDate' != key){
					tableHtml += '<th width="100px" class="text-center">'+key+'</th>';
					titleArray.push(key);
				}
			}
			tableHtml += '</tr>';
			for(var i=0;i<r.data.dailyDate.length;i++){
				tableHtml += '<tr>'
					+'<td class="text-left">'+r.data.dailyDate[i]+'</td>'
					for(var k in titleArray){
						tableHtml += '<td class="text-center">'+r.data[titleArray[k]][i].toFixed(2)+'</td>'
					}
				tableHtml += '</tr>'; 
			}
			if(r.data.length==0){
				tableHtml += '<tr><td colspan="10" align="center">No relevant data</td></tr>';
			}
			$("table").html(tableHtml);
			
			var myChart = echarts.init(document.getElementById('dailyChart'));
			
			var seriesArray = []
			for(var k in titleArray){
				var seriesData = {
						name: titleArray[k],
		                type: 'line',
		                data: r.data[titleArray[k]],
		                smooth: true
				}
				seriesArray.push(seriesData);
			}
			var option = {
		            title: {
		                text: 'Financial Chart'
		            },
		            tooltip: {
	        	        trigger: 'axis'
	        	    },
		            legend: {
		                data: titleArray
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
		            series: seriesArray
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