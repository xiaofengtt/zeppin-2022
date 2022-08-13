<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目评审</s:param>
</s:action>
<style>
	div.jtable-main-container table.jtable {margin-top:10px}
	#aduTaskTeacher{display:none;}
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
	<a class="btn btn-lg btnMyself btn-screen">
		<span><img src="../img/sousuo.png" alt="icon">
		<b>筛选</b>
		</span>
		<p>
			<img src="../img/lanse.png" alt="蓝色三角">
			<b>筛选项目</b>
		</p>
	</a>
</div>
	<div class="tablewrap">
	<div class="cui-menu" style="width:100%">
					<form id="aduTaskTeacher" class="form-horizontal" role="form" action="../admin/ttRecord_outputScore.action">
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
							</div>

							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">项目名称</label>
									<div class="col-sm-9">
										<select class="form-control" name="projectName" id="projectName"
											onchange="changeProject(this)">
											<option value="0" search="全部">全部</option>
											<s:iterator value="searchReportTask" id="rt">
												<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'><s:property
														value="#rt.getName()" /></option>
											</s:iterator>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">评审状态</label>
									<div class="col-sm-9">
										<select class="form-control" id="status" name="status">
											<option value="-1">全部</option>
											<option value="0">未评审</option>
											<option value="1">已评审</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row actionbar">
							<div class="text-center" style="padding-bottom:10px">
								<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
							</div>
						</div>
					</form>
				
				</div>
		<div id="ProjectTableContainer"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#projectName').select2(); 
				$('#ProjectTableContainer')
						.jtable(
								{
									title : '<a href="../admin/projectReview_initPage.action">项目评审</a> ',
									messages : zhCN, //Lozalize
									paging : true, //Enable paging
									pageSize : 10, //Set page size (default: 10)
									defaultSorting : 'id ASC', //Set default sorting
									dialogShowEffect : 'drop',

									actions : {
										listAction : '../admin/projectReview_getProjectList.action'
									},
									fields : {
										paid : {
											title : 'ID',
											key : true,
											width : '5%'
										},
										year : {
											title : '年份',
											width : '4%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										project : {
											title : '项目',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										subject : {
											title : '学科',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										training : {
											title : '承训院校',
											width : '10%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										startTime : {
											title : '培训开始时间',
											width : '9%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										endTime : {
											title : '培训结束时间',
											width : '9%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										classHour : {
											title : '学时',
											width : '4%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										report:{
											title : '申报书',
											width : '5%',
											visibility : 'fixed',
											display : function(data){
												if (data.record.path!=""){
												var html='<a href="..'+ data.record.path +'">下载</a>'
												html += '<a class="checkbox" style="display:inline;" id="checkbox_'+data.record.paid+'" href="../admin/documentIframe.jsp?id=checkbox_'+data.record.paid+'" data-fancybox-type="iframe" data-url="'+data.record.path+'">查看</a>';
												}else{
													html='<a>无</a>'
												}												
												return html;
											}
										},
										score : {
											title : '评分',
											width : '4%',
											inputClass : 'test',
											visibility : 'fixed'
										},
										customedit : {
											title : '操作',
											width : '4%',
											sorting : false,
											edit : false,
											create : false,
											visibility : 'fixed',
											display : function(data) {
												var html = '<a target="_blank" href="../paper/paper_view.action?pid='
														+ data.record.pid +'&sid=' + data.record.sid +'&tc=' + data.record.tc +'&type=' 
														+ data.record.type +'&id=' + data.record.id +'&valuator=' + data.record.valuator 
														+ '">评分</a>';
												return html;
											}
										}
										
									},
									columnResizable : false,
									recordsLoaded :function (data) {
										$(".ifrbox").colorbox({
											iframe : true,
											width : "860px",
											height: "620px",
											opacity : '0.5',
											overlayClose : false,
											escKey : true
										});
										
										$(".checkbox").colorbox({
											iframe : true,
											width : "1060px",
											height : "900px",
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
				            	year:$('select[name="year"]').val(),
								projectId:$('select[name="projectName"]').val(),
								subjectId : $('#subjectName').val(),
								status : $('#status').val()
				            });
							return false;
				        });	
						
							
								
			});
					
		</script>
	</div>
</div>
<script>
$(".btn-screen").click(function(e) {
	e.preventDefault();
	$('#aduTaskTeacher').toggle();
	if($('#aduTaskTeacher').css("display")=="block"){
		$("div.jtable-main-container table.jtable").css("margin-top","150px");
	}else{
		$("div.jtable-main-container table.jtable").css("margin-top","10px");
	}
})
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttRecord_getAssignTaskProject.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
			$('#subjectName').html('<option value="0">全部</option>');
		})
	}

	function changeProject(t) {
		var vid = $(t).val();
		$.getJSON(
			'../admin/ttRecord_getAssignTaskSubject.action?projectId='
					+ vid, function(data) {
				
				if (data.subjects.length > 0) {
					var cLis = '';
					$.each(data.subjects, function(i, c) {
						cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
					});
					$('#subjectName').html(cLis);
				} else {
					$('#subjectName').html('<option value="0">暂无数据</option>');
				}

			});
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>