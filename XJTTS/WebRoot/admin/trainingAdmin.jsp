<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">承训单位管理员管理</s:param>
</s:action>
<style>
.col-sm-2{width:16.66666667%;float:left;}
.col-sm-5{width:41.66666667%;float:left;}
.form-horizontal .control-label{text-align:right;float:left;}
</style>
<div class="main">

	<div class="tablewrap">

		<div class="cui-menu" <s:if test="%{showAddButton==0}">style="display:none"</s:if>>
			<a class="btn btn-primary btn-create" data-fancybox-type="iframe"
				href="../admin/trainingAdmOpt_initPage.action">+ 添加承训单位管理员</a>
		</div>
		<div class="cui-menu" style="width:100%">
			<form id="reprtTask" class="form-horizontal" role="form" action="../admin/projectApply_initPage.action" style="position:absolute;top:2px;right:10px;width:80%">
			<div class="clearfix">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">项目名称</label>
						<div class="col-sm-5">
							<select class="form-control" name="projectName" id="projectName">
								<option value="0">全部</option>
								<s:iterator value="searchReportTask" id="rt">
									<option value='<s:property value="#rt.getId()" />' <s:if test="#rt.getId()==selectProjectId"> selected </s:if> ><s:property
											value="#rt.getName()" /></option>
								</s:iterator>
							</select>
						</div>
						<label class="col-sm-1 control-label" for="">级别</label>
						<div class="col-sm-2">
							<select class="form-control" name="createuser">
								<option value="-1">全部</option>
								<option value="0">普通管理员</option>
								<option value="1">总管理员</option>
							</select>
						</div>
						<button class="btn btn-primary btn-myself" id="findRecord" type="submit" >查询</button>
					</div>
					
	
			</div>
			</form>
		</div>
		<div id="searchbox" class="searchbox" style="position:absolute;top:15px;right:0px">
			<form class="search search_form" id="searchForm" action="#" method="POST">
				<fieldset>
					<label for="searchheader" class="placeholder overlabel">姓名</label>
					<input autocomplete="off" id="searchheader" value="" type="text" name="q">
					<input type="hidden" name="" value="">
					<div class="show_dropdown">
						<div class="dropdown" style="display: block;">
							<ul>
								<li><label for="type_title">按姓名</label><input type="radio" name="stype" id="type_title" value="name" checked="checked">
								<li><label for="type_trainingCollege">按承训单位</label><input type="radio" name="stype" id="type_trainingCollege" value="organization">
								</li>
								<li><label for="type_idcard">按身份证号</label><input type="radio" name="stype" id="type_idcard" value="idcard">
								</li>
							</ul>
							<span class="bl"></span>
							<span class="br"></span>
						</div>
					</div>
					<button type="submit" id="searchBtn" class="search-button">Search</button>
				</fieldset>
			</form>
			
		</div>

		<div id="ProjectTableContainer"></div>

		<script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#ProjectTableContainer')
										.jtable(
												{
													title : '<a href="../admin/trainingAdmin_initPage.action">承训单位管理员管理</a> ',
													messages : zhCN, //Lozalize
													paging : true, //Enable paging
													pageSize : 10, //Set page size (default: 10)
													sorting : true, //Enable sorting
													defaultSorting : 'id ASC', //Set default sorting
													dialogShowEffect : 'drop',

													actions : {
														listAction : '../admin/trainingAdmin_getTrainingAdminList.action',

														deleteAction : '../admin/trainingAdmin_OptTrainingAdmin?method=delete'

													},
													fields : {
														id : {
															title : 'ID',
															key : true,
															width : '5%'
														},
														name : {
															title : '姓名',
															width : '9%',
															inputClass : 'test',
															visibility : 'fixed'
														},
														sex : {
															title : '性别',
															width : '7%',
															options : {
																'0' : '无',
																'1' : '男',
																'2' : '女'
															}
														},
														ethnic : {
															title : '民族',
															width : '7%'
														},
														trainingCollege : {
															title : '承训单位',
															width : '13%',
															list : true
														},
														department : {
															title : '学院/部门',
															width : '13%',
															list : true
														},
														jobDuty : {
															title : '工作职务',
															width : '10%',
															list : true
														},
														phone : {
															title : '办公电话',
															list : true
														},
														mobile : {
															title : '手机',
															list : true
														},
														status : {
															title : '状态',
															options : {
																'1' : '正常',
																'2' : '停用'
															},
															sorting : false,
															width : '6%'
														},
														customedit : {
															title : '操作',
															width : '6%',
															sorting : false,
															edit : false,
															create : false,
															visibility : 'fixed',
															display : function(
																	data) {
																var html = '<a class="ifrbox" data-fancybox-type="iframe" href="../admin/trainingAdmOpt_initPage.action?id='
																		+ data.record.id
																		+ '">编辑</a>';
																return html;
															}
														},
														passwordedit : {
															title : '操作',
															width : '6%',
															sorting : false,
															visibility : 'fixed',
															display : function(data) {
																var html = '<a href="../admin/trainingAdmin_opPassword.action?id='
																		+ data.record.id
																		+ '">重置密码</a>';
																return html;
															}
														
														}
													},
													columnResizable : false,
													deleteConfirmation : function(data) {
														data.deleteConfirmMessage = '确定要停用 <b style="color:#d9534f">'
																+ data.record.name
																+ '</b> 管理员身份?';
														$('#DeleteDialogButton').html('停用');
													},
													recordsLoaded :function (data) {
														$(".ifrbox").colorbox({
															iframe : true,
															width : "860px",
															height: "550px",
															opacity : '0.5',
															overlayClose : false,
															escKey : true
														});
														
														//删除按钮改成停用
													    $('#'+data.target.id).find('.jtable-delete-command-button').each(function(){
															$(this).find('span').text('停用')
														})
													}

												});
									$('#ProjectTableContainer').jtable('load');
										
								//搜索
								
								
						        $('#searchForm').submit(function (e) {
						            e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
										stype:$('input[name="stype"]:checked').val(),
						                q: $('#searchheader').val()
						               
						            });
									return false;
						        });
						        $('#findRecord').click(function (e) {
						            e.preventDefault();
						            $('#ProjectTableContainer').jtable('load', {
										projectName:$('select[name="projectName"]').val(),
						                createuser:$('select[name="createuser"]').val()
				               		
						            });
									return false;
						        });
										
							});
			
							
			
			
			$(function() {//添加按钮
				$(".btn-create").colorbox({
					iframe : true,
					width : "860px",
					height : "550px",
					opacity : '0.5',
					overlayClose : false,
					escKey : true
				});

			})
		</script>





	</div>
</div>


<jsp:include page="foot.jsp"></jsp:include>