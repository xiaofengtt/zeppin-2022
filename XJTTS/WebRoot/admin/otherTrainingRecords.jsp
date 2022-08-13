<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">其他项目培训记录</s:param>
</s:action>
<style>
		div.jtable-main-container table.jtable {margin-top:150px}
	</style>
<div class="main">
<div class="tablewrap">
<div class="cui-menu" style="width:100%">
	<form id="otherTrainingRecords" class="form-horizontal"  action="../admin/otherTrainingRecords_output.action">
		<div class="row clearfix">
			<div class="col-md-6">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">年份</label>
					<div class="col-sm-9">
						<select class="form-control" id="year" name="year" onchange="changeYear(this)">
							<option value="0">全部</option>
							<s:iterator value="searchYear" id="yt">
								<option value='<s:property value="#yt" />'><s:property value="#yt" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">培训科目</label>
					<div class="col-sm-9">
						<select id="subjectName" class="form-control" name="subjectName">
							<option value="0">全部</option>
						</select>
					</div>
				</div>
			</div>

			<div class="col-md-6">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">项目名称</label>
					<div class="col-sm-9">
						<select class="form-control" name="projectName" id="projectName"
							onchange="changeProject(this)">
							<option value="0">全部</option>
							<s:iterator value="searchProject" id="sp">
								<option value='<s:property value="sp" />'><s:property value="sp" /></option>
							</s:iterator>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="">承训单位</label>
					<div class="col-sm-9">
						<select class="form-control" id="trainingUnit" name="trainingUnit">
							<option value="0">全部</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="row actionbar">
			<div class="text-center" style="padding-bottom:10px">			
				<button class="btn btn-primary" id="findRecord" type="button">查询</button>
				<a href="trainingList.xls" id="output" class="btn btn-primary">导出模板</a>
				<a class="ifrmbox" href="../admin/otherTrainingRecords_uploadInit.action" data-fancybox-type="iframe" >
					<button type="button" id="upload" class="btn btn-primary">上传培训记录</button>
				</a>
			</div>
		</div>
	</form>				
	</div>

		<div id="ProjectTableContainer">
		
		</div>

		<script type="text/javascript">
		$(function() {
			$(".ifrmbox").colorbox({
				iframe : true,
				width : "860px",
				height : "500px",
				opacity : '0.5',
				overlayClose : false,
				escKey : true
			});
		})
		
			$(document).ready(function() {
				$('#ProjectTableContainer').jtable({
					title : '<a href="../admin/otherTrainingRecords_initPage.action">其他项目培训记录</a>',
					messages : zhCN, //Lozalize
					paging : true, //Enable paging
					pageSize : 10, //Set page size (default: 10)
					sorting : false, //Enable sorting
					defaultSorting : 'year DASC', //Set default sorting
					dialogShowEffect : 'drop',
					selecting: true, //Enable selecting
					selectOnRowClick : false,
		            multiselect: true, //Allow multiple selecting

					actions : {
						listAction : '../admin/otherTrainingRecords_getList.action'

					},
					fields : {
						id : {
							title : 'ID',
							key : true,
							sorting:false,
							list:false
						},
						name : {
							title : '姓名',
							width : '10%',
							list : true,
							sorting:false
						},
						idcard : {
							title : '身份证',
							width : '10%',
							list : true,
							sorting:false
						},
						projectName : {
							title : '项目名称',
							width : '10%',
							list : true,
							sorting:false
						},
						trainingCollege : {
							title : '承训院校',
							list : true,
							sorting:false,
							width: '10%'
						},
						trainingSubject : {
							title : '学科',
							list : true,
							sorting:false,
							width: '5%'
						},
						trainingHour : {
							title : '集中培训学时',
							list : true,
							width: '5%'

						},
						trainingOnlineHour : {
							title : '远程培训学时',
							list : true,
							width: '5%'
						},
						startTime : {
							title : '开始时间',
							list : true,
							width: '5%'

						},
						endTime : {
							title : '结束时间',
							list : true,
							width: '5%'

						}
					},
					columnResizable : false
				});
				$('#ProjectTableContainer').jtable('load');
				//搜索
		        $('#findRecord').click(function (e) {
		            e.preventDefault();
		            $('#ProjectTableContainer').jtable('load', {
		            	year: $('#year').val(),
						projectName:$('select[name="projectName"]').val(),
		                trainingUnit: $('#trainingUnit').val(),
						subjectName : $('#subjectName').val(),
		            });
					return false;
		        });
			});
		</script>
	</div>
</div>
<script>
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/otherTrainingRecords_getProjectList.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
			$('#subjectName').html('<option value="0">全部</option>');
			$('#trainingUnit').html('<option value="0">全部</option>');
		})
	}

	function changeProject(t) {
		var pname = $(t).val();
		$.getJSON('../admin/otherTrainingRecords_getSearshList.action?projectName='+ pname, function(data) {
				
				if (data.subjects.length > 0) {
					var cLis = '';
					$.each(data.subjects, function(i, c) {
						cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
					});
					$('#subjectName').html(cLis);
				} else {
					$('#subjectName').html('<option value="0">暂无数据</option>');
				}
				
				if (data.trainingUnits.length > 0) {
					var cLis = '';
					$.each(data.trainingUnits, function(i, c) {
						cLis += '<option value="' +c.id + '">' + c.name+ '</option>';
					});
					$('#trainingUnit').html(cLis);
				} else {
					$('#trainingUnit').html('<option value="0">暂无数据</option>');
				}

			});
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>