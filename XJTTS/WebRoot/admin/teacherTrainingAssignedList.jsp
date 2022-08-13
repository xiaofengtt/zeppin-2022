<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">先报后分项目报名记录</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<style>
		div.jtable-main-container table.jtable {margin-top:60px}
		.stateDiv label{width:100px;}
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
					<b>筛选学员</b>
				</p>
			</a>

		</div>
<div class="tablewrap">

<div class="cui-menu" style="width:100%">
<!-- 	<a class="btn btn-primary btn-screen"> -->
<!-- 					筛选学员 </a> -->
	<form id="otherTrainingRecords" class="form-horizontal"  action="../admin/ttAssigned_outputList.action" style="padding-top:15px;display:none;">
		<div class="row clearfix">
			<div class="col-md-6">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">年份</label>
						<div class="col-sm-9">
							<select class="form-control" name="year" id='year' onchange="changeYear(this)">
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
						<select class="form-control" name="projectName" id="projectName" onchange="changeProject(this)">
							<option value="0">全部</option>
							<s:iterator value="searchReportTask" id="rt">
								<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />' <s:if test="#rt.getId()==selectProjectId"> selected </s:if>>
								<s:property value="#rt.getName()" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
				
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-sm-2 control-label" for="">处理状态</label> -->
<!-- 					<div class="col-sm-9"> -->
<%-- 						<select class="form-control" id="trainingStatus" name="assStatus"> --%>
<!-- 							<option value="-1">全部</option> -->
<!-- 							<option value="0">已处理</option> -->
<!-- 							<option value="1">未处理</option> -->
<%-- 						</select> --%>
<!-- 					</div> -->
<!-- 				</div> -->

			</div>
			
		</div>
		<div class="row actionbar">
			<div class="text-center" style="padding-bottom:10px">			
				<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
				<button type="submit" id="output" class="btn btn-default btn-myself">导出列表</button>
				<a class="ifrmbox" href="../admin/ttAssigned_uploadInit.action" data-fancybox-type="iframe" >
					<button type="button" id="upload" class="btn btn-default btn-myself">导入培训记录</button>
				</a>
			</div>
		</div>
	</form>		
	<div class="stateDiv" style="margin-top:15px;">
		<label>处理状态&nbsp;：</label>
		<a class="light" value="-1">全部<span id="span1">(0)</span></a>
		<a class="" value="1">未处理<span id="span2">(0)</span></a>
		<a class="" value="0">已处理<span id="span3">(0)</span></a>
		
	</div>		
	</div>

		<div id="ProjectTableContainer">
		
		</div>

		<script type="text/javascript">
		
		$(".btn-screen").click(function(e){
			e.preventDefault();
			$('#otherTrainingRecords').toggle();
			if($('#otherTrainingRecords').css("display")=="block"){
				$("div.jtable-main-container table.jtable").css("margin-top","216px");
			}else{
				$("div.jtable-main-container table.jtable").css("margin-top","60px");
			}
		})
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
					title : '<a href="../admin/ttAssigned_initPage.action">先报后分项目报名记录</a>',
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
						listAction : '../admin/ttAssigned_getList.action'

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
							width : '5%',
							list : true,
							sorting:false
						},
						idcard : {
							title : '身份证',
							width : '5%',
							list : true,
							sorting:false
						},
						projectId : {
							title : '项目ID',
							width : '5%',
							list : true,
							sorting:false
						},
						projectName : {
							title : '项目名称',
							list : true,
							sorting:false,
							width: '13%'
						},
						subjectId : {
							title : '学科ID',
							list : true,
							sorting:false,
							width: '3%'
						},
						subjectName : {
							title : '学科名称',
							list : true,
							width: '13%'

						},
						status : {
							title : '处理状态',
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
						project:$('select[name="projectName"]').val(),
						subject: $('#subjectName').val(),
						assStatus :  $('.stateDiv a.light').attr("value")
		            });
		            getStatusCount();
		            grtNumber();
					return false;
		        });
		        $(".stateDiv a").click(function(e){
					$(this).addClass("light").siblings().removeClass("light");
					e.preventDefault();
		            $('#ProjectTableContainer').jtable('load', {
						project:$('select[name="projectName"]').val(),
						subject: $('#subjectName').val(),
						assStatus :  $('.stateDiv a.light').attr("value")
		            });
		            getStatusCount();
					return false;
				})
			});
		</script>
	</div>
</div>
<script>
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttAssigned_getAssignTaskProject.action?year=' + year, function(data){
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
			'../admin/ttAssigned_getAssignTaskSubject.action?projectId='
					+ vid, function(data) {
				
				if (data.subjects.length > 0) {
					var cLis = '';
					$.each(data.subjects, function(i, c) {
						cLis += '<option value="' +c.id + '" ' + 'search="'+ c.search +'">' + c.name	+ '</option>';
					});
					$('#subjectName').html(cLis);
				} else {
					$('#subjectName').html('<option value="0">暂无数据</option>');
				}
				
			});
	}
	
	function getStatusCount(){
		var project = $('select[name="projectName"]').val();
		var subject = $('#subjectName').val();
		$.getJSON('../admin/ttAssigned_getStatusCount.action?', {
			project : project,
			subject : subject
		}, function(r) {
			var options = {
				currentPage : r.currentPage,
				totalPages : r.totalPage,
				shouldShowPage:function(type, page, current){
	                switch(type)
	                {
	                   default:
	                      return true;
	                }
	            },
				onPageClicked : function(e, originalEvent, type, page) {
					window.location = updateURLParameter(url(), 'page', page);
				}

			}
			$('#pagination').bootstrapPaginator(options);
		})
	}
	$(function(){
		grtNumber();
		$('#projectName').select2(); 
	})
	//获取数值
	function grtNumber(){
		var project = $('select[name="projectName"]').val();
		var subject = $('#subjectName').val();
		$.getJSON('../admin/ttAssigned_getStatusCount.action?', {
			project : project,
			subject : subject
		}, function(r) {
			$("#span1").html("(" +r.totalCount+ ")");
			$("#span2").html("(" +r.noCheck+ ")");
			$("#span3").html("(" +r.checkPass+ ")");
		});
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>