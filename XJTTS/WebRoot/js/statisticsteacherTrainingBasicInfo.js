$(function(){
	$(".btnDiv a").click(function(){
		$(this).addClass("light").siblings().removeClass("light");
	});
	getBasicInfo();
	getNumber();
	getDefault();
});
$(document).ready(function(){
	$(".loadingDiv").css({"width":$(".loadingDiv").parent().parent().width()+30+"px",
		"height":$(".loadingDiv").parent().parent().height()+"px"});
});
var offsetHeight;
var offsetHeightSubject;
var offsetHeightTeacherAddress;
var offsetHeightTeachingLanguage;
$(window).scroll(function() {	
	var width=$(".main").width()*0.49;
    if(offsetHeight<$(window).scrollTop()&&$(window).scrollTop()<(offsetHeight+$(".tableTheadJobTitle").height()+$(".dv-tableJobTitle").height())){
    	$(".tableTheadJobTitleFix").css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".tableTheadJobTitleFix th").css({"width":width*0.5+"px"});
    }else{
    	$(".tableTheadJobTitleFix").css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightSubject<$(window).scrollTop()&&$(window).scrollTop()<(offsetHeightSubject+$(".tableTheadTeachingSubject").height()+$(".dv-tableTeachingSubject").height())){
    	$(".tableTheadTeachingSubjectFix").css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".tableTheadTeachingSubjectFix th:first").css({"width":width*0.6+"px"});
    }else{
    	$(".tableTheadTeachingSubjectFix").css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightTeacherAddress<$(window).scrollTop()&&$(window).scrollTop()<(offsetHeightTeacherAddress+$(".tableTheadTeacherAddress").height()+$(".dv-tableTeacherAddress").height())){
    	$(".tableTheadTeacherAddressFix").css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".tableTheadTeacherAddressFix th:first").css({"width":width*0.6+"px"});
    }else{
    	$(".tableTheadTeacherAddressFix").css({"position":"static","width":width,"display":"none"});
    }
    if(offsetHeightTeachingLanguage<$(window).scrollTop()&&$(window).scrollTop()<(offsetHeightTeachingLanguage+$(".tableTheadTeachingLanguage").height()+$(".dv-tableTeachingLanguage").height())){
    	$(".tableTheadTeachingLanguageFix").css({"position":"fixed","top":"0","left":"225px","width":width,"margin-top":"0","display":"block"});
    	$(".tableTheadTeachingLanguageFix th:first").css({"width":width*0.5+"px"});
    }else{
    	$(".tableTheadTeachingLanguageFix").css({"position":"static","width":width,"display":"none"});
    }
});
$(window).resize(function(){
	var width=$(".main").width()*0.49;
	$(".tableTheadJobTitleFix th").css({"width":width*0.5+"px"});
})
//判断地区是否为空
function areaIsNull(){
	var id=$("#organization").val();
	if(id==""||id==null||id==undefined){
		$(".companylocation").css({
			"border-color":"red",
			"box-shadow":"none"
		});
		layer.confirm('请选择地区', {
			  btn: ['确定'] //按钮
			}, function(){
			  layer.closeAll();
			});
	    return false;
	}else{		
	    $(".companylocation").removeAttr("style");
	    $(".btnDiv a:eq(0)").addClass("light").siblings().removeClass("light");
		getBasicInfo();
		getNumber();
	}
}
var tArraySex=[];
var tArrayAge=[];
var tArrayTeachingAge=[];
var tArraySchoolType=[];
var tArrayJobTitle=[];
var tArrayPolitice=[];
//基本情况统计
function getBasicInfo(){
	var id=$("#organization option:selected").attr("name");
	$(".div").css("display","none");
	$(".loadingDiv").css("display","block");
	$.ajax({
        type: "GET",
        url: "../admin/countTeacher_initBaseInfo.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.status=="OK"){
        		$(".loadingDiv").css("display","none");
        		$(".div0").css("display","block");
        		var recordssex="";
        		var recordsage="";
        		var recordsteachingAge="";
        		var recordsschoolType="";
        		var recordsjobTitle="";
        		var recordspolitice="";
        		var totalNumbersex=0;
        		var totalNumberage=0;
        		var totalNumberteachingAge=0;
        		var totalNumberschoolTypesch=0;
        		var totalNumberschoolTypetea=0;
        		var totalNumberjobTitle=0;
        		var totalNumberpolitice=0;
        		tArraySex=[];
        		tArrayAge=[];
        		tArrayTeachingAge=[];
        		tArraySchoolType=[];
        		tArrayJobTitle=[];
        		tArrayPolitice=[];
        		var table1="";
        		for (var i = 0; i < json.records.sex.length; i++) {
        			recordssex+='<tr><td width="50%" class="tl">'+json.records.sex[i].name+'</td><td width="50%" class="tr">'
        			+formatNum(json.records.sex[i].count)+'</td></tr>';
        			totalNumbersex+=Number(json.records.sex[i].count);
        			tArraySex[i]=new Array();
        			tArraySex[i]=[json.records.sex[i].name,Number(json.records.sex[i].count)];
        			
        		}
        		//概率
        		for (var i = 0; i < tArraySex.length; i++) {
        			tArraySex[i][1]=tArraySex[i][1]/totalNumbersex;
        		}
        		$(".table-sex").html(recordssex);
        		$("#datatable_Sex").append(recordssex);
        		$(".totalSex").html(formatNum(totalNumbersex));
        		for (var i = 0; i < json.records.age.length; i++) {
        			recordsage+='<tr><td width="50%">'+json.records.age[i].name+'</td><td width="50%">'
        			+formatNum(json.records.age[i].count)+'</td></tr>';
        			totalNumberage+=Number(json.records.age[i].count);
        			tArrayAge[i]=new Array();
        			tArrayAge[i]=[json.records.age[i].name,Number(json.records.age[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayAge.length; i++) {
        			tArrayAge[i][1]=tArrayAge[i][1]/totalNumberage;
        		}
        		$(".table-age").html(recordsage);
        		$(".totalAge").html(formatNum(totalNumberage));
        		for (var i = 0; i < json.records.teachingAge.length; i++) {
        			recordsteachingAge+='<tr><td width="50%">'+json.records.teachingAge[i].name+'</td><td width="50%">'
        			+formatNum(json.records.teachingAge[i].count)+'</td></tr>';
        			totalNumberteachingAge+=Number(json.records.teachingAge[i].count);
        			tArrayTeachingAge[i]=new Array();
        			tArrayTeachingAge[i]=[json.records.teachingAge[i].name,Number(json.records.teachingAge[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayTeachingAge.length; i++) {
        			tArrayTeachingAge[i][1]=tArrayTeachingAge[i][1]/totalNumberteachingAge;
        		}
        		$(".table-teachingAge").html(recordsteachingAge);
        		$(".totalteachingAge").html(formatNum(totalNumberteachingAge));
        		for (var i = 0; i < json.records.schoolType.length; i++) {
        			if(i=="0"){
        				table1='<tr><td width="30%">'+json.records.schoolType[i].name+'</td><td width="30%">'
            			+formatNum(json.records.schoolType[i].schoolCount)+'</td><td width="40%">'
            			+formatNum(json.records.schoolType[i].teacherCount)+'</td></tr>';
        			}else if(i==json.records.schoolType.length-1){
        				recordsschoolType+='<tr><td width="30%">'+json.records.schoolType[i].name+'</td><td width="30%">'
            			+formatNum(json.records.schoolType[i].schoolCount)+'</td><td width="40%">'
            			+formatNum(json.records.schoolType[i].teacherCount)+'</td></tr>';
        				recordsschoolType=recordsschoolType+table1;
        			}else{
        				recordsschoolType+='<tr><td width="30%">'+json.records.schoolType[i].name+'</td><td width="30%">'
            			+formatNum(json.records.schoolType[i].schoolCount)+'</td><td width="40%">'
            			+formatNum(json.records.schoolType[i].teacherCount)+'</td></tr>';
        			}     			
        			totalNumberschoolTypesch+=Number(json.records.schoolType[i].schoolCount);
        			totalNumberschoolTypetea+=Number(json.records.schoolType[i].teacherCount);
        			tArraySchoolType[i]=new Array();
        			tArraySchoolType[i]=[json.records.schoolType[i].name,Number(json.records.schoolType[i].teacherCount)];
        		}
        		//概率
        		for (var i = 0; i < tArraySchoolType.length; i++) {
        			tArraySchoolType[i][1]=tArraySchoolType[i][1]/totalNumberschoolTypetea;
        		}
        		$(".table-schoolType").html(recordsschoolType);
        		$(".totalschoolTypesch").html(formatNum(totalNumberschoolTypesch));
        		$(".totalschoolTypetea").html(formatNum(totalNumberschoolTypetea));
        		for (var i = 0; i < json.records.jobTitle.length; i++) {
        			if(i=="0"){
        				table1='<tr><td width="50%">'+json.records.jobTitle[i].name+'</td><td width="50%">'
            			+formatNum(json.records.jobTitle[i].count)+'</td></tr>';
        			}else if(i==json.records.jobTitle.length-1){
        				recordsjobTitle+='<tr><td width="50%">'+json.records.jobTitle[i].name+'</td><td width="50%">'
            			+formatNum(json.records.jobTitle[i].count)+'</td></tr>';
        				recordsjobTitle=recordsjobTitle+table1;
        			}else{
        				recordsjobTitle+='<tr><td width="50%">'+json.records.jobTitle[i].name+'</td><td width="50%">'
            			+formatNum(json.records.jobTitle[i].count)+'</td></tr>';
        			}        			
        			totalNumberjobTitle+=Number(json.records.jobTitle[i].count);
        			tArrayJobTitle[i]=new Array();
        			tArrayJobTitle[i]=[json.records.jobTitle[i].name,Number(json.records.jobTitle[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayJobTitle.length; i++) {
        			tArrayJobTitle[i][1]=tArrayJobTitle[i][1]/totalNumberjobTitle;
        		}
        		$(".table-jobTitle").html(recordsjobTitle);
        		$(".totaljobTitle").html(formatNum(totalNumberjobTitle));
        		for (var i = 0; i < json.records.politice.length; i++) {
        			recordspolitice+='<tr><td width="50%">'+json.records.politice[i].name+'</td><td width="50%">'
        			+formatNum(json.records.politice[i].count)+'</td></tr>';
        			totalNumberpolitice+=Number(json.records.politice[i].count);
        			tArrayPolitice[i]=new Array();
        			tArrayPolitice[i]=[json.records.politice[i].name,Number(json.records.politice[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayPolitice.length; i++) {
        			tArrayPolitice[i][1]=tArrayPolitice[i][1]/totalNumberpolitice;
        		}
        		$(".table-politice").html(recordspolitice);
        		$(".totalpolitice").html(formatNum(totalNumberpolitice));
        		$(".pieChart div").width($(".main").width()*0.49-10+"px");
        	}else{
        		
        	}
        },error:function(){
        	
        }
    }).done(function(){
    	//循环表格形成饼状图
    	$('.dv-table').each(function() {
			var id = $(this).attr('name');
			var obj=new Array();
			if(id=="Sex"){
				obj=tArraySex;
			}else if(id=="Age"){
				obj=tArrayAge;
			}else if(id=="TeachingAge"){
				obj=tArrayTeachingAge;
			}else if(id=="SchoolType"){
				obj=tArraySchoolType;
			}else if(id=="JobTitle"){
				obj=tArrayJobTitle;
			}else if(id=="Politice"){
				obj=tArrayPolitice;
			}
			$('#chart-column_'+id).highcharts({
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
		            	size:'90%',
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                	distance: -50,
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
		            data:obj
		        }]
		    });
    	});
    	offsetHeight=$(".tableTheadJobTitle").offset().top;
    })
}
//总教师人数+去年同期+同比变动
function getNumber(){
	$(".contentsDiv").css("display","none");
	$(".loadingDiv").css("display","block");
	var id=$("#organization option:selected").attr("name");
	$.ajax({
        type: "GET",
        url: "../admin/countTeacher_initCount.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.status=="OK"){
        		$(".contentsDiv").css("display","block");
//        		$(".loadingDiv").css("display","none");
            	$(".number span.total").html(formatNum(json.records.teacherCount.currentYearCount));
            	$(".number span.passengers").html(formatNum(json.records.teacherCount.lastYearCount));
            	       
            	var percent=((Number(json.records.teacherCount.currentYearCount)-Number(json.records.teacherCount.lastYearCount))/Number(json.records.teacherCount.currentYearCount)*100).toFixed(2);
            	if(percent=="+0.00"||percent=="-0.00"){
            		$(".number span.proportion").html("0.00");
            	}else{
            		if(percent<0){
            			$(".number span.proportion").parent().css("color","#07d058");
            		}else{
            			$(".number span.proportion").parent().css("color","#ff6f39");
            		}
            		$(".number span.proportion").html(percent);
            	}
        	}else{
        		
        	}
        	
        },error:function(){
        	
        }
    });
}
var tArrayMutiLanguage=[];
var tArrayTeachingLanguage=[];
var tArrayTeachingSubject=[];
var tArrayTeachingGrade=[];
//教学信息统计
function getTeachInfo(){
	var id=$("#organization option:selected").attr("name");
	$(".loadingDiv").css("display","block");
	$(".div").css("display","none");
	$.ajax({
        type: "GET",
        url: "../admin/countTeacher_initTeachingInfo.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.status=="OK"){
        		$(".loadingDiv").css("display","none");
        		$(".div1").css("display","block");
        		var recordsmutiLanguage="";
        		var recordsteachingLanguage="";
        		var recordsteachingGrade="";
        		var recordsteachingSubject="";
        		var totalNumbermutiLanguage=0;
        		var totalNumberteachingLanguage=0;
        		var totalNumberteachingSubject=0;
        		var totalNumberteachingGrade=0;
        		tArrayMutiLanguage=[];
        		tArrayTeachingLanguage=[];
        		tArrayTeachingSubject=[];
        		tArrayTeachingGrade=[];
        		var table1="";
        		for (var i = 0; i < json.records.mutiLanguage.length; i++) {
        			recordsmutiLanguage+='<tr><td width="50%" class="tl">'+json.records.mutiLanguage[i].name+'</td><td width="50%" class="tr">'
        			+formatNum(json.records.mutiLanguage[i].count)+'</td></tr>';
        			totalNumbermutiLanguage+=Number(json.records.mutiLanguage[i].count);
        			tArrayMutiLanguage[i]=new Array();
        			tArrayMutiLanguage[i]=[json.records.mutiLanguage[i].name,Number(json.records.mutiLanguage[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayMutiLanguage.length; i++) {
        			tArrayMutiLanguage[i][1]=tArrayMutiLanguage[i][1]/totalNumbermutiLanguage;
        		}
        		$(".table-mutiLanguage").html(recordsmutiLanguage);
        		$(".totalMutiLanguage").html(formatNum(totalNumbermutiLanguage));
        		for (var i = 0; i < json.records.teachingLanguage.length; i++) {
        			if(i=="0"){
        				table1='<tr><td width="50%">'+json.records.teachingLanguage[i].name+'</td><td width="50%">'
            			+formatNum(json.records.teachingLanguage[i].count)+'</td></tr>';
        			}else if(i==json.records.teachingLanguage.length-1){
        				recordsteachingLanguage+='<tr><td width="50%">'+json.records.teachingLanguage[i].name+'</td><td width="50%">'
            			+formatNum(json.records.teachingLanguage[i].count)+'</td></tr>';
        				recordsteachingLanguage=recordsteachingLanguage+table1;
        			}else{
        				recordsteachingLanguage+='<tr><td width="50%">'+json.records.teachingLanguage[i].name+'</td><td width="50%">'
            			+formatNum(json.records.teachingLanguage[i].count)+'</td></tr>';
        			}        			
        			totalNumberteachingLanguage+=Number(json.records.teachingLanguage[i].count);
        			tArrayTeachingLanguage[i]=new Array();
        			tArrayTeachingLanguage[i]=[json.records.teachingLanguage[i].name,Number(json.records.teachingLanguage[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayTeachingLanguage.length; i++) {
        			tArrayTeachingLanguage[i][1]=tArrayTeachingLanguage[i][1]/totalNumberteachingLanguage;
        		}
        		$(".table-teachingLanguage").html(recordsteachingLanguage);
        		$(".totalTeachingLanguage").html(formatNum(totalNumberteachingLanguage));
        		for (var i = 0; i < json.records.teachingGrade.length; i++) {
        			if(i=="0"){
        				table1='<tr><td width="50%">'+json.records.teachingGrade[i].name+'</td><td width="50%">'
            			+formatNum(json.records.teachingGrade[i].count)+'</td></tr>';
        			}else if(i==json.records.teachingGrade.length-1){
        				recordsteachingGrade+='<tr><td width="50%">'+json.records.teachingGrade[i].name+'</td><td width="50%">'
            			+formatNum(json.records.teachingGrade[i].count)+'</td></tr>';
        				recordsteachingGrade=recordsteachingGrade+table1;
        			}else{
        				recordsteachingGrade+='<tr><td width="50%">'+json.records.teachingGrade[i].name+'</td><td width="50%">'
            			+formatNum(json.records.teachingGrade[i].count)+'</td></tr>';
        			}       			
        			totalNumberteachingGrade+=Number(json.records.teachingGrade[i].count);
        			tArrayTeachingGrade[i]=new Array();
        			tArrayTeachingGrade[i]=[json.records.teachingGrade[i].name,Number(json.records.teachingGrade[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayTeachingGrade.length; i++) {
        			tArrayTeachingGrade[i][1]=tArrayTeachingGrade[i][1]/totalNumberteachingGrade;
        		}
        		$(".table-teachingGrade").html(recordsteachingGrade);
        		$(".totalteachingGrade").html(formatNum(totalNumberteachingGrade));
        		for (var i = 0; i < json.records.teachingSubject.length; i++) {
        			if(i=="0"){
        				table1='<tr><td width="60%">'+json.records.teachingSubject[i].name+'</td><td width="40%">'
            			+formatNum(json.records.teachingSubject[i].count)+'</td></tr>';
        			}else if(i==json.records.teachingSubject.length-1){
        				recordsteachingSubject+='<tr><td width="60%">'+json.records.teachingSubject[i].name+'</td><td width="40%">'
            			+formatNum(json.records.teachingSubject[i].count)+'</td></tr>';
        				recordsteachingSubject=recordsteachingSubject+table1;
        			}else{
        				recordsteachingSubject+='<tr><td width="60%">'+json.records.teachingSubject[i].name+'</td><td width="40%">'
            			+formatNum(json.records.teachingSubject[i].count)+'</td></tr>';
        			}       			
        			totalNumberteachingSubject+=Number(json.records.teachingSubject[i].count);
        			tArrayTeachingSubject[i]=new Array();
        			tArrayTeachingSubject[i]=[json.records.teachingSubject[i].name,Number(json.records.teachingSubject[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayTeachingSubject.length; i++) {
        			tArrayTeachingSubject[i][1]=tArrayTeachingSubject[i][1]/totalNumberteachingSubject;
        		}
        		$(".table-teachingSubject").html(recordsteachingSubject);
        		$(".totalteachingSubject").html(formatNum(totalNumberteachingSubject));
        		$(".pieChart div").width($(".main").width()*0.49-10+"px");
        	}else{
        		
        	}
        	
        },error:function(){
        	
        }
    }).done(function(){
    	//循环表格形成饼状图
    	$('.dv-table').each(function() {
			var id = $(this).attr('name');
			var obj=new Array();
			if(id=="MutiLanguage"){
				obj=tArrayMutiLanguage;
			}else if(id=="TeachingLanguage"){
				obj=tArrayTeachingLanguage;
			}else if(id=="TeachingGrade"){
				obj=tArrayTeachingGrade;
			}else if(id=="TeachingSubject"){
				obj=tArrayTeachingSubject;
			}
			$('#chart-column_'+id).highcharts({
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
		            	size:'90%',
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
		            data:obj
		        }]
		    });
    	});
    	offsetHeightSubject=$(".tableTheadTeachingSubject").offset().top;
    	offsetHeightTeachingLanguage=$(".tableTheadTeachingLanguage").offset().top;
    });
}
var tArrayTeacherAddressType=[];
var tArrayarea=[];
var tArrayyear=[];
//分地区信息统计
function getAddressInfo(){
	var id=$("#organization option:selected").attr("name");
	$(".loadingDiv").css("display","block");
	$(".div").css("display","none");
	$.ajax({
        type: "GET",
        url: "../admin/countTeacher_initAddress.action",
        data: {id:id},
        dataType: "text",
        async: true,
        success: function (data) {
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.status=="OK"){
        		$(".loadingDiv").css("display","none");
        		$(".div2").css("display","block");
        		var recordsteacherAddressType="";
        		var recordsteacherAddress="";       		
        		var totalNumberteacherAddressType=0;
        		var totalNumberteacherAddress=0;
        		tArrayarea=[];
        		tArrayyear=[];
        		tArrayTeacherAddressType=[];
        		var table1="";
        		for (var i = 0; i < json.records.teacherAddressType.length; i++) {
        			if(i=="0"){
        				table1='<tr><td width="50%" class="tl">'+json.records.teacherAddressType[i].name+'</td><td width="50%" class="tr">'
            			+formatNum(json.records.teacherAddressType[i].count)+'</td></tr>';
        			}else if(i==json.records.teacherAddressType.length-1){
        				recordsteacherAddressType+='<tr><td width="50%" class="tl">'+json.records.teacherAddressType[i].name+'</td><td width="50%" class="tr">'
            			+formatNum(json.records.teacherAddressType[i].count)+'</td></tr>';
        				recordsteacherAddressType=recordsteacherAddressType+table1;
        			}else{
        				recordsteacherAddressType+='<tr><td width="50%" class="tl">'+json.records.teacherAddressType[i].name+'</td><td width="50%" class="tr">'
            			+formatNum(json.records.teacherAddressType[i].count)+'</td></tr>';
        			}       			
        			totalNumberteacherAddressType+=Number(json.records.teacherAddressType[i].count);
        			tArrayTeacherAddressType[i]=new Array();
        			tArrayTeacherAddressType[i]=[json.records.teacherAddressType[i].name,Number(json.records.teacherAddressType[i].count)];
        		}
        		//概率
        		for (var i = 0; i < tArrayTeacherAddressType.length; i++) {
        			tArrayTeacherAddressType[i][1]=tArrayTeacherAddressType[i][1]/totalNumberteacherAddressType;
        		}
        		$(".table-teacherAddressType").html(recordsteacherAddressType);
        		$(".totalteacherAddressType").html(formatNum(totalNumberteacherAddressType));
        		for (var i = 0; i < json.records.teacherAddress.length; i++) {
        			recordsteacherAddress+='<tr><td width="60%">'+json.records.teacherAddress[i].name+'</td><td width="40%">'
        			+formatNum(json.records.teacherAddress[i].count)+'</td></tr>';
        			totalNumberteacherAddress+=Number(json.records.teacherAddress[i].count);
        			tArrayarea[i]=json.records.teacherAddress[i].name;
        			tArrayyear[i]=Number(json.records.teacherAddress[i].count);
        		}
        		
        		$(".table-teacherAddress").html(recordsteacherAddress);
        		$(".totalteacherAddress").html(formatNum(totalNumberteacherAddress));
        		$(".pieChart div").width($(".main").width()*0.49-10+"px");
        		if(tArrayyear.length>2){
        			$("#containerline").height((tArrayyear.length+1)*25+"px");
        		}
        		
        	}else{
        		
        	}
        	
        },error:function(){
        	
        }
    }).done(function(){
    	//循环表格形成饼状图
			$('#chart-column_TeacherAddressType').highcharts({
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
		            	size:'90%',
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
		            data:tArrayTeacherAddressType
		        }]
		    });
    	
    	//柱状图
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
	                text: '名',
	                align: 'high'
	            },
	            labels: {
	                overflow: 'justify'
	            }
	        },
	        tooltip: {
	            valueSuffix: '名'
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
	            name: '教师所在地区人数',
	            data: tArrayyear
	        }]
	    });
    	offsetHeightTeacherAddress=$(".tableTheadTeacherAddress").offset().top;
    });
}
var OrganizationSelect="";
function getsnode(bid) {
	var cUrl = "../admin/projectAdm_getOrganizationLevel.action";
	bid = (typeof (bid) == 'undefined') ? '' : bid;
	cUrl += (bid) ? '?id=' + bid : '?id=0';
		$.getJSON(cUrl,
		function(data) {
			if (data.length > 0) {
				$.each(
					data,
					function(i, c) {
						OrganizationSelect += "<option value='"+c.scode+"' name='"+c.scode+"'>"+c.name+"</option>";
					});
			}
			$("#organization").html(OrganizationSelect);
	});
}
//获取默认值
function getDefault(){
	var cUrl = "../admin/projectAdm_getOrganizationLevel.action";
	bid = (typeof (bid) == 'undefined') ? '' : bid;
	var e = (bid) ? $('#bido' + bid) : $('#bido');
	cUrl += (bid) ? '?id=' + bid : '?id=0';
		$.getJSON(cUrl,
		function(data) {
			OrganizationSelect = '没有找到';
			if (data.length > 0) {
				OrganizationSelect = '';
				$.each(
					data,
					function(i, c) {
						OrganizationSelect = "<option value='"+c.id+"' name='"+c.scode+"' selected>全部</option>";
					});
			}
			$("#organization").html(OrganizationSelect);
			getsnode($("#organization").val());
	});
}