$(function(){
	$(".btnDiv a").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	getYearInfo("1");
	$("#endTime").change(function(){
		judgeYear("#endTime");
	});
	$("#startTime").change(function(){
		judgeYear("#startTime");
	});
	$(".loadingDiv").css({"width":$(this).parent().width()+30+"px"});
});
$(document).ready(function(){
	$(".loadingDiv").css({"width":$(".loadingDiv").parent().parent().width()+30+"px","height":$(".loadingDiv").parent().parent().height()+"px"});
});

var offsetHeightYear;
var offsetHeightLevel;
var offsetHeightSubject;
var offsetHeightGrade;
var offsetHeightOrganization;
$(window).scroll(function() {	
	var width=$(".main").width()-1;
    if(offsetHeightYear<$(window).scrollTop()&&$(window).scrollTop()<offsetHeightYear+$(".year").parent().parent().height()){
    	$(".yearFix").parent().parent().css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".yearFix th:first").css({"width":width*0.15+"px"});
    }else{
    	$(".yearFix").parent().parent().css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightLevel<$(window).scrollTop()&&$(window).scrollTop()<offsetHeightLevel+$(".level").parent().parent().height()){
    	$(".levelFix").parent().parent().css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".levelFix th:first").css({"width":width*0.25+"px"});
    }else{
    	$(".levelFix").parent().parent().css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightSubject<$(window).scrollTop()&&$(window).scrollTop()<offsetHeightSubject+$(".subject").parent().parent().height()){
    	$(".subjectFix").parent().parent().css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".subjectFix th:first").css({"width":width*0.15+"px"});
    }else{
    	$(".subjectFix").parent().parent().css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightGrade<$(window).scrollTop()&&$(window).scrollTop()<offsetHeightGrade+$(".grade").parent().parent().height()){
    	$(".gradeFix").parent().parent().css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".gradeFix th:first").css({"width":width*0.25+"px"});
    }else{
    	$(".gradeFix").parent().parent().css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightOrganization<$(window).scrollTop()&&$(window).scrollTop()<offsetHeightOrganization+$(".organization").parent().parent().height()){
    	$(".organizationFix").parent().parent().css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".organizationFix th:first").css({"width":width*0.25+"px"});
    }else{
    	$(".organizationFix").parent().parent().css({"position":"static","width":width,"display":"none"});
    }
});

var tArrayarea=[];//地区数组
var tArrayyear=[];//年份数值
var equipmentHeight=$(window).height();
//分年统计
function getYearInfo(number){
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	if(judgeYear("#endTime")){
		if(number=="1"){
			$(".loadingDiv").css("display","block");
			$(".contentsDiv").css("display","none");
		}else{
			$(".loadingDiv").css("display","block");
			$(".div").css("display","none");
		}
		
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getYearCount.action",
	        data: {startYear:startYear,endYear:endYear},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table='<tr class="year"><th width="15%">年份</th><th width="20%">总教师人数</th><th width="20%">总参训人数</th>'+ 
	        	'<th width="15%">参训覆盖率</th><th width="15%">总结业人次</th><th width="15%">参训结业率</th></tr>';
	        	var tableFix='<tr class="yearFix"><th width="15%">年份</th><th width="20%">总教师人数</th><th width="20%">总参训人数</th>'+ 
	        	'<th width="15%">参训覆盖率</th><th width="15%">总结业人次</th><th width="15%">参训结业率</th></tr>';
	        	$(".tableThead").html(tableFix).css("display","none");
	        	if(json.status=="OK"){
	        		$(".contentsDiv").css("display","block");
	        		$(".loadingDiv").css("display","none");
	        		$(".div").css("display","block");
	        		if(number=="1"){
	        			$(".btnDiv a:eq(0)").addClass("light").siblings().removeClass("light");
	        		}
	        		tArrayarea=[];
	        		tArrayyear=[];
	        		$(".dv-table table").html("");
	        		$(".totalTr").html("");
	        		$(".number span.total").html(formatNum(json.totalTraining));
	        		$(".number span.passengers").html(formatNum(json.totalTrainingTeacher));
	        		$(".number span.proportion").html(formatNum(json.totalTrainingComplete));
	        		$(".number span.coverage").html(json.totalTrainingTeacherRate);
	        		for (var i = 0; i < json.records.length; i++) {
	        			table+='<tr><td width="15%">'+json.records[i].year+'</td><td width="20%">'+formatNum(json.records[i].teacherCount)+
	        			'</td><td width="20%">'+formatNum(json.records[i].trainingTeacherCount)+'</td><td width="15%">'
	        			+json.records[i].trainingTeacherRate+'%</td><td width="15%">'+formatNum(json.records[i].trainingCompleteCount)+
	        			'</td><td width="15%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
	        			tArrayarea[i]=json.records[i].year;
	        			tArrayyear[i]=json.records[i].trainingCount;
	        		}
	        		
	        	}
	        	$(".dv-table table").html(table);
	        	$(".totalTr").html('');
	        	$(".pieChart .title").html("参训人次曲线图");
	        	$("#containerline").height("433px");
	        	if(equipmentHeight<$(".tableThead").height()+$(".dv-table").height()+$(".totalTr").height()){
	        		
	        	}
	        },error:function(){
	        	
	        }
	        	
	     }).done(function(){
	    	//生成折线图
				$('#containerline').highcharts({
					title: {
			            text: '',
			            x: -20 //center
			        },
			        subtitle: {
			            text: '',
			            x: -20
			        },
			        xAxis: {
			            categories: tArrayarea
			        },
			        yAxis: {
			            title: {
			                text: '参训人次（人次）'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '人次'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: '参训人次',
			            data: tArrayyear,
			            showInLegend: false ,
			            marker: {
		                    radius: 7,  //曲线点半径，默认是4
		                    
			            }
			        }]
			    
			    });	
				offsetHeightYear=$(".year").parent().offset().top;
	     });
	}
}

