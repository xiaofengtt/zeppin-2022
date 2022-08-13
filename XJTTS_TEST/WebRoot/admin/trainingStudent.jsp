<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员报到及成绩管理</s:param>
</s:action>
<style>div.jtable-main-container table.jtable {margin-top:150px}</style>
<div class="main">

	<div class="tablewrap">
				<div class="cui-menu" style="width:100%">
					<form id="aduTaskTeacher" class="form-horizontal"  action="../admin/trainingStudentOpt_outputAduPage.action">
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
											<option value="-1">全部</option>
											<s:iterator value="lsttTrainingSubjects" id="ts">
											<option value="<s:property value="#ts.getId()" />"><s:property
														value="#ts.getName()" /></option>
											</s:iterator>
										</select>
									</div>
								</div>							
							</div>

							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">项目名称</label>
									<div class="col-sm-9">
										<select class="form-control" name="projectName" id="projectName">
											<option value="-1">全部</option>
											<s:iterator value="lstProjects" id="rt">
												<option value='<s:property value="#rt.getId()" />'><s:property
														value="#rt.getName()" /></option>
											</s:iterator>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">报到状态</label>
									<div class="col-sm-9">
										<select id="Status" class="form-control" name="Status">
											<option value="-1">全部</option>
											<s:iterator value="lstStatus" id="ls">
												<option value='<s:property value="ls" />'><s:property
														value="ls" /></option>
											</s:iterator>
										</select>
									</div>
								</div>	
							</div>
						</div>

						<div class="row actionbar">
							<div class="text-center">
								<button class="btn btn-primary" id="findRecord" type="button">查询</button>
								
								<button class="btn btn-primary" id="outputAdu" type="submit">导出学员信息</button>
							</div>
						</div>
					</form>
				
				</div>
		<div id="ProjectTableContainer"></div>

<script type="text/javascript">
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttRecord_getTrainingProject.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据<option>');
			}
		})
	}

	$(document)
			.ready(
					function() {
						$('#ProjectTableContainer')
								.jtable(
										{
											title : '<a href="../admin/trainingStudentOpt_initPage.action">承训单位学员管理</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'year DASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/trainingStudentOpt_getTrainingList.action'

											},
											fields : {
												id : {
													title : 'ID',
													key : true,
													width: '5%'
												},
												year : {
													title : '时间',
													width : '10%',
													list : true
												},
												project : {
													title : '项目',
													width : 'auto',
													list : true
													
												},
												subject : {
													title : '学科',
													width : '10%',
													list : true
												},
												
												assignNum : {
													title : '分配人数',
													list : true,
													width: '9%'
												},
												registerNum : {
													title : '报送人数',
													list : true,
													width: '9%'
												},
												abnormalNum : {
													title : '异动人数',
													list : true,
													width: '9%'
												},
												status : {
													title : '培训状态',
													list : true,
													width: '9%'
												},
												
												customedit : {
													title : '操作',
													width : '16%',
													sorting : false,
													edit : false,
													create : false,
													visibility : 'fixed',
													display : function(
															data) {
														var html = '<a  href="../admin/trainingStudentOpt_registInit.action?id='
																+ data.record.id
																+ '">学员报到</a>    <a  href="../admin/trainingStudentOpt_scoreInit.action?id='
																+ data.record.id
																+ '">成绩管理</a>    <a class="bigifrmbox" data-fancybox-type="iframe" href="../admin/trainingStudentOpt_authority.action?id='
																+ data.record.id
																+ '">查看权限</a>';
														return html;
													}
												}
											},
											columnResizable : false,
											
											recordsLoaded :function (data) {
												$(".ifrbox").colorbox({
													iframe : true,
													width : "860px",
													height: "550px",
													opacity : '0.5',
													overlayClose : false,
													escKey : true
												});
												$(function() {//添加按钮
													$(".bigifrmbox").colorbox({
														iframe : true,
														width : "880px",
														height : "800px",
														opacity : '0.5',
														overlayClose : false,
														escKey : true
													});
												})
												
											}

										});
							$('#ProjectTableContainer').jtable('load');
								
							//搜索
					        $('#findRecord').click(function (e) {
					            e.preventDefault();
					            $('#ProjectTableContainer').jtable('load', {
									projectName:$('select[name="projectName"]').val(),
									subjectName : $('#subjectName').val()
									
			               
					            });
								return false;
					        });	
							
					      //调整学科
					        $('#changeSubject').click(function (e) {
					            e.preventDefault();
					            window.location.href="../admin/trainingStudentOpt_changeSubjectInit.action";

								return false;
					        });	
								
					});
	
					
	
	
	
	
	
</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>