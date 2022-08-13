<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">审核替换学员</s:param>
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
					<form id="aduReplace" class="form-horizontal" role="form">
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
									<label class="col-sm-2 control-label" for="">学员审核状态</label>
									<div class="col-sm-9">
										<select class="form-control" id="aduStatus" name="status">
											<option value="-1">全部</option>
											<option value="2">已通过</option>
											<option value="1">未审核</option>
											<option value="0">未通过</option>
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
										<select class="form-control" id="trainingUnit"
											name="trainingUnit">
											<option value="0">全部</option>
										</select>
									</div>
								</div>
							</div>
							<div class="text-center" style="padding-bottom:10px">
								<button class="btn btn-primary" id="findRecord" type="button">查询</button>
							</div>
						</div>
						<div class="row actionbar">
							
						</div>
					</form>
				
				</div>
				<div id="ProjectTableContainer"></div>
				
				<script type="text/javascript">
					$(document).ready(function() {
						$('#ProjectTableContainer')
								.jtable(
										{
											title : '<a href="../admin/ttRecord_initReplaceAdu.action">审核替换学员</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'id ASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/ttRecord_getReplaceTeacherList.action'


											},
											fields : {
												id : {
													title : 'ID',
													key : true,
													width : '4%'
												},
												name : {
													title : '原报送学员',
													width : 'auto',
													
													visibility : 'fixed'
												},
												idCard : {
													title : '原报送学员身份证号',
													width : 'auto',
													
												},
												reName : {
													title : '替换学员',
													width : 'auto'
												},
												reIdCard : {
													title : '替换学员身份证号',
													width : 'auto',
													list : true
												},
												project : {
													title : '项目名称',
													width : 'auto',
													list : true
												},
												trainingSubject : {
													title : '学科名称',
													width : 'auto',
													list : true
												},
												trainingCollege : {
													title : '承训单位',
													width : 'auto',
													list : true
												},
												reStatus : {
													title : '审核状态',
													width : 'auto',
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
														var html = '<a data-fancybox-type="iframe" class="btn btn-create" href="../admin/ttRecord_reviewReplaceTeacherInit.action?id='
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
										tcId : $('#trainingUnit').val(),
										status : $('#aduStatus').val()
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
						cLis += '<option value="' +c.id + '">' + c.name
								+ '</option>';
					});
					$('#trainingUnit').html(cLis);
				} else {
					$('#trainingUnit').html(
							'<option value="0">暂无数据<option>');
				}

			});
	}
	
</script>

<jsp:include page="foot.jsp"></jsp:include>