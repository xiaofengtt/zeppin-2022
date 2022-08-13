$(function(){
	getProject();
	$("#endTime").change(function(){
		judgeYear("#endTime");
	});
	$("#startTime").change(function(){
		judgeYear("#startTime");
	});
	getInfo();
});
$(document).ready(function(){
	$(".loadingDiv").css({"width":$(".loadingDiv").parent().parent().width()+30+"px","height":$(".loadingDiv").parent().parent().height()+"px"});
});
var offsetHeight;
$(window).scroll(function() {	
	var width=$(".main").width()-1;
    if(offsetHeight<$(window).scrollTop()&&$(window).scrollTop()<(offsetHeight+$(".tableThead").height()+$(".dv-table").height()+$(".totalTr").height()-2)){
    	$(".theadFixed").css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".totalTr,.dv-table").width(width);
    }else{
    	$(".theadFixed").css({"position":"static","width":width,"display":"none"});
    	$(".totalTr,.dv-table").width(width);
    }
  });
function stopPropagation(e) {
	if (e.stopPropagation)
	e.stopPropagation();
	else
	e.cancelBubble = true;
} 
function allchk(){ 
    var chknum = $(".options .checkbox").size();//选项总个数 
    var chk = 0; 
   	$(".options .checkbox").each(function () {   
//        if($(this).attr("checked")=="checked"){ 
//            chk++; 
//        } 
    }); 
   	var checklist = document.getElementsByName ("selected");
   	for(var i=0;i<checklist.length;i++)
	   {
	      if(checklist[i].checked == 1){
	    	  chk++;
	      }
	   }
    if(chknum==chk){//全选 
        $("#all").prop("checked", true); 
    }else{//不全选 
        $("#all").prop("checked", false); 
    } 
}
function closeSelect(){
//	event.stopPropagation();
//	$(".options").css("display","none");
}
//选择项目
function getProject(){
	$.ajax({
        type: "GET",
        url: "../admin/countTraining_getProjectList.action",
        data: {},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	var option="";
        	if(json.status=="OK"){
        		for (var i = 0; i < json.records.length; i++) {
//        			option+="<option value="+json.records[i].id+">"+json.records[i].name+"</option>";
        			option+="<p onclick='closeSelect()'><input class='checkbox checkbox1' type='checkbox' id='"+
        			json.records[i].id+"' value='"+json.records[i].name+"' name='selected'/><label for='"+json.records[i].id+"'>"+
        			json.records[i].name+"</label><span class='clear'></span></p>";
        		}
        	}
//        	$("#projectName").append(option);
        	$(".options").append(option);
        	$(".options label").width($(".options").width()-50+"px");
        },error:function(){
        	
        }
        	
     }).done(function(){
    	 $("#select1").click(function(e){
			stopPropagation(e);
			$(".options").stop().fadeIn();
		});
		$("#all").click(function(){  
			var checklist = document.getElementsByName ("selected");
			   if(document.getElementById("all").checked)
			   {
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = 1;
			   }
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = 0;
			  }
			 }
		});
		$(".options .checkbox").click(function(){ 
		    allchk(); 
		}); 
        $(document).click(function(e){
             stopPropagation(e);
            $(".options").hide();
            var valArr = new Array; 
            var valArr2 = new Array;
//		    $(".options :checkbox[checked]").each(function(i){ 
//		        valArr[i] = $(this).val(); 
//		        valArr2[i] = $(this).attr("id");
//		    }); 
            var checklist = document.getElementsByName ("selected");
            for(var i=0;i<checklist.length;i++)
			   {
			     if(checklist[i].checked == 1){
			    	 valArr.push(checklist[i].value); 
			    	 valArr2.push(checklist[i].id);
			     }
			   }
		    parents = valArr2.join(',');//转换为逗号隔开的字符串 
		    if(parents==""||valArr2[0]==""){
		    	parents="";
		    }
		    $(".choose").html(valArr[0]); 
		    $(".parentInput").val(parents);
        })
     });
}
var tArray=[];
var tArrayarea=[];//年份
var tArraypie=new Array();//培训人次饼状图
var tArrayyear=[];//年份数值
//查询获取信息
function getInfo(){
	$(".contentsDiv").css("display","none");
	var valArr = []; 
    var valArr2 = [];
    var checklist = document.getElementsByName ("selected");
    for(var i=0;i<checklist.length;i++)
	   {
	     if(checklist[i].checked == 1){
	    	 valArr.push(checklist[i].value); 
	    	 valArr2.push(checklist[i].id);
	     }
	   }
    parents = valArr2.join(',');//转换为逗号隔开的字符串 
    if(parents==""||valArr2[0]==""){
    	parents="";
    }
    $(".choose").html(valArr[0]); 
    $(".parentInput").val(parents);
	var startYear=$("#startTime").val();
	var endYear=$("#endTime").val();
	var projectType=$(".parentInput").val();
	
	if(judgeYear("#endTime")){
		$(".loadingDiv").css("display","block");
		$.ajax({
	        type: "POST",
	        url: "../admin/countTraining_getVillageCount.action",
	        data: {startYear:startYear,endYear:endYear,projectType:projectType},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var table="";
	        	var datatable="<tr><th></th><th>覆盖率</th></tr>";
	        	var datatable1="<tr><th></th><th>教师培训人数</th></tr>";
	        	if(json.status=="OK"){
	        		$(".contentsDiv").css("display","block");
	        		$(".loadingDiv").css("display","none");
	        		$("#datatable").html("");
	        		tArray=[];
	        		tArrayarea=[];
	        		tArraypie=[];
	        		tArrayyear=[];
	        		$(".number span.total").html(formatNum(json.totalTraining));
	        		$(".number span.passengers").html(formatNum(json.totalVillageTraining));
	        		$(".number span.coverage").html(json.totalVillageTrainingTeacherRate);
	        		$(".number span.proportion").html(json.totalVillageTrainingRate);
	        		for (var i = 0; i < json.records.length; i++) {
	        			if(json.level=="1"&&json.records[i].name!="null"){
	        				table+='<tr><td class="extend" width="25%"><a class="extentBtn extentAdd" onclick="extendLevel(\''
	        					+json.records[i].id+'\',this)" name="'+i+'"></a>'
		        				+""+'<div class="text-center">'+json.records[i].name+
		        			'</div><b class="clear"></b></td><td width="15%">'+formatNum(json.records[i].teacherCount)+
		        			'</td><td width="10%">'+formatNum(json.records[i].villageTeacherCount)+'</td><td width="10%">'
		        			+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'+formatNum(json.records[i].villageTrainingCount)+
		        			'</td><td width="10%">'+formatNum(json.records[i].villageTrainingTeacherCount)+'</td><td width="10%">'
		        			+json.records[i].villageTrainingRate+'%</td><td width="10%">'+json.records[i].villageTrainingTeacherRate+'%</td></tr>';
	        			}else{
	        				if(json.records[i].name!="null"){
	        					table+='<tr><td width="25%">'+json.records[i].name+'</td><td width="15%">'+formatNum(json.records[i].teacherCount)+
			        			'</td><td width="10%">'+formatNum(json.records[i].villageTeacherCount)+'</td><td width="10%">'
			        			+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'+formatNum(json.records[i].villageTrainingCount)+
			        			'</td><td width="10%">'+formatNum(json.records[i].villageTrainingTeacherCount)+'</td><td width="10%">'
			        			+json.records[i].villageTrainingRate+'%</td><td width="10%">'+json.records[i].villageTrainingTeacherRate+'%</td></tr>';
	        				}
	        				
	        			}
	        			

	        			datatable+='<tr><td>'+json.records[i].name+'</td><td>'+json.records[i].villageTrainingRate+'</td></tr>';
	        			datatable1+='<tr><td>'+json.records[i].name+'</td><td>'+json.records[i].villageTrainingRate+'</td></tr>';
	        					        			
	        		}
	        		for (var i = 0; i < json.yearRecords.length; i++) {
	        			tArrayarea[i]=json.yearRecords[i].year;
	        			tArrayyear[i]=json.yearRecords[i].poorTrainingCount;
	        		}
	        		tArray[1]=new Array();
        			tArray[0]=new Array();
        			tArray[0][0]="乡村教师参训人数";
        			tArray[0][1]=json.totalVillageTrainingTeacherRate;
        			tArray[1][0]="乡村教师未参训人数";
        			tArray[1][1]=100-Number(json.totalVillageTrainingTeacherRate);
        			tArraypie[1]=new Array();
        			tArraypie[0]=new Array();
        			tArraypie[0][0]="乡村教师参训人次";
        			tArraypie[0][1]=json.totalVillageTrainingRate;
        			tArraypie[1][0]="非乡村教师参训人次";
        			tArraypie[1][1]=100-Number(json.totalVillageTrainingRate);
	        	}
	        	$(".dv-table table").html(table);
	        	$("#datatable").html(datatable);
	        	$(".totalTr").html('<tr class=""><td width="25%">总计</td><td width="15%">'+formatNum(json.totalTeacher)+'</td><td width="10%">'
	        			+formatNum(json.totalVillageTeacher)+'</td><td width="10%">'+formatNum(json.totalTraining)+'</td><td width="10%">'
	        			+formatNum(json.totalVillageTraining)+'</td><td width="10%">'+formatNum(json.totalVillageTrainingTeacher)+
	        			'</td><td width="10%">'+json.totalVillageTrainingRate+'%</td><td width="10%">'+json.totalVillageTrainingTeacherRate+
	        			'%</td></tr>');
	        	$(".col-md-6 div").width($(".main").width()*0.48+"px");
	        	$(".col-md-12 div").width($(".main").width()*0.98+"px");
	        },error:function(){
	        	
	        }
	        	
	     }).done(function(){
	    	 var tableHeight=$(".dv-table table").height();
				var divHeight=$(".dv-table").height();
				if(tableHeight>divHeight){
					$("table.tableThead th:last-child,table.totalTr td:last-child").addClass("paddingTd");
				}else{
					$("table.tableThead th:last-child,table.totalTr td:last-child").removeClass("paddingTd");
				}
			//覆盖率饼状图
				$('#containerrate').highcharts({
					chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
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
			            data: tArray
			            
			        }]
			    });	
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
			                text: '贫困地区教师培训人次（人次）'
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
			            name: '培训人次',
			            data: tArrayyear,
			            showInLegend: false ,
			            marker: {
		                    radius: 7,  //曲线点半径，默认是4
		                    
			            }
			        }]
			    
			    });	
				//培训人次饼状图
				$('#containerpie').highcharts({
					chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
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
			            data: tArraypie
			            
			        }]
			    });
				offsetHeight=$(".tableThead").offset().top;
				$(".dv-table .extend").each(function(i,val){
					$(this).find("div").css("width",$(this).width()-36+"px");
				});
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
	var projectType=$(".parentInput").val();
	var name=$(obj).attr("name");
	if($(obj).hasClass("extentAdd")){
		$(".loadingDiv").css({"display":"block","height":$(document).height()});
		$.ajax({
	        type: "GET",
	        url: "../admin/countTraining_getVillageCount.action",
	        data: {startYear:startYear,endYear:endYear,projectType:projectType,parent:parent},
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
	        				if(json.records[i].name!="null"){
	        					table+='<tr class="exendChild exend'+name+'"><td width="25%">'+json.records[i].name+'</td><td width="15%">'
		        				+formatNum(json.records[i].teacherCount)+
			        			'</td><td width="10%">'+formatNum(json.records[i].villageTeacherCount)+'</td><td width="10%">'
			        			+formatNum(json.records[i].trainingCount)+'</td><td width="10%">'+formatNum(json.records[i].villageTrainingCount)+
			        			'</td><td width="10%">'+formatNum(json.records[i].villageTrainingTeacherCount)+'</td><td width="10%">'
			        			+json.records[i].villageTrainingRate+'%</td><td width="10%">'+json.records[i].villageTrainingTeacherRate+'%</td></tr>';
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