//分类统计
function getLevelInfo(){
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	
	if(judgeYear("#endTime")){
		$(".loadingDiv").css("display","block");
		$(".div").css("display","none");
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getLevelCount.action",
	        data: {startYear:startYear,endYear:endYear},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table='<tr class="level"><th width="25%">类型</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="15%">总参训人次</th><th width="15%">总结业人次</th><th width="15%">参训结业率</th></tr>';
	        	var tableFix='<tr class="levelFix"><th width="25%">类型</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="15%">总参训人次</th><th width="15%">总结业人次</th><th width="15%">参训结业率</th></tr>';
	        	$(".tableThead").html(tableFix).css("display","none");
	        	if(json.status=="OK"){
	        		$(".div").css("display","block");
	        		$(".loadingDiv").css("display","none");
	        		$(".dv-table table").html("");
	        		$(".totalTr").html("");
	        		$(".number span.total").html(formatNum(json.totalTraining));
	        		$(".number span.passengers").html(formatNum(json.totalTrainingTeacher));
	        		$(".number span.proportion").html(formatNum(json.totalTrainingComplete));
	        		$(".number span.coverage").html(json.totalTrainingTeacherRate);
	        		tArrayarea=[];
	        		for (var i = 0; i < json.records.length; i++) {
	        			table+='<tr><td width="25%">'+json.records[i].level+'</td><td width="15%">'+formatNum(json.records[i].trainingTeacherCount)+
	        			'</td><td width="15%">'+json.records[i].trainingTeacherRate+'%</td><td width="15%">'
	        			+formatNum(json.records[i].trainingCount)+'</td><td width="15%">'+formatNum(json.records[i].trainingCompleteCount)+
	        			'</td><td width="15%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
	        			tArrayarea[i]=new Array();
	        			var Percentage=Number(json.records[i].trainingCount)/Number(json.totalTraining)*100;
	        			tArrayarea[i]=[json.records[i].level,Percentage];
	        			
	        		}
	        		
	        	}
	        	$(".dv-table table").html(table);
	        	$(".totalTr").html('<tr class=""><td width="25%">总计</td><td width="15%">'+formatNum(json.totalTrainingTeacher)+
	        			'</td><td width="15%">'
	        			+json.totalTrainingTeacherRate+'%</td><td width="15%">'+formatNum(json.totalTraining)+'</td><td width="15%">'
	        			+formatNum(json.totalTrainingComplete)+'</td><td width="15%">'+json.totalTrainingCompleteRate+
	        			'%</td></tr>');
	        	$(".pieChart .title").html("项目分类饼状图");
	        	$("#containerline").height("433px");
	        },error:function(){
	        	
	        }
	        	
	     }).done(function(){
	    	 //饼状图
	    	 $('#containerline').highcharts({
					chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        credits:{
					     enabled:false // 禁用版权信息
					},
					colors:['#7cb5ec', '#90ed7d', '#f7a35c','#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1',
						       '#89440c','#9ccc65','#e74141','#00897b','#ffb74d','#fff3e0','#c4fdff','#c6dff1','#4db8ff','#ffe0b2','#7fb7ec',
						       '#ffcc80'],
			        title: {
			            text: ''
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: false,
			                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                },
			                showInLegend: true
			            }
			        },
			        series: [{
			            type: 'pie',
			            name: '占比',
			            data:tArrayarea
			        }]
			    });
	    	 offsetHeightLevel=$(".level").parent().offset().top;
	     });
		
	}
}

