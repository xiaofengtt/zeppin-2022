<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/url.min.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>
<link rel="stylesheet" href="../css/bootstrap.css">
<style type="text/css">
	*{margin: 0 auto;padding: 0;}
	.clear{clear: both !important;display: block;}
	h3{margin-top: 20px;}
	h4{margin: 30px 0 20px 0;}
	.buttonDiv{margin-top: 40px;}
	.buttonDiv a{width: 18%;text-align: center;display: block;float: left;cursor: pointer;border: 1px solid #4489ce;border-radius:4px;padding: 5px 10px;margin: 5px 15px;font-size: 14px;margin-left: 0px;box-sizing:border-box;}
	.buttonDiv a b{font-weight: normal;display: block;}
	.buttonDiv a:hover{text-decoration: none;background: #4489ce; border-color:#4489ce;color: #ffffff;}
	.buttonDiv a.light{background: #4489ce; border-color:#4489ce;color: #ffffff;}
	.Record{border-left:2px solid #4489ce;padding-left:24px;margin:30px 20px;}
	.Record .recordDiv{position: relative;margin-bottom: 16px;min-height: 20px;}
	.Record .recordDiv div{float: left;color: #333;font-size: 14px;}
	.Record .recordDiv .icon{width: 16px;background: url(../img/icon-1.png) center no-repeat;position: absolute;left: -33.5px;height:20px;}
	.Record .recordDiv:hover .icon{background: url(../img/icon-2.png) center no-repeat;}
	.Record .recordDiv .infomation a{color: #333;line-height: 20px;}
	.Record .recordDiv .data{width: 20%;}
	.Record .recordDiv .infomation {width: 70%;}
	.Record .recordDiv .result{width: 10%;text-align: right;}
	.table1{margin-bottom:0px;border-bottom:0px;margin-top: 20px;}
	.table1 tr.lastChild td{border-bottom:0px;}
	td.portraitTd{text-align: center;vertical-align: middle !important;border-width: 3px !important;}
	#list-content{max-width:1100px;}
	.train{padding:20px 50px;}
	.train ul{padding:20px;}
	.train ul li{float:left;width:48%;line-height:30px;font-size:14px;color:#333333;margin-right:10px;}
	.Norecord{color:#b8b8b8;font-size:24px;text-align:center;line-height:120px;height:120px;}
	#highChar{margin-top:60px;}
	.portraitTd{overflow:hidden;height:210px;}
	.portraitTd img{max-width:100%;max-height:210px;}
</style>
<div class="main">
	<div class="listwrap">

		</div>
		<div id="list-content" class="list-content clearfix">
		<h2 class="text-center">??????????????????</h2>
			<table class="table table-bordered table1">
				<tr>
					<td width="16.21%" class="text-right">?????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.name" /></td>
					<td width="16.21%" class="text-right">???????????????</td>
					<td width="22.99%" class="text-center"><s:property value="teacher.mobile" /></td>
					<s:if test='teacher.headPicPath!= null'>
						<td rowspan="6" width="15.74%" class="portraitTd">
							<img src="../<s:property value="teacher.headPicPath" />" alt="??????" width="">
						</td>
					</s:if>
					<s:elseif test='%{teacher.headPicPath == null && teacherEx.sexString=="???"}'>
						<td rowspan="6" width="15.74%" class="portraitTd">					
							<img src="../img/women.png" alt="??????" width="">
						</td>
					</s:elseif>
					<s:elseif test='%{teacher.headPicPath == null && teacherEx.sexString=="???"}'>
						<td rowspan="6" width="15.74%" class="portraitTd">					
							<img src="../img/USER.png" alt="??????" width="">
						</td>
					</s:elseif>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">?????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.sexString" /></td>
					<td width="16.21%" class="text-right">?????????</td>
					<td width="22.99%" class="text-center"><s:property value="teacher.ethnic.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">???????????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.idcard" /></td>
					<td width="16.21%" class="text-right">???????????????</td>
					<td width="22.99%" class="text-center"><s:property value="teacher.politics.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">?????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.teachingAge" /></td>
					<td width="16.21%" class="text-right">?????????????????????</td>
					<td width="22.99%" class="text-center"><s:property value="teacherEx.mainTeachingCourse.subject.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">???????????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.statusString" /></td>
					<td width="16.21%" class="text-right">?????????????????????</td>
					<td width="22.99%" class="text-center"><s:property value="teacherEx.mainTeachingClass.grade.name" /></td>
				</tr>
				<tr class="lastChild">
					<td width="16.21%" class="text-right">?????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacherEx.authorized" /></td>
					<td width="16.21%" class="text-right">?????????????????????</td>
					<td width="22.99%" class="text-center"><s:property value="teacherEx.mainTeachingLanguage.language.name" /></td>
				</tr>
			</table>
			<table class="table table-bordered table2">
				<tr>
					<td width="16.21%" class="text-right">?????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.jobTitle.name" /></td>
					<td width="16.21%" class="text-right">???????????????</td>
					<td width="38.73%" class="text-center"><s:property value="teacher.eductionBackground.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">?????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.jobDuty.name" /></td>
					<td width="16.21%" class="text-right">??????????????????</td>
					<td width="38.73%" class="text-center"><s:property value="teacher.chineseLanguageLevel.name" /></td>
				</tr>
				<tr>
					<td width="16.21%" class="text-right">???????????????</td>
					<td width="28.74%" class="text-center"><s:property value="teacher.organization.name" /></td>
					<td width="16.21%" class="text-right">?????????????????????</td>
					<td width="38.73%" class="text-center"><s:property value="teacherEx.isMultiLanguage" /></td>
				</tr>
			</table>
			<div class="buttonDiv">
				<s:iterator value="cycleHash" id="">				
					<a class="" onclick="getInfo(<s:property value="value[0]" />,<s:property value="value[1]" />,<s:property value="key" />)"><span><s:property value="value[2]" /></span><b>(<span class="startyear<s:property value="key" />"><s:property value="value[0]" /></span>~<span class="endyear<s:property value="key" />"><s:property value="value[1]" /></span>)</b></a>						
				</s:iterator>
			</div>

			<h3 style="margin-top: 30px;">????????????</h3>
			<div class="Record">
				
			</div>
			<div class="Records">
				
			</div>

			<h3>????????????</h3>
			<h4 class="text-center">????????????????????????</h4>
			<table class="table table-bordered table3 text-center">
				<tr><th></th><th class="text-center">??????????????????</th><th class="text-center">????????????????????????</th><th class="text-center">????????????????????????</th><th class="text-center">??????????????????</th></tr>
				<tr><td>????????????</td><td class="cycleCentralize"></td><td class="cycleInformation"></td><td class="cycleRegional"></td><td class="cycleSchool"></td></tr>
				<tr><td>???????????????</td><td class="teacherCentralize"></td><td class="teacherInformation"></td><td class="teacherRegional"></td><td class="teacherSchool"></td></tr>
				<tr><td>????????????</td><td class="centralizeStatus"></td><td class="informationStatus"></td><td class="regionalStatus"></td><td class="schoolStatus"></td></tr>
			</table>
			<table class="table table-bordered text-center" id="datatable" style="display:none;">
				<thead>
			        <tr>
			            <th></th>
			            <th>????????????</th>
			            <th>???????????????</th>
			        </tr>
			    </thead>
			    <tbody>
			        <tr>
			            <th>??????????????????</th>
			            <td class="cycleCentralize"></td>
			            <td class="teacherCentralize"></td>
			        </tr>
			        <tr>
			            <th>????????????????????????</th>
			            <td class="cycleInformation"></td>
			            <td class="teacherInformation"></td>
			        </tr>
			        <tr>
			            <th>????????????????????????</th>
			            <td class="cycleRegional"></td>
			            <td class="teacherRegional"></td>
			        </tr>
			        <tr>
			            <th>??????????????????</th>
			            <td class="cycleSchool"></td>
			            <td class="teacherSchool"></td>
			        </tr>
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
		getInfo('','');
	})
	var Id = (url('?Id') != null) ? url('?Id') : '';
	Id=44781;
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
	        			var ttRcord="<div class='Norecord'>??????????????????</div>"
	        			$(".Records").css("display","block").html(ttRcord);
	        		}else{
	        			$(".Records").css("display","none");
	        		}
	        		for (var i = 0; i < json.records.ttRecords.length; i++) {
	        			ttRcords+='<div class="recordDiv"><div class="icon"></div><div class="data">'+json.records.ttRecords[i].time+'</div><div class="infomation"><a href="javascript:" onclick="infoLayer(\''+json.records.ttRecords[i].tcName+'\',\''+json.records.ttRecords[i].centralize+'\',\''+json.records.ttRecords[i].traintype+'\',\''+json.records.ttRecords[i].information+'\',\''+json.records.ttRecords[i].trainingStatus+'\',\''+json.records.ttRecords[i].regional+'\',\''+json.records.ttRecords[i].trainingScore+'\',\''+json.records.ttRecords[i].school+'\',\''+json.records.ttRecords[i].certificate+'\',\''+json.records.ttRecords[i].trainingReason+'\',\''+json.records.ttRecords[i].projectName+'\')">???'+json.records.ttRecords[i].tcName+'?????????'+json.records.ttRecords[i].projectName+'???'+json.records.ttRecords[i].tsName+'??????</a></div><div class="result">'+json.records.ttRecords[i].trainingStatus+'</div><span class="clear"></span></div>';
	        		}
					$(".Record").html(ttRcords);
					$(".cycleCentralize").html(json.records.classHoursCount.cycleCentralize);
					$(".cycleInformation").html(json.records.classHoursCount.cycleInformation);
					$(".cycleRegional").html(json.records.classHoursCount.cycleRegional);
					$(".cycleSchool").html(json.records.classHoursCount.cycleSchool);
					$(".teacherCentralize").html(json.records.classHoursCount.teacherCentralize);
					$(".teacherInformation").html(json.records.classHoursCount.teacherInformation);
					$(".teacherRegional").html(json.records.classHoursCount.teacherRegional);
					$(".teacherSchool").html(json.records.classHoursCount.teacherSchool);
					$(".centralizeStatus").html(json.records.classHoursCount.centralizeStatus);
					$(".informationStatus").html(json.records.classHoursCount.informationStatus);
					$(".regionalStatus").html(json.records.classHoursCount.regionalStatus);
					$(".schoolStatus").html(json.records.classHoursCount.schoolStatus);
					if(json.records.classHoursCount.teacherHeadPath=='null'){
						
					}else{
						$(".portraitTd img").attr("src",json.records.classHoursCount.teacherHeadPath);
					}
					
	        	}else{
	        		
	        	}
	        },error:function(){
	        	
	        }
	    }).done(function(){
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
	                    text: '??????'
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
	
	function infoLayer(val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11){
		var index=layer.open({
			  type: 1,
			  title: false,
			  shadeClose: true,
			  shade: 0.6,
			  offset: ['center'],
			  scrollbar: false,
			  area: ['830px', '300px'],
			  content: "<div class='train'><h3 class='text-center'>"+val11+"</h3><ul><li>???????????????<span>"+val1+"</span></li><li>???????????????????????????<span>"+val2+"</span></li><li>???????????????<span>"+val3+"</span></li><li>?????????????????????????????????<span>"+val4+"</span></li><li>???????????????<span>"+val5+"</span></li><li>?????????????????????????????????<span>"+val6+"</span></li><li>???????????????<span>"+val7+"</span></li><li>???????????????????????????<span>"+val8+"</span></li><li>???????????????<span>"+val9+"</span></li><li>?????????????????????<span>"+val10+"</span></li></ul></div>" 
			}); 
			$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
		   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
	}

</script>

