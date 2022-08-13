<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目申报文件一览</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>
	
<div class="main">

	<div class="listwrap">
		<div class="list_bar">项目申报文件一览</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-screen fliter-btn">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选申报记录</b>
				</p>
			</a>
		</div>
		<div class="list-content clearfix">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<div class="ttRecordsAduBtn" style="padding-bottom:15px;">
			</div>
			<form id="aduTaskTeacher" class="form-horizontal"
				style="display:none" action="">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">年份</label>
							<div class="col-sm-9">
								<select class="form-control" name="year" id='year'
									onchange="changeYear(this)">
									<option value="0">全部</option>
									<s:iterator value="searchYear" id="yt">
										<option value='<s:property value="#yt" />'
											<s:if test="#yt==selectYear"> selected </s:if>><s:property
												value="#yt" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9">
								<select class="form-control" name="projectName" id="projectName"
									onchange="changeProject(this)">
									<option value="0" search='全部'>全部</option>
									<s:iterator value="searchReportTask" id="rt">
										<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'><s:property
												value="#rt.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>


					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">培训科目</label>
							<div class="col-sm-9">
								<select id="subjectName" class="form-control" name="subjectName">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">承训单位</label>
							<div class="col-sm-9">
								<select class="form-control" id="trainingUnit"
									name="trainingUnit">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
						
					</div>
				</div>

				<div class="row actionbar">
					<input name="searchKey" type="hidden"
						value="<s:property value="#parameters.key" />">
					<div class="text-center" style="padding-bottom:25px;">
					
						<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
					</div>
				</div>
			</form>
			<div class="stateDiv">
				<label>状态&nbsp;：</label> <a class="" value="-1">全部<span
					id="span1">(0)</span></a> <a class="" value="1">未审核<span id="span2">(0)</span></a>
				<a class="" value="2">已通过<span id="span3">(0)</span></a> <a class=""
					value="0">未通过<span id="span4">(0)</span></a>
			</div>
			<div class="clearfix">
				<div class="cui-menu2 clearfix" style="margin-left:9px">
				</div>

				<div id="list-content" class="list-content clearfix"></div>

				<script id="ttRecordAduTpl" type="text/x-jsrender">
					<div id="adu_{{:id}}" class="list-item">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="12%"><span class="text-primary">ID：</span>{{:id}}</td>
										<td width="33%"><span class="text-primary">名称：</span><span>{{:projectName}}</span></td>
										<td width="22%"><span class="text-primary">学科：</span> {{:trainingSubject}}</td>
										<td width="18%"><span class="text-primary">申报单位：</span> {{:trainingCollege}}</td>
									</tr>
								</tbody>
							</table>
						</div>

						<d>
						<div class="list-item-bd clearfix">
		
							<div class="list-item-col list-5-05" style="">
								<ul>
									<li><span class="text-primary">项目申报书：</span>
									{{if projectApplyReport.status=='normal'}}
										{{:projectApplyReport.title}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;<a href="..{{:projectApplyReport.path}}">下载</a>
										<a class="checkbox" style="display:inline;" id="checkbox_{{:projectApplyReport.id}}" href="../admin/documentIframe.jsp?id=checkbox_{{:projectApplyReport.id}}" data-fancybox-type="iframe" data-url='{{:projectApplyReport.path}}'>查看</a>
										</li>
									{{else}}
										{{:projectApplyReport.statusCN}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;{{:projectApplyReport.statusCN}}</li>
									{{/if}}
									<li><span class="text-primary">实施方案：</span>
									{{if projectPlan.status=='normal'}}
										{{:projectPlan.title}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;<a href="..{{:projectPlan.path}}">下载</a>
										<a class="checkbox" style="display:inline;" id="checkbox_{{:projectPlan.id}}" href="../admin/documentIframe.jsp?id=checkbox_{{:projectPlan.id}}" data-fancybox-type="iframe" data-url='{{:projectPlan.path}}'>查看</a>
										</li>
									{{else}}
										{{:projectPlan.statusCN}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;{{:projectPlan.statusCN}}</li>
									{{/if}}
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">开班通知：</span>
									{{if startMessageReport.status=='normal'}}
										{{:startMessageReport.title}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;<a href="..{{:startMessageReport.path}}">下载</a>
										<a class="checkbox" style="display:inline;" id="checkbox_{{:startMessageReport.id}}" href="../admin/documentIframe.jsp?id=checkbox_{{:startMessageReport.id}}" data-fancybox-type="iframe" data-url='{{:startMessageReport.path}}'>查看</a>
										</li>
									{{else}}
										{{:startMessageReport.statusCN}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;{{:startMessageReport.statusCN}}</li>
									{{/if}}
									<li><span class="text-primary">课程表：</span>
									{{if curriculumDocument.status=='normal'}}
										{{:curriculumDocument.title}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;<a href="..{{:curriculumDocument.path}}">下载</a>
										<a class="checkbox" style="display:inline;" id="checkbox_{{:curriculumDocument.id}}" href="../admin/documentIframe.jsp?id=checkbox_{{:curriculumDocument.id}}" data-fancybox-type="iframe" data-url='{{:curriculumDocument.path}}'>查看</a>
										</li>
									{{else}}
										{{:curriculumDocument.statusCN}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;{{:curriculumDocument.statusCN}}</li>
									{{/if}}
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">绩效报告：</span>
									{{if proformanceReport.status=='normal'}}
										{{:proformanceReport.title}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;<a href="..{{:proformanceReport.path}}">下载</a>
										<a class="checkbox" style="display:inline;" id="checkbox_{{:proformanceReport.id}}" href="../admin/documentIframe.jsp?id=checkbox_{{:proformanceReport.id}}" data-fancybox-type="iframe" data-url='{{:proformanceReport.path}}'>查看</a>
										</li>
									{{else}}
										{{:proformanceReport.statusCN}}</li>
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;{{:proformanceReport.statusCN}}</li>
									{{/if}}
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">培训简报：</span>{{:workReport.statusCN}}</li>
									{{if workReport.status=='normal'}}
										{{for workReport.reports}}
											<li><span class="text-primary">简报{{: #index+1}}：</span>{{: #data.title}}&nbsp;&nbsp;<a href="..{{: #data.path}}">下载</a>
											<a class="checkbox" style="display:inline;" id="checkbox_{{: #data.id}}" href="../admin/documentIframe.jsp?id=checkbox_{{: #data.id}}" data-fancybox-type="iframe" data-url='{{: #data.path}}'>查看</a>
											</li>
										{{/for}}

									{{else}}
										<li><span class="text-primary">上传情况：</span>&nbsp;&nbsp;{{:workReport.statusCN}}</li>
									{{/if}}
								</ul>
							</div>

						</div>
			
				</script>



				<div id="pagination" style="float:right;" class="pull-right"></div>

			</div>
		</div>

	</div>
</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>
<div class="hide">
	<input type="hide" name="jobTitle_Json" value=""> <input
		type="hide" name="jobDuty_Json" value=""> <input type="hide"
		name="age_Json" value=""> <input type="hide"
		name="teachAge_Json" value=""> <input type="hide"
		name="chineseLanguageLevel_Json" value="">
</div>

<script>
	var jobTitle_Json = [], age_Json = [], teachAge_Json = [], jobDuty_Json = [], chineseLanguageLevel_Json = [];
	var aPage = '';
	var curPage = 1;
	
	function setPage(){
		if(curPage != null && curPage != 'undefined' && curPage != ''){
			setCookie('ttRecordsAdu',curPage,1/12);
		}
	}
	
	var projectStatus = (url('?status')!= null) ? url('?status') : '-1';
	$(function() {
		$(".stateDiv a").each(function(index, val) {
			if ($(this).attr("value") == projectStatus) {
				$(this).addClass("light").siblings().removeClass("light");
			}
		})
		if(projectStatus == null){
			projectStatus = $(".stateDiv a").attr("value");
		}
		$(".stateDiv a").click(function(e) {
			$(this).addClass("light").siblings().removeClass("light");
			e.preventDefault();
			projectStatus = $('.stateDiv a.light').attr("value");
			ttrecordAdu('#aduTaskTeacher');
			setCookie('ttRecordsAduState',projectStatus,1/12);
			curPage = 1;
		})

	})

	
	function ttrecordAdu(o, pp) {
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $(o).serialize() + '&page=' + pp+'&status='+projectStatus;
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/projectApply_getDocumentList.action?' + str,
				function(data) {
					if (data.Result == 'OK') {
						var template = $.templates('#ttRecordAduTpl');
						var html = template.render(data.Records);
						$('#list-content').html(html).removeClass('loading');
						
						$(".checkbox").colorbox({
							iframe : true,
							width : "1060px",
							height : "900px",
							opacity : '0.5',
							overlayClose : false,
							escKey : true
						});
						var options = {
							currentPage : data.page.currentPage,
							totalPages : data.page.totalPage,
							shouldShowPage : function(type, page, current) {
								switch (type) {
								default:
									return true;
								}
							},
							onPageClicked : function(e, originalEvent, type,
									page) {
								var pp = page;
								aPage = page;
								curPage = page;
								ttrecordAdu('#aduTaskTeacher', pp);
							}

						}
						$('#pagination').bootstrapPaginator(options);
						//筛选
						$(o).slideUp('slow', function() {
							$('.fliter-btn').show();
						})
					} else {
						//$('.alert-danger').html(data.message).show();
						$('#list-content').html(
								'<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
										+ data.Message + '</p>').removeClass(
								'loading');
						$('#pagination').html('');
					}
				}).done(function(){
					$(".longtxt").css("width",$("#list-content").width()*0.3-90+"px");
					$(".longtxt_hover").css("width",$("#list-content").width()*0.3+50+"px");
					$(window).resize(function(){
						$(".longtxt").css("width",$("#list-content").width()*0.3-90+"px");
						$(".longtxt_hover").css("width",$("#list-content").width()*0.3+50+"px");
					})
				});
	}
	
	

	$(function() {
		getNumber();
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.recordlist'))
				$('.recordlist').hide();
		});

		$('.feature-group label').click(function() {
			var ele = $(this).prev();
			if (ele.is(':checked')) {
				ele.prop('checked', false);
			} else {
				ele.prop('checked', true);
			}
		})
		
		var startState = getCookie('ttRecordsAduState');
		if(startState != null && startState != 'undefined' && startState != ''){
			$(".stateDiv a[value="+startState+"]").addClass("light").siblings().removeClass("light");
			projectStatus = startState;
			setCookie('ttRecordsAduState',-1,1/12);
		}
		
		var startPage = getCookie('ttRecordsAdu');
		if(startPage != null && startPage != 'undefined' && startPage != ''){
			ttrecordAdu('#aduTaskTeacher', startPage);
			curPage = startPage;
			setCookie('ttRecordsAdu',1,1/12);
		}else{
			ttrecordAdu('#aduTaskTeacher');
		}

		$('#findRecord').click(function(e) {
			e.preventDefault();
			ttrecordAdu('#aduTaskTeacher');
			getNumber();
			curPage = 1;
			return false;
		});
		

		$('.fliter-btn').click(function(e) {
			e.preventDefault();
			$('#aduTaskTeacher').toggle();
			$('.more-filters-container').hide();
			$('.fliters-result-container').hide();

		})
		$('#projectName').select2(); 
	});


	function changeYear(t) {
		var year = $(t).val();
		var projectType = $('#calId').attr("name");
		$.getJSON('../admin/projectApply_getAssignTaskProject.action?year='
				+ year, function(data) {
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name
							+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
			$('#subjectName').html('<option value="0">全部</option>');//
			$('#trainingUnit').html('<option value="0">全部</option>');
		})
	}
	function changeProject(t) {
		var vid = $(t).val();
		$.getJSON(
				'../admin/ttRecord_getAssignTaskOrganization.action?projectId='
						+ vid, function(data) {
					if (data.organizations.length > 0) {
						var cLis = '';
						$.each(data.organizations, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name
									+ '</option>';
						});
						$('#orgas').html(cLis);
					} else {
						$('#orgas').html('<option value="0">暂无数据</option>');
					}
					if (data.subjects.length > 0) {
						var cLis = '';
						$.each(data.subjects, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name
									+ '</option>';
						});
						$('#subjectName').html(cLis);
					} else {
						$('#subjectName')
								.html('<option value="0">暂无数据</option>');
					}
					//console.log(data.trainingUnits)
					if (data.trainingUnits.length > 0) {
						var cLis = '';
						$.each(data.trainingUnits, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name
									+ '</option>';
						});
						$('#trainingUnit').html(cLis);
					} else {
						$('#trainingUnit').html(
								'<option value="0">暂无数据</option>');
					}

				});
	}
	//获取数值
	function getNumber(){
		var year = $('[name="year"]').val();
		var projectType = $('#calId').attr("name");
		var projectName = $('select[name="projectName"]')
				.val();
		var subjectName = $('select[name="subjectName"]')
				.val();
		$.getJSON('../admin/projectApply_getDocStatusCount.action?', {
			year : year,
			projectName : projectName,
			projectType : projectType,
			subjectName : subjectName,
		}, function(r) {
			$("#span1").html("(" + r.totalCount + ")");
			$("#span2").html("(" + r.noCheck + ")");
			$("#span3").html("(" + r.checkPass + ")");
			$("#span4").html("(" + r.checkNoPass + ")");
		})
	}
</script>

<body>
</html>