//分学段统计
function getSubjectInfo(){
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	
	if(judgeYear("#endTime")){
		$(".loadingDiv").css("display","block");
		$(".div").css("display","none");
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getGradeCount.action",
	        data: {startYear:startYear,endYear:endYear},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table='<tr class="subject"><th width="15%">学段</th><th width="15%">总教师人数</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="15%">总参训人次</th><th width="15%">总结业人次</th><th width="10%">参训结业率</th></tr>';
	        	var tableFix='<tr class="subjectFix"><th width="15%">学段</th><th width="15%">总教师人数</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="15%">总参训人次</th><th width="15%">总结业人次</th><th width="10%">参训结业率</th></tr>';
	        	$(".tableThead").html(tableFix).css("display","none");
	        	if(json.status=="OK"){
	        		$(".div").css("display","block");
	        		$(".loadingDiv").css("display","none");
	        		$(".dv-table table").html("");
	        		$(".totalTr").html("");
	        		$(".number span.total").html(formatNum(json.totalTraining));
	        		$(".number span.passengers").html(formatNum(json.totalTrainingTeacher));
	        		$(".number span.proportion").html(formatNum(json.totalTrainingComplete));
	        		$(".number span.coverage").html(json.totalTrainingTeacherRate);
	        		tArrayarea=[];
	        		var table1="";
	        		for (var i = 0; i < json.records.length; i++) {
	        			if(i==0){
	        				table1='<tr><td width="15%">'+json.records[i].grade+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
		        			'</td><td width="15%">'+formatNum(json.records[i].trainingTeacherCount)+
		        			'</td><td width="15%">'+json.records[i].trainingTeacherRate+'%</td><td width="15%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="15%">'+formatNum(json.records[i].trainingCompleteCount)+
		        			'</td><td width="10%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
	        			}else if(i==json.records.length-1){
	        				table+='<tr><td width="15%">'+json.records[i].grade+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
		        			'</td><td width="15%">'+formatNum(json.records[i].trainingTeacherCount)+
		        			'</td><td width="15%">'+json.records[i].trainingTeacherRate+'%</td><td width="15%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="15%">'+formatNum(json.records[i].trainingCompleteCount)+
		        			'</td><td width="10%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
	        				table=table+table1;
	        			}else{
		        			table+='<tr><td width="15%">'+json.records[i].grade+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
		        			'</td><td width="15%">'+formatNum(json.records[i].trainingTeacherCount)+
		        			'</td><td width="15%">'+json.records[i].trainingTeacherRate+'%</td><td width="15%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="15%">'+formatNum(json.records[i].trainingCompleteCount)+
		        			'</td><td width="10%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
		        			
	        			}
	        			tArrayarea[i]=new Array();
	        			var Percentage=Number(json.records[i].trainingCount)/Number(json.totalTraining)*100;
	        			tArrayarea[i]=[json.records[i].grade,Percentage];
	        		}
	        		
	        	}
	        	$(".dv-table table").html(table);
	        	$(".totalTr").html('<tr class=""><td width="15%">总计</td><td width="15%">'+formatNum(json.totalTeacher)+'</td><td width="15%">'
	        			+formatNum(json.totalTrainingTeacher)+'</td><td width="15%">'
	        			+json.totalTrainingTeacherRate+'%</td><td width="15%">'+formatNum(json.totalTraining)+'</td><td width="15%">'
	        			+formatNum(json.totalTrainingComplete)+'</td><td width="10%">'+json.totalTrainingCompleteRate+
	        			'%</td></tr>');
	        	$(".pieChart .title").html("教师学段饼状图");
	        	$("#containerline").height("433px");
	        },error:function(){
	        	
	        }
	        	
	     }).done(function(){
	    	//饼状图
	    	 $('#containerline').highcharts({
					chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        colors:['#7cb5ec', '#90ed7d', '#f7a35c','#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1',
						       '#89440c','#9ccc65','#e74141','#00897b','#ffb74d','#fff3e0','#c4fdff','#c6dff1','#4db8ff','#ffe0b2','#7fb7ec',
						       '#ffcc80'],
			        credits:{
					     enabled:false // 禁用版权信息
					},
			        title: {
			            text: ''
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: false,
			                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                },
			                showInLegend: true
			            }
			        },
			        series: [{
			            type: 'pie',
			            name: '占比',
			            data:tArrayarea
			        }]
			    });
	    	 offsetHeightSubject=$(".subject").parent().offset().top;
	     });
	}
}

