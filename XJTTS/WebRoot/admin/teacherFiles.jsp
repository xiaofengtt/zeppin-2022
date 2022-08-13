<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师档案</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>
<style type="text/css">
	*{margin: 0 auto;padding: 0;}
	.clear{clear: both !important;display: block;}
	h3{margin-top: 20px;}
	h4{margin: 10px 0 20px 0;}
	.buttonDiv{margin-top: 40px;}
	.buttonDiv a{width: 18%;text-align: center;display: block;float: left;cursor: pointer;border: 1px solid #4489ce;border-radius:4px;padding: 5px 10px;margin: 5px 15px;font-size: 14px;margin-left: 0px;box-sizing:border-box;}
	.buttonDiv a b{font-weight: normal;display: block;}
	.buttonDiv a:hover{text-decoration: none;background: #4489ce; border-color:#4489ce;color: #ffffff;}
	.buttonDiv a.light{background: #4489ce; border-color:#4489ce;color: #ffffff;}
	.Record{margin:30px 20px;}
	.Record .recordDiv{position: relative;min-height: 20px;float: left;padding-left:24px;width:80%;}
	.Record .recordDiv div{float: left;color: #333;font-size: 14px;}
	.Record .recordDiv .line{position: absolute;top:11px;width:2px;height:100%;background:#4489ce;left:-2px;}
	.Record .recordDiv .icon{width: 37px;background: url(../img/icon-1.png) center no-repeat;position: absolute;left: -9px;height:20px;}
	.Record .recordDiv:hover .icon{background: url(../img/icon-2.png) center no-repeat;width:28px;left:-9px;}
	.Record .recordDiv .infomation a{color: #333;line-height: 20px;}
	.Record .recordDivs .data{float: left;padding:8px 15px 8px 0px;text-align:right;line-height:20px;}
	.Record .recordDiv .contentDiv{background:#fff;color:#fff;width:100%;padding:8px 9px;border-radius:4px;margin-left:-4px;}
	.Record .recordDiv:hover .contentDiv{background:#4489ce;color:#fff;cursor:pointer;}
	.Record .recordDiv:hover .infomation a{color:#fff;text-decoration: none;}
	.Record .recordDiv:hover .result{color:#fff;}
	.Record .recordDiv .infomation {width: 80%;}
	.Record .recordDiv .result{width: 20%;text-align: right;}
	.table1{margin-bottom:0px;border-bottom:0px;margin-top: 20px;}
	.table1 tr.lastChild td{border-bottom:0px;}
	td.portraitTd{text-align: center;vertical-align: middle !important;border-width: 3px !important;}
	#list-content{max-width:1100px;}
	.train{padding:20px 50px;}
	.train ul{padding:10px 20px;}
	.train ul li{float:left;width:48%;line-height:30px;font-size:13px;color:#808080;margin-right:10px;}
	.Norecord{color:#b8b8b8;font-size:24px;text-align:center;line-height:120px;height:120px;}
	#highChar{margin-top:60px;}
	.portraitTd{overflow:hidden;height:210px;}
	.portraitTd img{max-width:100%;max-height:210px;}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">教师培训档案</div>

		</div>
		<div id="list-content" class="list-content clearfix">
		<h2 class="text-center">教师培训档案</h2>
			<table class="table table-bordered table1">
				<tr>
					<td width="16.21%" class="text-right">姓名：</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.name" /></td>
					<td width="16.21%" class="text-right">联系电话：</td>
					<td width="22.99%" class="text-center"><s:property value="teacher.mobile" /></td>
					<s:if test='teacher.headPicPath!= null'>
						<td rowspan="6" width="15.74%" class="portraitTd">
							<img src="../<s:property value="teacher.headPicPath" />" alt="头像" width="">
						</td>
					</s:if>
					<s:elseif test='%{teacher.headPicPath == null && teacherEx.sexString=="女"}'>
						<td rowspan="6" width="15.74%" class="portraitTd">					
							<img src="../img/women.png" alt="头像" width="">
						</td>
					</s:elseif>
					<s:elseif test='%{teacher.headPicPath == null && teacherEx.sexString=="男"}'>
						<td rowspan="6" width="15.74%" class="portraitTd">					
							<img src="../img/USER.png" alt="头像" width="">
						</td>
					</s:elseif>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">性别：</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.sexString" /></td>
					<td width="16.21%" class="text-right">民族：</td>
					<td width="22.99%" class="text-center"><s:property value="teacher.ethnic.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">身份证号：</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.idcard" /></td>
					<td width="16.21%" class="text-right">政治面貌：</td>
					<td width="22.99%" class="text-center"><s:property value="teacher.politics.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">教龄：</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.teachingAge" /></td>
					<td width="16.21%" class="text-right">主要教学学科：</td>
					<td width="22.99%" class="text-center"><s:property value="teacherEx.mainTeachingCourse.subject.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">教师状态：</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.statusString" /></td>
					<td width="16.21%" class="text-right">主要教学学段：</td>
					<td width="22.99%" class="text-center"><s:property value="teacherEx.mainTeachingClass.grade.name" /></td>
				</tr>
				<tr class="lastChild">
					<td width="16.21%" class="text-right">编制：</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.authorized" /></td>
					<td width="16.21%" class="text-right">主要教学语言：</td>
					<td width="22.99%" class="text-center"><s:property value="teacherEx.mainTeachingLanguage.language.name" /></td>
				</tr>
			</table>
			<table class="table table-bordered table2">
				<tr>
					<td width="16.21%" class="text-right">职称：</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.jobTitle.name" /></td>
					<td width="16.21%" class="text-right">最高学历：</td>
					<td width="38.73%" class="text-center"><s:property value="teacher.eductionBackground.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">职务：</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.jobDuty.name" /></td>
					<td width="16.21%" class="text-right">汉语言水平：</td>
					<td width="38.73%" class="text-center"><s:property value="teacher.chineseLanguageLevel.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">所在学校：</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.organization.name" /></td>
					<td width="16.21%" class="text-right">是否双语教学：</td>
					<td width="38.73%" class="text-center"><s:property value="teacherEx.isMultiLanguage" /></td>
				</tr>
			</table>
			<div class="buttonDiv">
				<s:iterator value="cycleHash" id="" status="index">
					<s:if test="#index.index == 0">
						<input type="hidden" id="pcId" value="<s:property value="key" />" />
					</s:if>				
					<a class="" onclick="getInfo(<s:property value="value[0]" />,<s:property value="value[1]" />,<s:property value="key" />)"><span><s:property value="value[2]" /></span><b>(<span class="startyear<s:property value="key" />"><s:property value="value[0]" /></span>~<span class="endyear<s:property value="key" />"><s:property value="value[1]" /></span>)</b></a>						
				</s:iterator>
			</div>

			<h3 style="margin-top: 30px;">培训记录</h3>
			<div class="Record">
				
			</div>
			<div class="Records">
				
			</div>

			<h3>认定学时</h3>
			<h4 class="text-center">教师学时认定情况</h4>
			<table class="table table-bordered table3 text-center">
				<thead id="studyhoursStatusTitle">
					<tr>
			            <th></th>
			            <th>认定标准</th>
			            <th>已完成学时</th>
			            <th>完成情况</th>
			        </tr>
		        </thead>
		        <tbody id="studyhoursStatus">
		        
		        </tbody>
			</table>
			<table class="table table-bordered text-center" id="datatable" style="display:none;">
				<thead id="studyhoursTitle">
			        <tr>
			            <th></th>
			            <th>认定标准</th>
			            <th>已完成学时</th>
			        </tr>
			    </thead>
			    <tbody id="studyhours">
			    </tbody>
			</table>
			<div id="highChar"></div>	
		</div>
	</div>


<script type="text/javascript">
	$(function(){
		$(".buttonDiv a:first").addClass("light");
		$(".buttonDiv a:last").after("<div class='clear'></div>");
		$(".buttonDiv a").click(function(){
			$(this).addClass("light").siblings().removeClass("light");
		}).each(function(index,val){
			if(index%4==0&&index>0){
				$(this).after("<div class='clear'></div>")
			}
		})
		var cycleid = $('#pcId').val();
		getInfo('','',cycleid);
	})
	var Id = (url('?Id') != null) ? url('?Id') : '';
	function getInfo(startyear,endyear,cycleid){
		$.ajax({
	        type: "POST",
	        url: "../admin/personalFile_reLoad.action",
	        data: {Id:Id,startYear:startyear,endYear:endyear,cycleId:cycleid},
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
        					json.records.ttRecords[i].credit+'\',\''+json.records.ttRecords[i].show+'\',\''+json.records.ttRecords[i].uuid+'\')"><div class="contentDiv"><div class="line"></div><div class="icon"></div>'+''+
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
	
	function infoLayer(val1,val3,val2,val5,val7,val9,val10,val11,val12,val14,val15,val16){
		if(val15 == "true"){
			val16='&nbsp;&nbsp<a target="_blank" href="../paper/report_detailTeacher.action?uuid=' +val16 +'">查看详细</a>'
		}else{
			val16='';
		}
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
			  area: ['830px', '320px'],
			  content: "<div class='train'><h4>"+val11+">>"+val12+"</h4><hr><ul><li>承训单位：<span>"+val1+
			  "</span></li><li>培训方式：<span>"+val3+
			  "</span></li><li>培训状态：<span>"+val5+
			  "</span></li><li>培训成绩：<span>"+val7+
			  "</span>"+val16+"</li><li>学分：<span>"+val14+
			  dataStr+
			  "</span></li><li>证书编号：<span>"+val9+
			  "</span></li><li>不合格的原因：<span>"+val10+"</span></li></ul></div>" 
			}); 
			$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
		   	$(".layui-layer-rim").css({"border":"none","border-radius":"4px"});
	}

</script>


<jsp:include page="foot.jsp"></jsp:include>