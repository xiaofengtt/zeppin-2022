<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">查看学员成绩</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
	<style>
		div.jtable-main-container table.jtable {margin-top:150px}
	</style>

<script src="../js/iframe.js"></script>
<div class="main">

	<div class="listwrap">
		<div class="list-content clearfix" >
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			
			<div class="tablewrap">
				<div class="cui-menu" style="width:100%">
					<form id="aduTaskTeacher" class="form-horizontal" role="form"action="../admin/ttRecord_outputScore.action">
						<div class="row clearfix">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">年份</label>
									<div class="col-sm-9">
										<select class="form-control" name="year" onchange="changeYear(this)">
											<option value="0">全部</option>
											<s:iterator value="searchYear" id="yt">
												<option value='<s:property value="#yt" />' <s:if test="#yt==selectYear"> selected </s:if> ><s:property
														value="#yt" /></option>
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
		
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">姓名/身份证号</label>
									<div class="col-sm-9">
										<input name="teacherName"  class="form-control" >
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
											<s:iterator value="searchReportTask" id="rt">
												<option value='<s:property value="#rt.getId()" />'><s:property
														value="#rt.getName()" /></option>
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
								<div class="form-group" style="margin-left:15px">
									<button class="btn btn-primary" id="findRecord" type="button">查询</button>
									<button class="btn btn-primary" id="" type="submit">导出</button>
								</div>
							</div>
						</div>

					</form>
				
				</div>
				<div id="ProjectTableContainer"></div>
				
				<script type="text/javascript">
					$(document).ready(function() {
						$('#ProjectTableContainer')
								.jtable(
										{
											title : '<a href="../admin/ttRecord_initMarkPage.action">查看学员成绩</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'id ASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/ttRecord_getSearchMark.action'


											},
											fields : {
												id : {
													title : 'ID',
													key : true,
													width : '4%'
												},
												projectName : {
													title : '项目',
													width : '13%',
													
													visibility : 'fixed'
												},
												subjectName : {
													title : '培训科目',
													width : '12%',
													
												},
												trainingName : {
													title : '承训机构',
													width : '7%'
												},
												name : {
													title : '姓名',
													width : '6%',
													list : true
												},
												schoolName : {
													title : '单位',
													width : '6%',
													list : true
												},
												mark : {
													title : '成绩',
													width : '4%',
													list : true
												},
												trainingClasshour : {
													title : '培训学时',
													width : '4%',
													list : true
												},
												onlineHour : {
													title : '网络培训学时',
													width : '4%',
													list : true
												},
												certificate : {
													title : '证书编号',
													width : '4%',
													list : true
												},
												status : {
													title : '状态',
													width : '4%',
													list : true
												},
												custom : {
													title:'操作',
													width:'5%',
													sorting : false,
													edit : false,
													create : false,
													visibility : 'fixed',
													display : function(data) {
													
														
														if(data.record.show) {
															var html = '<a target="_blank" href="../paper/report_detailTeacher.action?uuid=' +data.record.uuid +'">详细</a>';
														}else {
															var	html ='';
														}
														
														return html;
													}
												}
										
											},
											columnResizable : false
											

										});

								$('#ProjectTableContainer').jtable('load');
								
								//搜索
						        $('#findRecord').click(function (e) {
						            e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
										projectName:$('select[name="projectName"]').val(),
						                trainingUnit: $('#trainingUnit').val(),
										subjectName : $('#subjectName').val(),
										teacherName : $('input[name="teacherName"]').val()
				               
						            });
									return false;
						        });
							
								
					});
					
					
				</script>
				
				
			</div>
		</div>

	</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>

<script>
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttRecord_getAssignTaskProject.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据<option>');
			}
			$('#subjectName').html('<option value="0">全部<option>');
			$('#trainingUnit').html('<option value="0">全部<option>');
		})
	}

	function changeProject(t) {
		var vid = $(t).val();
		$.getJSON(
			'../admin/ttRecord_getAssignTaskOrganization.action?projectId='
					+ vid, function(data) {
				
				if (data.subjects.length > 0) {
					var cLis = '';
					$.each(data.subjects, function(i, c) {
						cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
					});
					$('#subjectName').html(cLis);
				} else {
					$('#subjectName').html('<option value="0">暂无数据<option>');
				}
				
				if (data.trainingUnits.length > 0) {
					var cLis = '';
					$.each(data.trainingUnits, function(i, c) {
						cLis += '<option value="' +c.id + '">' + c.name+ '</option>';
					});
					$('#trainingUnit').html(cLis);
				} else {
					$('#trainingUnit').html('<option value="0">暂无数据<option>');
				}

			});
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>