//分学科统计
function getGradeInfo(){
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	
	if(judgeYear("#endTime")){
		$(".loadingDiv").css("display","block");
		$(".div").css("display","none");
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getSubjectCount.action",
	        data: {startYear:startYear,endYear:endYear},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table='<tr class="grade"><th width="25%">学科</th><th width="15%">总教师人数</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="10%">总参训人次</th><th width="10%">总结业人次</th><th width="10%">参训结业率</th></tr>';
	        	var tableFix='<tr class="gradeFix"><th width="25%">学科</th><th width="15%">总教师人数</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="10%">总参训人次</th><th width="10%">总结业人次</th><th width="10%">参训结业率</th></tr>';
	        	$(".tableThead").html(tableFix).css("display","none");
	        	if(json.status=="OK"){
	        		$(".div").css("display","block");
	        		$(".loadingDiv").css("display","none");
	        		$(".dv-table table").html("");
	        		$(".totalTr").html("");
	        		$(".number span.total").html(formatNum(json.totalTraining));
	        		$(".number span.passengers").html(formatNum(json.totalTrainingTeacher));
	        		$(".number span.proportion").html(formatNum(json.totalTrainingComplete));
	        		$(".number span.coverage").html(json.totalTrainingTeacherRate);
	        		var table1="";
	        		tArrayarea=[];
	        		tArrayyear=[];
	        		for (var i = 0; i < json.records.length; i++) {
	        			if(i==0){
	        				table1='<tr><td width="25%">'+json.records[i].grade+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
	        				'</td><td width="15%">'
	        				+formatNum(json.records[i].trainingTeacherCount)+
		        			'</td><td width="15%">'+json.records[i].trainingTeacherRate+'%</td><td width="10%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'+formatNum(json.records[i].trainingCompleteCount)+
		        			'</td><td width="10%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
	        			}else if(i==json.records.length-1){
	        				table+='<tr><td width="25%">'+json.records[i].grade+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
	        				'</td><td width="15%">'
	        				+formatNum(json.records[i].trainingTeacherCount)+
		        			'</td><td width="15%">'+json.records[i].trainingTeacherRate+'%</td><td width="10%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'+formatNum(json.records[i].trainingCompleteCount)+
		        			'</td><td width="10%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
	        				table=table+table1;
	        			}else{
		        			table+='<tr><td width="25%">'+json.records[i].grade+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
	        				'</td><td width="15%">'
	        				+formatNum(json.records[i].trainingTeacherCount)+
		        			'</td><td width="10%">'+json.records[i].trainingTeacherRate+'%</td><td width="15%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'+formatNum(json.records[i].trainingCompleteCount)+
		        			'</td><td width="10%">'+json.records[i].trainingCompleteRate+'%</td></tr>';
		        			
	        			}
	        			tArrayarea[i]=json.records[i].grade;
	        			tArrayyear[i]=json.records[i].trainingCount;
	        		}
	        		
	        	}
	        	$(".dv-table table").html(table);
	        	$(".totalTr").html('<tr class=""><td width="25%">总计</td><td width="15%">'+formatNum(json.totalTeacher)+
	        			'</td><td width="15%">'+formatNum(json.totalTrainingTeacher)+'</td><td width="15%">'
	        			+json.totalTrainingTeacherRate+'%</td><td width="10%">'+formatNum(json.totalTraining)+'</td><td width="10%">'
	        			+formatNum(json.totalTrainingComplete)+'</td><td width="10%">'+json.totalTrainingCompleteRate+
	        			'%</td></tr>');
	        	$(".pieChart .title").html("学科统计横柱图");
	        	$("#containerline").height((tArrayyear.length+1)*25+"px");
	        },error:function(){
	        	
	        }
	        	
	     }).done(function(){
	    	 
	    	 //横柱图
	    	 $('#containerline').highcharts({
			        chart: {
			            type: 'bar'
			        },
			        title: {
			            text: ''
			        },
			        subtitle: {
			            text: ''
			        },
			        xAxis: {
			            categories: tArrayarea,
			            title: {
			                text: null
			            }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '人次',
			                align: 'high'
			            },
			            labels: {
			                overflow: 'justify'
			            }
			        },
			        tooltip: {
			            valueSuffix: '人次'
			        },
			        plotOptions: {
			            bar: {
			                dataLabels: {
			                    enabled: true
			                }
			            }
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -40,
			            y: 100,
			            floating: true,
			            borderWidth: 1,
			            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
			            shadow: true
			        },
			        credits: {
			            enabled: false
			        },
			        series: [{
			            name: '统计学科参训人次',
			            data: tArrayyear
			        }]
			    });
	    	 offsetHeightGrade=$(".grade").parent().offset().top;
	     });
	}else{
		if(startYear==""){
			$("#startTime").css({
				"border-color":"red",
				"box-shadow":"none"
			});
			layer.confirm('开始年份不能为空', {
				  btn: ['确定'] //按钮
				}, function(){
				  layer.closeAll();
				});
			return false;
		}else{
			$("#startTime").removeAttr("style");
		}
		if(endYear==""){
			$("#endTime").css({
				"border-color":"red",
				"box-shadow":"none"
			});
			layer.confirm('结束年份不能为空', {
				  btn: ['确定'] //按钮
				}, function(){
				  layer.closeAll();
				});
			return false;
		}else{
			$("#endTime").removeAttr("style");
		}
	}
}


