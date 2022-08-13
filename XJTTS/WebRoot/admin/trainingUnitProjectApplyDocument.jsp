<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">材料上传情况一览</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<style>div.jtable-main-container table.jtable {margin-top:15px}
div.jtable-main-container > div.jtable-title div.jtable-title-text{line-height:58px;font-size:18px;}
.cui-menu{top:60px;}
div.jtable-main-container > table.jtable > thead th{text-align:center;}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td{text-align:center;}
div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container{height:auto;}
</style>
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
					<b>筛选</b>
				</p>
			</a>
</div>
	<div class="tablewrap">
<div class="cui-menu" style="width:100%">
<!-- 	<a class="btn btn-primary btn-screen"> -->
<!-- 					筛选 </a> -->
					<form id="aduTaskTeacher" class="form-horizontal" role="form" action="../admin/trainingUnitProjectApply_outputProjectApplyList.action" style="display:none;padding-top:15px;">
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
							</div>
						</div>

						<div class="row actionbar">
							<div class="text-center">
								<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
<!-- 								<button class="btn btn-primary" id="outputRecord" type="submit">导出</button> -->
							</div>
						</div>
					</form>
				
				</div>
		<div id="ProjectTableContainer"></div>

<script type="text/javascript">

$(".btn-screen").click(function(e){
	e.preventDefault();
	$('#aduTaskTeacher').toggle();
	if($('#aduTaskTeacher').css("display")=="block"){
		$("div.jtable-main-container table.jtable").css("margin-top","175px");
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
											title : '<a href="javascript:void(0);">材料上传情况一览</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'id DASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/trainingUnitProjectApply_getDocumentList.action'

											},
											fields : {
												id: {
													title : 'ID',
													key : true,
													width: '5%'
												},
												projectName : {
													title : '申报项目',
													width : '30%',
													list : true
												},
												trainingSubject: {
													title : '申报学科',
													width : '20%',
													list : true
													
												},
												projectApplyReport: {
													title : '申报书',
													width : '8%',
													list : true
												},
												startMessageReport: {
													title : '开班通知',
													width : '8%',
													list : true
												},
												workReport: {
													title : '工作简报',
													list : true,
													width: '8%'
												},
												
												proformanceReport: {
													title : '绩效报告',
													list : true,
													width: '8%'
												},
												
												curriculumDocument: {
													title : '课程表',
													list : true,
													width: '8%'
												},
												
												projectPlan: {
													title : '实施方案',
													list : true,
													width: '8%'
												}
												
// 												customedit: {
// 													title : '操作',
// 													width : '13%',
// 													sorting : false,
// 													edit : false,
// 													create : false,
// 													visibility : 'fixed',
// 													display : function(
// 															data) {
// // 														var html = '<a class="cancelapply" data-url="" href="../admin/trainingUnitProjectApply_delet.action?pid='
// // 																+ data.record.id
// // 																+ '">取消申报</a>    <a class="ifrbox" data-fancybox-type="iframe" href="../admin/trainingUnitProjectApply_projectApply.action?pid='
// // 																+ data.record.id
// // 																+ '">编辑</a>  &nbsp  <a class="ifrbox" data-fancybox-type="iframe" href="../admin/trainingUnitDocUpdateOptiframe.jsp?pid='
// // 																+ data.record.id
// // 																+ '">材料上传情况</a>';
// // 														return html;
// 														return '';
// 													}
// 												}
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
						
								
					});
	
					$(function(){
						$('#ProjectTableContainer').on('click','.cancelapply',function(){
							if(confirm("你确定要取消申报吗？")) {
								$.get($(this).attr('href'),function(data){
									if(data.Result == 'OK') {
										window.location.href = window.location.href;
									}else {
										alert(data.Message);
									}
								})
							}
						
							return false
						})
					})
	
	
</script>


		


	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>