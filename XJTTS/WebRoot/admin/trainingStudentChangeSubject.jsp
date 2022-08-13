<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">调整学员培训学科</s:param>
</s:action>
<style>div.jtable-main-container table.jtable {margin-top:15px}
div.jtable-main-container > div.jtable-title div.jtable-title-text{line-height:58px;font-size:18px;}
.cui-menu{top:60px;}
div.jtable-main-container > table.jtable > thead th{text-align:center;}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td{text-align:center;}
div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container{height:auto;}
</style>
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>

<div class="main">
<div class="iconDiv">
			<a class="btn btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选学员</b>
				</p>
			</a>
</div>
	<div class="tablewrap">
				<div class="cui-menu" style="width:100%">
<!-- 					<a class="btn btn-primary btn-screen"> -->
<!-- 					筛选学员 </a> -->
					<form id="aduTaskTeacher" class="form-horizontal"  action="../admin/trainingStudentOpt_outputAduPage.action" style="display:none;padding-top:15px;">
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
									<label class="col-sm-2 control-label" for="">项目名称</label>
									<div class="col-sm-9">
										<select class="form-control" name="projectName" id="projectName">
											<option value="-1" search="全部">全部</option>
											<s:iterator value="lstProjects" id="rt">
												<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'><s:property
														value="#rt.getName()" /></option>
											</s:iterator>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">培训科目</label>
									<div class="col-sm-9">
										<select id="subjectName" class="form-control" name="subjectId">
											<option value="-1" search="全部">全部</option>
											<s:iterator value="lsttTrainingSubjects" id="ts">
											<option value="<s:property value="#ts.getId()" />" search="<s:property value="#ts.getName()" />"><s:property
														value="#ts.getName()" /></option>
											</s:iterator>
										</select>
									</div>
								</div>
								
														
							</div>

							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">学员姓名</label>
									<div class="col-sm-9">
										<input class="form-control" name="teacherName" id="teacherName" type="text">
									</div>
								</div>	
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">身份证号</label>
									<div class="col-sm-9">
										<input class="form-control" name="teacherIdCard" id="teacherIdCard" type="text">
									</div>
								</div>
								
							</div>
						</div>

						<div class="row actionbar">
							
						</div>
						<div class="text-center">
									<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
								</div>
					</form>
				
				</div>
		<div id="ProjectTableContainer"></div>

<script type="text/javascript">

$(".btn-screen").click(function(e){
	e.preventDefault();
	$('#aduTaskTeacher').toggle();
	if($('#aduTaskTeacher').css("display")=="block"){
		$("div.jtable-main-container table.jtable").css("margin-top","210px");
	}else{
		$("div.jtable-main-container table.jtable").css("margin-top","15px");
	}
})
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttRecord_getTrainingProject.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
		})
	}

	$(document)
			.ready(
					function() {
						$('#projectName').select2(); 
						$('#subjectName').select2(); 
						$('#ProjectTableContainer')
								.jtable(
										{
											title : '<a href="../admin/trainingStudentOpt_initPage.action">承训单位学员管理</a> >> 调整参训学员培训学科',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'year DASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/trainingStudentOpt_getChangeSubjectStuList.action'

											},
											fields : {
												id : {
													title : 'ID',
													key : true,
													width: '5%'
												},
												name : {
													title : '姓名',
													width : '10%',
													list : true
												},
												idCard : {
													title : '身份证号',
													width : 'auto',
													list : true
													
												},
												mobile : {
													title : '手机号',
													width : '10%',
													list : true
												},
												
												sex : {
													title : '性别',
													list : true,
													width: 'auto'
												},
												organazation : {
													title : '单位',
													list : true,
													width: '12%'
												},
												project : {
													title : '报名项目',
													list : true,
													width: '20%'
												},
												trainingSubject : {
													title : '报名学科',
													list : true,
													width: 'auto'
												},
												trainingStatus : {
													title : '培训状态',
													list : true,
													width: '9%'
												},
												
												customedit : {
													title : '操作',
													width : '10%',
													sorting : false,
													edit : false,
													create : false,
													visibility : 'fixed',
													display : function(data) {
														var html = '<a class="ifrbox" data-fancybox-type="iframe" href="../admin/trainingStudentOpt_changeTeacherSubjectInit.action?id='
																+ data.record.id
																+ '">调整培训学科</a>';
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
									projectId:$('select[name="projectName"]').val(),
									subjectId : $('#subjectName').val(),
									teacherIdCard : $('#teacherIdCard').val(),
									teacherName : $('#teacherName').val()
			               
					            });
								return false;
					        });		
								
					});
	
	
	
	
	
</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>