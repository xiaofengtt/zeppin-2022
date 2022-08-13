<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目申报记录</s:param>
</s:action>
<style>div.jtable-main-container table.jtable {margin-top:150px}</style>
<div class="main">

	<div class="tablewrap">
<div class="cui-menu" style="width:100%">
					<form id="aduTaskTeacher" class="form-horizontal" role="form">
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
							</div>
						</div>

						<div class="row actionbar">
							<div class="text-center">
								<button class="btn btn-primary" id="findRecord" type="submit">查询</button>
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
											title : '<a href="../admin/trainingUnitProjectApplyHistory.jsp">申报记录一览</a> ',
											messages : zhCN, //Lozalize
											paging : true, //Enable paging
											pageSize : 10, //Set page size (default: 10)
											sorting : false, //Enable sorting
											defaultSorting : 'id DASC', //Set default sorting
											dialogShowEffect : 'drop',

											actions : {
												listAction : '../admin/trainingUnitProjectApply_history.action'

											},
											fields : {
												id: {
													title : 'ID',
													key : true,
													width: '5%'
												},
												project : {
													title : '申报项目',
													width : 'auto',
													list : true
												},
												trainingSubject: {
													title : '申报学科',
													width : '10%',
													list : true
													
												},
												trainingStarttime: {
													title : '开始时间',
													width : '10%',
													list : true
												},
												trainingEndtime: {
													title : '结束时间',
													width : '10%',
													list : true
												},
												approveNumber: {
													title : '人数',
													list : true,
													width: '7%'
												},
												
												trainingClasshour: {
													title : '课时',
													list : true,
													width: '7%'
												},
												status: {
													title : '申报状态',
													list : true,
													width: '9%'
												},
												
												customedit: {
													title : '操作',
													width : '13%',
													sorting : false,
													edit : false,
													create : false,
													visibility : 'fixed',
													display : function(
															data) {
														var html = '<a class="cancelapply" data-url="" href="../admin/trainingUnitProjectApply_delet.action?pid='
																+ data.record.id
																+ '">取消申报</a>    <a class="ifrbox" data-fancybox-type="iframe" href="../admin/trainingUnitProjectApply_projectApply.action?pid='
																+ data.record.id
																+ '">编辑</a>';
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
												
											
											}

										});
							$('#ProjectTableContainer').jtable('load');
							//搜索
					        $('#aduTaskTeacher').submit(function (e) {
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