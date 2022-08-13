$(function(){
		var pcId = '';
		$.ajax({
	        type: "POST",
	        url: "../teacher/trainRecords_initPersonalFile.action",
	        data: {},
	        dataType: "text",
	        async: false,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	var btnstr="";
	        	if(json.Result=="OK"){				
					$("#teacherName").html(json.Records[0].teacher.teacherName);
					$("#teacherMobile").html(json.Records[0].teacher.teacherMobile);
					$("#teacherSex").html(json.Records[0].teacher.teacherSex);
					$("#teacherFolk").html(json.Records[0].teacher.teacherFolk);
					$("#teacherIdcard").html(json.Records[0].teacher.teacherIdcard);
					$("#teacherPolice").html(json.Records[0].teacher.teacherPolice);
					$("#teacherTeachAge").html(json.Records[0].teacher.teacherTeachAge);
					$("#teacherMainTeachSubject").html(json.Records[0].teacher.teacherMainTeachSubject);
					$("#teacherStatus").html(json.Records[0].teacher.teacherStatus);
					$("#teacherMainTeachGrade").html(json.Records[0].teacher.teacherMainTeachGrade);
					$("#teacherAuthorize").html(json.Records[0].teacher.teacherAuthorize);
					$("#teacherMainTeachLanguage").html(json.Records[0].teacher.teacherMainTeachLanguage);
					
					$("#teacherJobTitle").html(json.Records[0].teacher.teacherJobTitle);
					$("#teacherBackground").html(json.Records[0].teacher.teacherBackground);
					$("#teacherJobDuty").html(json.Records[0].teacher.teacherJobDuty);
					$("#teacherChineselevel").html(json.Records[0].teacher.teacherChineselevel);
					$("#teacherOrganization").html(json.Records[0].teacher.teacherOrganization);
					$("#teacherIsMultiLanguage").html(json.Records[0].teacher.teacherIsMultiLanguage);
					$(".portraitTd img").attr("src","../"+json.Records[0].teacher.teacherHeadPath);
					
					for(var i = 0; i < json.Records[1].projectCycle.length; i++){
						if(json.Records[1].projectCycle[i].id > 1){
							pcId = json.Records[1].projectCycle[i].id;
						}
						btnstr+="<a class='' onclick='getInfo("+json.Records[1].projectCycle[i].startyear+","+json.Records[1].projectCycle[i].endyear+","+json.Records[1].projectCycle[i].id+")'><span>"+json.Records[1].projectCycle[i].name+"</span><b>(<span class='startyear1'>"+json.Records[1].projectCycle[i].startyear+"</span>~<span class='endyear1'>"+json.Records[1].projectCycle[i].endyear+"</span>)</b></a>"
					}
					$("#projectCycle").html(btnstr);
	        	}else{
	        		
	        	}
	        },error:function(){
	        	
	        }
	    }).done(function(){
	    	getInfo('','',pcId);
	    	$(".buttonDiv a:first").addClass("light");
			$(".buttonDiv a:last").after("<div class='clear'></div>");
			$(".buttonDiv a").click(function(){
				$(this).addClass("light").siblings().removeClass("light");
			}).each(function(index,val){
				if(index%4==0&&index>0){
					$(this).after("<div class='clear'></div>")
				}
			})
	    })
	})
	
