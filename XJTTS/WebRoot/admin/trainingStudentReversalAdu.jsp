<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">审核学员异动信息</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
	<style>
		div.jtable-main-container table.jtable {margin-top:60px}
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

<script src="../js/iframe.js"></script>
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
	<div class="listwrap">
		<div class="list-content clearfix" >
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			
			<div class="tablewrap">
				<div class="cui-menu" style="width:100%">
<!-- 					<a class="btn btn-primary btn-screen"> -->
<!-- 					筛选学员 </a> -->
					<form id="aduReplace" class="form-horizontal" role="form" style="display:none;padding-top:15px;">
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
								
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label" for="">学员审核状态</label> -->
<!-- 									<div class="col-sm-9"> -->
<%-- 										<select class="form-control" id="aduStatus" name="status"> --%>
<!-- 											<option value="-2">全部</option> -->
<!-- 											<option value="-1">未审核</option> -->
<!-- 											<option value="0">未通过</option> -->
<!-- 											<option value="1">已通过</option> -->
<%-- 										</select> --%>
<!-- 									</div> -->
<!-- 								</div> -->
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
									<label class="col-sm-2 control-label" for="">班级</label>
									<div class="col-sm-9">
										<select class="form-control" id="classes" name="classes">
											<option value="0">全部</option>
											<option value="1">一班</option>
											<option value="2">二班</option>
											<option value="3">三班</option>
											<option value="4">四班</option>
											<option value="5">五班</option>
											<option value="6">六班</option>
											<option value="7">七班</option>
											<option value="8">八班</option>
											<option value="9">九班</option>
											<option value="10">十班</option>
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
				<div class="stateDiv" style="margin-top:15px;">
						<label>状态&nbsp;：</label>
						<a class="light" value="-2">全部<span id="span1">(0)</span></a>
						<a class="" value="-1">未审核<span id="span2">(0)</span></a>
						
						<a class="" value="1">已通过<span id="span3">(0)</span></a>
						<a class="" value="0">未通过<span id="span4">(0)</span></a>
					</div>
				</div>
				<div id="ProjectTableContainer"></div>
				
				<script type="text/javascript">
				
				$(".btn-screen").click(function(e){
					e.preventDefault();
					$('#aduReplace').toggle();
					if($('#aduReplace').css("display")=="block"){
						$("div.jtable-main-container table.jtable").css("margin-top","220px");
					}else{
						$("div.jtable-main-container table.jtable").css("margin-top","60px");
					}
				})
					$(document).ready(function() {
						$('#ProjectTableContainer')
								.jtable(
										{
											title : '<a href="../admin/trainingStudentOpt_getChangeSubjectRecordsInit.action">审核异动学员</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'id ASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/trainingStudentOpt_getChangeSubjectRecordsList.action'


											},
											fields : {
												id : {
													title : 'ID',
													key : true,
													width : '5%'
												},
												teacher : {
													title : '姓名',
													width : '10%',
													
													visibility : 'fixed'
												},
												idcard : {
													title : '身份证号',
													width : '10%',
													
												},
												project : {
													title : '培训项目',
													width : 'auto'
												},
												trainingSubject : {
													title : '培训学科',
													width : '10%',
													list : true
												},
												classes : {
													title : '培训班级',
													width : '8%',
													list : true
												},
												reTrainingSubject : {
													title : '调整后培训学科',
													width : '10%',
													list : true
												},
												reClasses : {
													title : '调整后班级',
													width : '8%',
													list : true
												},
												status : {
													title : '审核状态',
													width : '8%',
													list : true
												},
												custom : {
													title:'审核操作',
													width:'5%',
													sorting : false,
													edit : false,
													create : false,
													visibility : 'fixed',
													display : function(data) {
														var html = '<a data-fancybox-type="iframe" class="btn btn-create" href="../admin/trainingStudentOpt_reviewReversalInit.action?id='
															+ data.record.id
															+ '">审核</a>'									
														return html;
													}
												}
										
											},
											columnResizable : false,
											recordsLoaded :function (data) {
												$(".btn-create").colorbox({
													iframe : true,
													width : "865px",
													height : "750px",
													maxWidth : '1600px',
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
										projectId:$('select[name="projectName"]').val(),
										subjectId : $('#subjectName').val(),
										status : $('.stateDiv a.light').attr("value"),
										classes : $('#classes').val()
						            });
						            getNumber();
									return false;
						        });	
						        $(".stateDiv a").click(function(e){
									$(this).addClass("light").siblings().removeClass("light");
									e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
										projectId:$('select[name="projectName"]').val(),
										subjectId : $('#subjectName').val(),
										status : $('.stateDiv a.light').attr("value"),
										classes : $('#classes').val()
						            });
									return false;
								})
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
	function adupass() {
		var obj = $('#adupass');
		var cUrl = obj.attr('data-url');
		$.getJSON(cUrl, function(ret) {
			if (ret.Result == 'OK') {
	//				infotip(obj, ret.Message);
	//				$('#adu_'+obj.attr('data-id')).remove();
				alert('成功,' + ret.Message);
				$('#ProjectTableContainer').jtable('load');
			} else {
				alert('失败,' + ret.Message);
			}
		})
		return false;
	}
	
	function adunopass() {
		var obj = $('#adunopass');
		var cUrl = obj.attr('data-url');
		$.getJSON(cUrl, function(ret) {
			if (ret.Result == 'OK') {
	//				infotip(obj, ret.Message);
	//				$('#adu_'+obj.attr('data-id')).remove();
				alert('成功,' + ret.Message);
				$('#ProjectTableContainer').jtable('load');
			} else {
				alert('失败,' + ret.Message);
			}
		})
		return false;
	}
	
	function changeYear(t){
		var year = $(t).val();
		
		//重置学科
		var cis = '<option value="0">全部</option>';
		$('#subjectName').html(cis);
		
		$.getJSON('../admin/trainingStudentOpt_getProject.action?year=' + year, function(data){
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

	function changeProject(t) {
		var pid = $(t).val();
		if(pid == 0){
			var cis = '<option value="0">全部</option>';
			$('#subjectName').html(cis);
			return;
		}
		$.getJSON('../admin/trainingStudentOpt_getSubject.action?projectId=' + pid, function(data){
			if (data.status == "OK") {
				var cLis = '';
				$.each(data.subjects, function(i, c) {
					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
				});
				$('#subjectName').html(cLis);
			} else {
				$('#subjectName').html('<option value="0">暂无数据</option>');
			}
		})
	}
	
	function getStatusCount(){
		var projectId = $('select[name="projectName"]').val();
		var subjectId = $('#subjectName').val();
		var classes = $('#classes').val();
		$.getJSON('../admin/trainingStudentOpt_getChangeSubjectStatus.action?', {
			projectId : projectId,
			subjectId : subjectId,
			classes : classes
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
		getNumber();
		$('#projectName').select2(); 
	})
	//获取数值
	function getNumber(){
		var projectId = $('select[name="projectName"]').val();
		var subjectId = $('#subjectName').val();
		var classes = $('#classes').val();
		$.getJSON('../admin/trainingStudentOpt_getChangeSubjectStatus.action?', {
			projectId : projectId,
			subjectId : subjectId,
			classes : classes
		}, function(r) {
			$("#span1").html("(" +r.totalCount+ ")");
			$("#span2").html("(" +r.noCheck+ ")");
			$("#span3").html("(" +r.checkPass+ ")");
			$("#span4").html("(" +r.checkNoPass+ ")");
		})
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>