//分地区统计
function getOrganizationInfo(){
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	
	if(judgeYear("#endTime")){
		$(".loadingDiv").css("display","block");
		$(".div").css("display","none");
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getOrganizationCount.action",
	        data: {startYear:startYear,endYear:endYear},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table='<tr class="organization"><th width="25%">地区</th><th width="15%">总教师人数</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="10%">总参训人次</th><th width="10%">总结业人次</th><th width="10%">参训结业率</th></tr>';
	        	var tableFix='<tr class="organizationFix"><th width="25%">地区</th><th width="15%">总教师人数</th><th width="15%">总参训人数</th><th width="15%">参训覆盖率</th>'+ 
	        	'<th width="10%">总参训人次</th><th width="10%">总结业人次</th><th width="10%">参训结业率</th></tr>';
	        	$(".tableThead").html(tableFix).css("display","none");
	        	if(json.status=="OK"){
	        		$(".div").css("display","block");
	        		$(".loadingDiv").css("display","none");
	        		$(".dv-table table").html("");
	        		$(".totalTr").html("");
	        		$(".number span.total").html(formatNum(json.totalTraining));
	        		$(".number span.passengers").html(formatNum(json.totalTrainingTeacher));
	        		$(".number span.proportion").html(formatNum(json.totalTrainingComplete));
	        		$(".number span.coverage").html(json.totalTrainingTeacherRate);
	        		tArrayarea=[];
	        		tArrayyear=[];
	        		for (var i = 0; i < json.records.length; i++) {
	        			if(json.level=="1"&&json.records[i].organization!="null"){
	        				table+='<tr><td class="extend" width="25%"><a class="extentBtn extentAdd" onclick="extendLevel(\''
	        					+json.records[i].parent+'\',this)" name="'+i+'"></a>'
		        				+""+'<div class="text-center">'+json.records[i].organization+
		        			'</div><b class="clear"></b></td><td width="15%">'+
		        			formatNum(json.records[i].teacherCount)+'</td><td width="15%">'+
		        			formatNum(json.records[i].trainingTeacherCount)+'</td><td width="15%">'+json.records[i].trainingTeacherRate+
		        			'%</td><td width="10%">'+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'
		        			+formatNum(json.records[i].trainingCompleteCount)+'</td><td width="10%">'
		        			+json.records[i].trainingCompleteRate+'%</td></tr>';
	        			}else{
	        				if(json.records[i].organization!="null"){
	        					table+='<tr><td width="25%">'+json.records[i].organization+'</td><td width="15%">'+
			        			formatNum(json.records[i].teacherCount)+'</td><td width="15%">'+
			        			formatNum(json.records[i].trainingTeacherCount)+'</td><td width="15%">'+json.records[i].trainingTeacherRate+
			        			'%</td><td width="10%">'+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'
			        			+formatNum(json.records[i].trainingCompleteCount)+'</td><td width="10%">'
			        			+json.records[i].trainingCompleteRate+'%</td></tr>';
	        				}else{
	        					
	        				}
	        				
	        			}
	        			tArrayarea[i]=json.records[i].organization;
	        			tArrayyear[i]=json.records[i].trainingCount;
	        			
	        		}
	        		
	        	}
	        	$(".dv-table table").html(table);
	        	$(".totalTr").html('<tr class=""><td width="25%">总计</td><td width="15%">'+formatNum(json.totalTeacher)+'</td><td width="15%">'
	        			+formatNum(json.totalTrainingTeacher)+'</td><td width="15%">'+json.totalTrainingTeacherRate+'%</td><td width="10%">'
	        			+formatNum(json.totalTraining)+'</td><td width="10%">'
	        			+formatNum(json.totalTrainingComplete)+'</td><td width="10%">'+json.totalTrainingCompleteRate+
	        			'%</td></tr>');
	        	$(".pieChart .title").html("地区统计横柱图");
	        	$("#containerline").height((tArrayyear.length+1)*25+"px");
	        },error:function(){
	        	
	        }
	        	
	     }).done(function(){
	    	//横柱图
	    	 $('#containerline').highcharts({
			        chart: {
			            type: 'bar'
			        },
			        title: {
			            text: ''
			        },
			        subtitle: {
			            text: ''
			        },
			        xAxis: {
			            categories: tArrayarea,
			            title: {
			                text: null
			            }
			        },
			        yAxis: {
			            min: 0,
			            title: {
			                text: '人次',
			                align: 'high'
			            },
			            labels: {
			                overflow: 'justify'
			            }
			        },
			        tooltip: {
			            valueSuffix: '人次'
			        },
			        plotOptions: {
			            bar: {
			                dataLabels: {
			                    enabled: true
			                }
			            }
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'top',
			            x: -40,
			            y: 100,
			            floating: true,
			            borderWidth: 1,
			            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
			            shadow: true
			        },
			        credits: {
			            enabled: false
			        },
			        series: [{
			            name: '统计地区参训人次',
			            data: tArrayyear
			        }]
			    });
	    	 offsetHeightOrganization=$(".organization").parent().offset().top;
	    	 $(".dv-table .extend").each(function(i,val){
				$(this).find("div").css("width",$(this).width()-36+"px");
	    	 });
	     });
	}
}
//判断开始年份和结束年份的大小
function judgeYear(obj){
	var start=Number($("#startTime").val());
	var end=Number($("#endTime").val());
	if(end!=""){
		if(end<start){
			$(obj).css({
				"border-color":"red",
				"box-shadow":"none"
			});
			layer.confirm('开始年份不能大于结束年份', {
				  btn: ['确定'] //按钮
				}, function(){
				  layer.closeAll();
				});
		    return false;
		}else{
			$("#startTime,#endTime").removeAttr("style");
		    return true;
		}
	}
}