// 	var Id = (url('?Id') != null) ? url('?Id') : '';
	function getInfo(startyear,endyear,cycleid){
		$.ajax({
	        type: "POST",
	        url: "../teacher/trainRecords_reLoad.action",
	        data: {startYear:startyear,endYear:endyear,cycleId:cycleid},
	        dataType: "text",
	        async: true,
	        success: function (data) {
	        	var json = (new Function("", "return " + data.split("&&&")[0]))();
	        	if(json.status=="OK"){
	        		var ttRcords="";
	        		if(json.records.ttRecords.length==0){
	        			if(typeof(json.records.teacherEduAdvance) != "undefined"){
		        			ttRcords+='<div class="recordDivs"><div class="data">'+json.records.teacherEduAdvance.time+
	    					'</div><div class="recordDiv" onclick=""><div class="contentDiv"><div class="line"></div><div class="icon"></div>'+''+
	    					'<div class="infomation">在'+json.records.teacherEduAdvance.graduation+'进修'+
	    					json.records.teacherEduAdvance.major+'，并顺利结业，结业证书编号：'+json.records.teacherEduAdvance.certificate+'。学历由'+json.records.teacherEduAdvance.oldBackground+
	    					'提升至'+json.records.teacherEduAdvance.background+'</div>'+''+
	    					'<div class="result">'+json.records.teacherEduAdvance.status+'</div><span class="clear"></span></div></div><span class="clear"></span></div>';
		        		}else{
		        			var ttRcord="<div class='Norecord'>暂无培训记录</div>";
			        		$(".Records").css("display","block").html(ttRcord);
		        		}
	        		}else{
	        			$(".Records").css("display","none");
	        			for (var i = 0; i < json.records.ttRecords.length; i++) {
	        				var hours = "";
	        				if(typeof(json.records.ttRecords[i].studyhour) != "undefined"){
	        					for(var m = 0; m < json.records.ttRecords[i].studyhour.length; m++){
	        						var name = json.records.ttRecords[i].studyhour[m].nameCN;
	        						var value = json.records.ttRecords[i].studyhour[m].value;
	        						var dataStr = name + '$$' + value;
	        						hours += dataStr +","
	        					}
	        					hours = hours.substring(0,hours.length - 1);
	        				}
		        			ttRcords+='<div class="recordDivs"><div class="data">'+json.records.ttRecords[i].time+
	    					'</div><div class="recordDiv" onclick="infoLayer(\''+json.records.ttRecords[i].tcName+'\',\''+
	    					json.records.ttRecords[i].traintype+'\',\''+ hours+'\',\''+
	    					json.records.ttRecords[i].trainingStatus+'\',\''+
	    					json.records.ttRecords[i].trainingScore+'\',\''+
	    					json.records.ttRecords[i].certificate+'\',\''+
	    					json.records.ttRecords[i].trainingReason+'\',\''+json.records.ttRecords[i].projectName+'\',\''+
	    					json.records.ttRecords[i].tsName+'\',\''+
        					json.records.ttRecords[i].credit+'\')"><div class="contentDiv"><div class="line"></div><div class="icon"></div>'+''+
	    					'<div class="infomation"><a href="javascript:">在'+json.records.ttRecords[i].tcName+'参加了'+
	    					json.records.ttRecords[i].projectName+'>>'+json.records.ttRecords[i].tsName+'培训</a></div>'+''+
	    					'<div class="result">'+json.records.ttRecords[i].trainingStatus+'</div><span class="clear"></span></div></div><span class="clear"></span></div>';
		        		}
	        			if(typeof(json.records.teacherEduAdvance) != "undefined"){
		        			ttRcords+='<div class="recordDivs"><div class="data">'+json.records.teacherEduAdvance.time+
	    					'</div><div class="recordDiv" onclick=""><div class="contentDiv"><div class="line"></div><div class="icon"></div>'+''+
	    					'<div class="infomation">在'+json.records.teacherEduAdvance.graduation+'进修'+
	    					json.records.teacherEduAdvance.major+'，并顺利结业，结业证书编号：'+json.records.teacherEduAdvance.certificate+'。学历由'+json.records.teacherEduAdvance.oldBackground+
	    					'提升至'+json.records.teacherEduAdvance.background+'</div>'+''+
	    					'<div class="result">'+json.records.teacherEduAdvance.status+'</div><span class="clear"></span></div></div><span class="clear"></span></div>';
		        		}
	        		}
	        		
					$(".Record").html(ttRcords);
					
					//循环学时统计表格
//					if(typeof(json.records.classHoursCount) != "undefined"){
//						var studyhoursStatus = '';
//						var studyhours = '';
//						for(var i = 0; i < json.records.classHoursCount.studyhour.length; i++){
//							var name = json.records.classHoursCount.studyhour[i].nameCN;
//							var cycleValue = json.records.classHoursCount.studyhour[i].cycleValue;
//							var teacherValue = json.records.classHoursCount.studyhour[i].teacherValue;
//							var teacherStatus = json.records.classHoursCount.studyhour[i].teacherStatus;
//							studyhoursStatus += '<tr><th>'+name+'</th>'
//											+'<td>'+cycleValue+'</td>'
//											+'<td>'+teacherValue+'</td>'
//											+'<td>'+teacherStatus+'</td></tr>';
//							studyhours += '<tr><th>'+name+'</th>'
//											+'<td>'+cycleValue+'</td>'
//											+'<td>'+teacherValue+'</td></tr>';
//						}
//						$('#studyhoursStatus').html(studyhoursStatus);
//						$('#studyhours').html(studyhours);
//					}
					if(typeof(json.records.classHoursCount) != "undefined"){
						var studyhoursStatus = '';
						var studyhours = '';
						var studyhoursStatusTitle = '';
						var studyhoursTitle = '';
						if(cycleid == 1){
							studyhoursStatusTitle += '<tr><th></th>'
								+'<th>认定标准</th>'
								+'<th>已完成学时</th>'
								+'<th>完成情况</th></tr>';
								
							studyhoursTitle += '<tr><th></th>'
								+'<th>认定标准</th>'
								+'<th>已完成学时</th>';
						} else {//去除认定标准学时显示
							studyhoursStatusTitle += '<tr><th></th>'
							+'<th>已完成学时</th>'
							+'<th>完成情况</th>';
							
							studyhoursTitle = '<tr><th></th>'
								+'<th>已完成学时</th>';
						}
						$('#studyhoursStatusTitle').html(studyhoursStatusTitle);
						$('#studyhoursTitle').html(studyhoursTitle);
						
						for(var i = 0; i < json.records.classHoursCount.studyhour.length; i++){
							var name = json.records.classHoursCount.studyhour[i].nameCN;
							var cycleValue = json.records.classHoursCount.studyhour[i].cycleValue;
							var teacherValue = json.records.classHoursCount.studyhour[i].teacherValue;
							var teacherStatus = json.records.classHoursCount.studyhour[i].teacherStatus;
							if(cycleid > 1){
								teacherStatus = '--';
								studyhoursStatus += '<tr><th>'+name+'</th>'
								+'<td>'+teacherValue+'</td>'
								+'<td>'+teacherStatus+'</td></tr>';
								
								studyhours += '<tr><th>'+name+'</th>'
								+'<td>'+teacherValue+'</td></tr>';
							} else {
								studyhoursStatus += '<tr><th>'+name+'</th>'
								+'<td>'+cycleValue+'</td>'
								+'<td>'+teacherValue+'</td>'
								+'<td>'+teacherStatus+'</td></tr>';
								
								studyhours += '<tr><th>'+name+'</th>'
								+'<td>'+cycleValue+'</td>'
								+'<td>'+teacherValue+'</td></tr>';
							}
							
						}
						$('#studyhoursStatus').html(studyhoursStatus);
						$('#studyhours').html(studyhours);
					}
					if(json.records.classHoursCount.teacherHeadPath=='null'){
						
					}else{
						$(".portraitTd img").attr("src",json.records.classHoursCount.teacherHeadPath);
					}
					
	        	}else{
	        		
	        	}
	        },error:function(){
	        	
	        }
	    }).done(function(){
	    	$(".Record .recordDiv:last .line").css("height","14px");
	    	$('#highChar').highcharts({
	            data: {
	                table: 'datatable'
	            },
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: ''
	            },
	            yAxis: {
	                allowDecimals: false,
	                title: {
	                    text: '学时'
	                }
	            },
	            tooltip: {
	                formatter: function () {
	                    return '<b>' + this.series.name + '</b><br/>' +
	                        this.point.y + ' ' + this.point.name.toLowerCase();
	                }
	            }
	        });
	    });
	}
	
	function infoLayer(val1,val3,val2,val5,val7,val9,val10,val11,val12,val14){
		var hours = val2.split(',');
		var dataStr = '';
		if(val2 !='' && hours.length > 0){
			for(var i = 0; i < hours.length; i++){
				var data = hours[i].split('$$');
				var str = '<li>获得'+data[0]+'：<span>'+data[1]+'</span></li>'
				dataStr += str;
			}
		}
		var index=layer.open({
			  type: 1,
			  title: false,
			  shadeClose: true,
			  shade: 0.6,
			  scrollbar: false,
			  area: ['830px', '300px'],
			  content: "<div class='train'><h4>"+val11+">>"+val12+"</h4><hr><ul><li>承训单位：<span>"+val1+
			  "</span></li><li>培训方式：<span>"+val3+
			  "</span></li><li>培训状态：<span>"+val5+
			  "</span></li><li>培训成绩：<span>"+val7+
			  "</span></li><li>学分：<span>"+val14+
			  dataStr+
			  "</span></li><li>证书编号：<span>"+val9+
			  "</span></li><li>不合格的原因：<span>"+val10+"</span></li></ul></div>" 
			}); 
			$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
		   	$(".layui-layer-rim").css({"border":"none","border-radius":"4px"});
	}