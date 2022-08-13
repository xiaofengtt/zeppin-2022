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
		div.jtable-main-container table.jtable {margin-top:60px}
		.stateDiv label{width:100px;}
		div.jtable-main-container > div.jtable-title div.jtable-title-text{line-height:58px;font-size:18px;}
		.cui-menu{top:60px;}
		div.jtable-main-container > table.jtable > thead th{text-align:center;}
		html{min-width:1020px;}
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
				<a class="btn btnMyself btn-search btn-screen">
					<span><img src="../img/sousuo.png" alt="icon">
						<b>筛选</b>
					</span>
					<p>
						<img src="../img/lanse.png" alt="蓝色三角">
						<b>筛选学员</b>
					</p>
				</a>
	
			</div>
	<div class="listwrap" style="min-width:1020px;">
		<div class="list-content clearfix" >
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			
			<div class="tablewrap">
				<div class="cui-menu" style="width:100%">
<!-- 					<a class="btn btn-primary btn-screen"> -->
<!-- 					筛选学员 </a> -->
					<form id="aduTaskTeacher" class="form-horizontal" role="form"action="../admin/ttRecord_outputScore.action" style="padding-top:15px;display:none;">
						<input type="hidden" value="1" name="projectCycle" />
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
											<option value="0" search="全部">全部</option>
											<s:iterator value="searchReportTask" id="rt">
												<option value='<s:property value="#rt.getId()" />' search="<s:property value="#rt.getName()" />"><s:property
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
								
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label" for="">培训状态</label> -->
<!-- 									<div class="col-sm-9"> -->
<%-- 										<select class="form-control" id="trainingStatus" name="trainingStatus"> --%>
<!-- 											<option value="-1">全部</option> -->
<!-- 											<option value="0">异动</option> -->
<!-- 											<option value="1">未报到</option> -->
<!-- 											<option value="2">已报到</option> -->
<!-- 											<option value="3">已结业</option> -->
<!-- 											<option value="4">未结业</option> -->
<%-- 										</select> --%>
<!-- 									</div> -->
<!-- 								</div> -->
							</div>
						</div>
						<div class="row actionbar">
							<div class="text-center" style="padding-bottom:10px">
								<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
								<button class="btn btn-default btn-myself" id="" type="submit">导出</button>
							</div>
						</div>
					</form>
				<div class="stateDiv" style="margin-top:15px;">
					<label>培训状态：</label>
					<a class="light" value="-1">全部<span id="span1">(0)</span></a>					
					<a class="" value="1">未报到<span id="span2">(0)</span></a>
					<a class="" value="2">已报到<span id="span3">(0)</span></a>
					<a class="" value="4">未结业<span id="span4">(0)</span></a>
					<a class="" value="3">已结业<span id="span5">(0)</span></a>				
					<a class="" value="0">异动<span id="span6">(0)</span></a>
				</div>
		</div>
				</div>
				<div id="ProjectTableContainer"></div>
				
				<script type="text/javascript">
					$(document).ready(function() {
						var projectCycle = $('input[name="projectCycle"]').val() == null ? "1" : $('input[name="projectCycle"]').val();
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
													width : '3%'
												},
												projectName : {
													title : '项目',
													width : '12%',
													
													visibility : 'fixed'
												},
												subjectName : {
													title : '培训科目',
													width : '4%',
													
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
												studyhour:{
                                                	title : '培训学时',
                                                	list : true,
                                                	sorting:false,
                                                	width: '12%',
                                                	display: function(data){
                                                		var htmlStr = '';
                                                		if(data.record.studyhour != null){
                                                			for(var i = 0; i < data.record.studyhour.length; i++){
                                                    			if(data.record.studyhour[i].value > 0){
                                                    				htmlStr+=data.record.studyhour[i].nameCN+'：<strong>'
                                                    				+data.record.studyhour[i].value+'</strong><br>'
                                                    			}
                                                    		}
                                                		}
                                                		if(htmlStr.length == 0){
                                                			htmlStr += '无';
                                                		}
                                                		return htmlStr;
                                                	}
                                                },
												certificate : {
													title : '证书编号',
													width : '4%',
													list : true
												},
												status : {
													title : '状态',
													width : '3%',
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

								$('#ProjectTableContainer').jtable('load',{
									projectCycle : projectCycle
								});
								
								
								//搜索
						        $('#findRecord').click(function (e) {
						            e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
										projectName:$('select[name="projectName"]').val(),
						                trainingUnit: $('#trainingUnit').val(),
										subjectName : $('#subjectName').val(),
										teacherName : $('input[name="teacherName"]').val(),
				               			trainingStatus : $('.stateDiv a.light').attr("value"),
				               			projectCycle : projectCycle
						            });
						            getStatusCount();
						            getNumber();
									return false;
						        });
							
						        $(".stateDiv a").click(function(e){
									$(this).addClass("light").siblings().removeClass("light");
									e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
										projectName:$('select[name="projectName"]').val(),
						                trainingUnit: $('#trainingUnit').val(),
										subjectName : $('#subjectName').val(),
										teacherName : $('input[name="teacherName"]').val(),
				               			trainingStatus : $('.stateDiv a.light').attr("value"),
				               			projectCycle : projectCycle
						            });
						            getStatusCount();
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
$(".btn-screen").click(function(e){
	e.preventDefault();
	$('#aduTaskTeacher').toggle();
	if($('#aduTaskTeacher').css("display")=="block"){
		$("div.jtable-main-container table.jtable").css("margin-top","260px");
	}else{
		$("div.jtable-main-container table.jtable").css("margin-top","60px");
	}
})
	function changeYear(t){
		var year = $(t).val();
		var projectCycle = $('input[name="projectCycle"]').val() == null ? "1" : $('input[name="projectCycle"]').val();
		$.getJSON('../admin/ttRecord_getAssignTaskProjects.action?year=' + year + '&projectCycle=' + projectCycle, function(data){
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
			$('#trainingUnit').html('<option value="0">全部</option>');
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
	function getStatusCount(){
		var projectName = $('select[name="projectName"]').val();
		var trainingUnit = $('#trainingUnit').val();
		var subjectName = $('#subjectName').val();
		var teacherName = $('input[name="teacherName"]').val();
		var trainingStatus = $('.stateDiv a.light').attr("value");
		var projectCycle = $('input[name="projectCycle"]').val() == null ? "1" : $('input[name="projectCycle"]').val();
		$.getJSON('../admin/ttRecord_getSearchMarkStatus.action?', {
			projectName : projectName,
			trainingUnit : trainingUnit,
			subjectName : subjectName,
			teacherName : teacherName,
			projectCycle : projectCycle
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
	function getNumber(){
		var projectName = $('select[name="projectName"]').val();
		var trainingUnit = $('#trainingUnit').val();
		var subjectName = $('#subjectName').val();
		var teacherName = $('input[name="teacherName"]').val();
		var trainingStatus = $('.stateDiv a.light').attr("value");
		var projectCycle = $('input[name="projectCycle"]').val() == null ? "1" : $('input[name="projectCycle"]').val();
		$.getJSON('../admin/ttRecord_getSearchMarkStatus.action?', {
			projectName : projectName,
			trainingUnit : trainingUnit,
			subjectName : subjectName,
			teacherName : teacherName,
			projectCycle : projectCycle
		}, function(r) {
			$("#span1").html("(" +r.totalCount+ ")");
			$("#span2").html("(" +r.noCheck+ ")");
			$("#span3").html("(" +r.checkPass+ ")");
			$("#span4").html("(" +r.noComplete+ ")");
			$("#span5").html("(" +r.complete+ ")");
			$("#span6").html("(" +r.other+ ")");
		})
	}
</script>

<jsp:include page="foot.jsp"></jsp:include>