//展开下级
function extendLevel(parent,obj){
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	var name=$(obj).attr("name");
	if($(obj).hasClass("extentAdd")){
		$(".loadingDiv").css({"display":"block","height":$(document).height()});
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getOrganizationCount.action",
	        data: {startYear:startYear,endYear:endYear,parent:parent},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table="";
	        	if(json.status=="OK"){
	        		$(".loadingDiv").css("display","none");
	        		if(json.records.length=="0"){
	        			$(obj).removeClass("extentRemove");
	        			$(obj).removeClass("extentAdd");
	        		}else{
	        			for (var i = 0; i < json.records.length; i++) {
	        				if(json.records[i].organization!="null"){
	        					table+='<tr class="exendChild exend'+name+'"><td width="25%">'+json.records[i].organization+'</td><td width="15%">'+
			        			formatNum(json.records[i].teacherCount)+'</td><td width="15%">'+
			        			formatNum(json.records[i].trainingTeacherCount)+'</td><td width="15%">'+json.records[i].trainingTeacherRate+
			        			'%</td><td width="10%">'+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'
			        			+formatNum(json.records[i].trainingCompleteCount)+'</td><td width="10%">'
			        			+json.records[i].trainingCompleteRate+'%</td></tr>';
	        				}
	        				
	        			}
	        			$(obj).addClass("extentRemove").removeClass("extentAdd")
	        			.parent().parent().after(table);
	        		}
	        	}
	        }
		});
	}else if($(obj).hasClass("extentRemove")){
		$(obj).removeClass("extentRemove").addClass("extentAdd");
		$(".exend"+name).remove();
	}	
}
